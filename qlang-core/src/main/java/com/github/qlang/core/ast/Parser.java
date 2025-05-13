package com.github.qlang.core.ast;

import com.github.qlang.core.ast.node.BitReverseOp;
import com.github.qlang.core.ast.node.DivOp;
import com.github.qlang.core.ast.node.EqOp;
import com.github.qlang.core.ast.node.False;
import com.github.qlang.core.ast.node.GtOp;
import com.github.qlang.core.ast.node.GteOp;
import com.github.qlang.core.ast.node.LShiftOp;
import com.github.qlang.core.ast.node.LtOp;
import com.github.qlang.core.ast.node.LteOp;
import com.github.qlang.core.ast.node.MinusOp;
import com.github.qlang.core.ast.node.ModOp;
import com.github.qlang.core.ast.node.MulOp;
import com.github.qlang.core.ast.node.NegOp;
import com.github.qlang.core.ast.node.NeqOp;
import com.github.qlang.core.ast.node.Node;
import com.github.qlang.core.ast.node.NotOp;
import com.github.qlang.core.ast.node.Num;
import com.github.qlang.core.ast.node.PlusOp;
import com.github.qlang.core.ast.node.PosOp;
import com.github.qlang.core.ast.node.PowOp;
import com.github.qlang.core.ast.node.RShiftOp;
import com.github.qlang.core.ast.node.True;
import com.github.qlang.core.ast.token.Token;
import com.github.qlang.core.ast.token.TokenType;
import com.github.qlang.core.ast.token.Tokenizer;
import com.github.qlang.core.ast.token.Tokens;
import com.github.qlang.core.exception.ParseException;

import java.util.ArrayList;
import java.util.List;

public class Parser extends Iterator<Token> {

    public Parser(String input) {
        this(new Tokenizer(input));
    }

    public Parser(Tokenizer tokenizer) {
        super(parseTokens(tokenizer));
    }

    private static Token[] parseTokens(Tokenizer tokenizer) {
        List<Token> tokens = new ArrayList<>();
        Token token;
        do {
            token = tokenizer.nextToken();
            tokens.add(token);
        } while (!token.is(TokenType.EOF));
        return tokens.toArray(new Token[0]);
    }

    public Node parse() {
        Node eat = eat();
        if (has()) {
            throw new ParseException("unexpected end, has more tokens");
        }
        return eat;
    }

    private Node eat() {
        return eatEqual();
    }

    private Node eatEqual() {
        Node left = eatCompare();
        while (peek().in(TokenType.EQ, TokenType.NEQ)) {
            Token op = peek();
            advance();
            Node right = eatCompare();
            if (op.is(TokenType.EQ)) {
                left = new EqOp(left, right);
            } else if (op.is(TokenType.NEQ)) {
                left = new NeqOp(left, right);
            }
        }
        return left;
    }

    private Node eatCompare() {
        Node left = eatShift();
        while (peek().in(TokenType.LT, TokenType.LTE, TokenType.GT, TokenType.GTE)) {
            Token op = peek();
            advance();
            Node right = eatShift();
            if (op.is(TokenType.LT)) {
                left = new LtOp(left, right);
            } else if (op.is(TokenType.LTE)) {
                left = new LteOp(left, right);
            } else if (op.is(TokenType.GT)) {
                left = new GtOp(left, right);
            } else if (op.is(TokenType.GTE)) {
                left = new GteOp(left, right);
            }
        }
        return left;
    }

    private Node eatShift() {
        Node left = eatPlusMinus();
        while (peek().in(TokenType.L_SHIFT, TokenType.R_SHIFT)) {
            Token op = peek();
            advance();
            Node right = eatPlusMinus();
            if (op.is(TokenType.L_SHIFT)) {
                left = new LShiftOp(left, right);
            } else if (op.is(TokenType.R_SHIFT)) {
                left = new RShiftOp(left, right);
            }
        }
        return left;
    }

    private Node eatPlusMinus() {
        Node left = eatMulDivMod();
        while (peek().in(TokenType.PLUS, TokenType.MINUS)) {
            Token op = peek();
            advance();
            Node right = eatMulDivMod();
            if (op.is(TokenType.PLUS)) {
                left = new PlusOp(left, right);
            } else if (op.is(TokenType.MINUS)) {
                left = new MinusOp(left, right);
            } else {
                throw new ParseException("unsupported operator: " + op);
            }
        }
        return left;
    }

    private Node eatMulDivMod() {
        Node left = eatPow();
        while (peek().in(TokenType.MUL, TokenType.DIV, TokenType.MOD)) {
            Token op = peek();
            advance();
            Node right = eatPow();
            if (op.is(TokenType.MUL)) {
                left = new MulOp(left, right);
            } else if (op.is(TokenType.DIV)) {
                left = new DivOp(left, right);
            } else if (op.is(TokenType.MOD)) {
                left = new ModOp(left, right);
            } else {
                throw new ParseException("unsupported operator: " + op);
            }
        }
        return left;
    }

    private Node eatPow() {
        Node left = eatPosNegNot();
        while (peek().is(TokenType.POW)) {
            advance();
            Node right = eatPosNegNot();
            left = new PowOp(left, right);
        }
        return left;
    }

    private Node eatPosNegNot() {
        Token op = peek();
        if (op.in(TokenType.PLUS, TokenType.MINUS)) {
            advance();
            if (op.is(TokenType.PLUS)) {
                return new PosOp(eatPosNegNot());
            } else {
                return new NegOp(eatPosNegNot());
            }
        } else if (op.is(TokenType.NOT)) {
            advance();
            return new NotOp(eatPosNegNot());
        } else if (op.is(TokenType.BIT_REVERSE)) {
            advance();
            return new BitReverseOp(eatPosNegNot());
        } else {
            return eatUnit();
        }
    }

    private Node eatUnit() {
        Token token = peek();
        advance();
        if (Tokens.LPAREN.equals(token)) {
            Node eat = eat();
            if (Tokens.RPAREN.equals(peek())) {
                advance(); // 跳过右括号
                return eat;
            }
            throw new ParseException("expected rparen, but: " + peek());
        } else if (token.is(TokenType.NUMBER)) {
            return new Num(token.getValue());
        } else if (token.is(TokenType.TRUE)) {
            return new True();
        } else if (token.is(TokenType.FALSE)) {
            return new False();
        } else {
            throw new ParseException("unexpected token: " + token);
        }
    }

    @Override
    public boolean has() {
        return !peek().is(TokenType.EOF);
    }
}
