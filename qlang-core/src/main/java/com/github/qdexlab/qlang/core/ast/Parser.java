package com.github.qdexlab.qlang.core.ast;

import com.github.qdexlab.qlang.core.ast.node.AndOp;
import com.github.qdexlab.qlang.core.ast.node.BitAndOp;
import com.github.qdexlab.qlang.core.ast.node.BitNotOp;
import com.github.qdexlab.qlang.core.ast.node.BitOrOp;
import com.github.qdexlab.qlang.core.ast.node.BitXorOp;
import com.github.qdexlab.qlang.core.ast.node.DivOp;
import com.github.qdexlab.qlang.core.ast.node.ElvisOp;
import com.github.qdexlab.qlang.core.ast.node.EqOp;
import com.github.qdexlab.qlang.core.ast.node.False;
import com.github.qdexlab.qlang.core.ast.node.FuncOp;
import com.github.qdexlab.qlang.core.ast.node.GtOp;
import com.github.qdexlab.qlang.core.ast.node.GteOp;
import com.github.qdexlab.qlang.core.ast.node.Identifier;
import com.github.qdexlab.qlang.core.ast.node.IndexOp;
import com.github.qdexlab.qlang.core.ast.node.LShiftOp;
import com.github.qdexlab.qlang.core.ast.node.LtOp;
import com.github.qdexlab.qlang.core.ast.node.LteOp;
import com.github.qdexlab.qlang.core.ast.node.MemberOp;
import com.github.qdexlab.qlang.core.ast.node.MinusOp;
import com.github.qdexlab.qlang.core.ast.node.ModOp;
import com.github.qdexlab.qlang.core.ast.node.MulOp;
import com.github.qdexlab.qlang.core.ast.node.NegOp;
import com.github.qdexlab.qlang.core.ast.node.NeqOp;
import com.github.qdexlab.qlang.core.ast.node.Node;
import com.github.qdexlab.qlang.core.ast.node.NotOp;
import com.github.qdexlab.qlang.core.ast.node.Null;
import com.github.qdexlab.qlang.core.ast.node.Num;
import com.github.qdexlab.qlang.core.ast.node.OrOp;
import com.github.qdexlab.qlang.core.ast.node.PlusOp;
import com.github.qdexlab.qlang.core.ast.node.PosOp;
import com.github.qdexlab.qlang.core.ast.node.PowOp;
import com.github.qdexlab.qlang.core.ast.node.RShiftOp;
import com.github.qdexlab.qlang.core.ast.node.Str;
import com.github.qdexlab.qlang.core.ast.node.True;
import com.github.qdexlab.qlang.core.ast.node.XorOp;
import com.github.qdexlab.qlang.core.ast.token.Token;
import com.github.qdexlab.qlang.core.ast.token.TokenType;
import com.github.qdexlab.qlang.core.ast.token.Tokenizer;
import com.github.qdexlab.qlang.core.ast.token.Tokens;
import com.github.qdexlab.qlang.core.exception.ParseException;
import com.github.qdexlab.qlang.core.function.FunctionContainer;

import java.util.ArrayList;
import java.util.List;

public class Parser extends Iterator<Token> {
    private static volatile boolean initialized = false;

    public Parser(String input) {
        this(new Tokenizer(input));
    }

    public Parser(Tokenizer tokenizer) {
        super(parseTokens(tokenizer));
        initialize();
    }

    private void initialize() {
        if (!initialized) {
            synchronized (Parser.class) {
                if (!initialized) {
                    FunctionContainer.loadFunctions();
                    initialized = true;
                }
            }
        }
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
        return earElvis();
    }

    private Node earElvis() {
        Node left = earXor();
        while (peek().in(TokenType.ELVIS)) {
            advance();
            Node right = earXor();
            left = new ElvisOp(left, right);
        }
        return left;
    }

    private Node earXor() {
        Node left = earOr();
        while (peek().in(TokenType.XOR)) {
            advance();
            Node right = earOr();
            left = new XorOp(left, right);
        }
        return left;
    }

    private Node earOr() {
        Node left = earAnd();
        while (peek().in(TokenType.OR)) {
            advance();
            Node right = earAnd();
            left = new OrOp(left, right);
        }
        return left;
    }

    private Node earAnd() {
        Node left = earBitOr();
        while (peek().in(TokenType.AND)) {
            advance();
            Node right = earBitOr();
            left = new AndOp(left, right);
        }
        return left;
    }

    private Node earBitOr() {
        Node left = earBitXor();
        while (peek().in(TokenType.BIT_OR)) {
            advance();
            Node right = earBitXor();
            left = new BitOrOp(left, right);
        }
        return left;
    }

    private Node earBitXor() {
        Node left = earBitAnd();
        while (peek().in(TokenType.BIT_XOR)) {
            advance();
            Node right = earBitAnd();
            left = new BitXorOp(left, right);
        }
        return left;
    }

    private Node earBitAnd() {
        Node left = eatEqual();
        while (peek().in(TokenType.BIT_AND)) {
            advance();
            Node right = eatEqual();
            left = new BitAndOp(left, right);
        }
        return left;
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
        } else if (op.is(TokenType.BIT_NOT)) {
            advance();
            return new BitNotOp(eatPosNegNot());
        } else {
            return eatMemberIndex();
        }
    }

    private Node eatMemberIndex() {
        Node left = eatUnit();
        while (peek().in(TokenType.DOT, TokenType.L_SQUARE)) {
            Token op = peek();
            advance();
            if (op.is(TokenType.DOT)) {
                if (hasEnough(2) && peek().is(TokenType.IDENTIFIER) && peek(1).is(TokenType.LPAREN)) {
                    String name = peek().getValue();
                    advance(); // 跳过方法名
                    advance(); // 跳过左括号
                    Node right = eatFunc(name);
                    left = new MemberOp(left, right);
                } else if (hasEnough(1) && peek().is(TokenType.IDENTIFIER)) {
                    String name = peek().getValue();
                    advance(); // name
                    Node right = new Identifier(name);
                    left = new MemberOp(left, right);
                } else {
                    throw new ParseException("unsupported operator: " + op);
                }
            } else if (op.is(TokenType.L_SQUARE)) {
                if (hasEnough(2)) {
                    Node right = eat();
                    if (has() && peek().is(TokenType.R_SQUARE)) {
                        advance();
                        left = new IndexOp(left, right);
                    } else {
                        throw new ParseException("L_SQUARE and R_SQUARE must appear in pairs");
                    }
                } else {
                    throw new ParseException("unsupported operator: " + op);
                }
            } else {
                throw new ParseException("unsupported operator: " + op);
            }
        }
        return left;
    }

    private Node eatUnit() {
        Token token = peek();
        advance();
        if (token.is(TokenType.IDENTIFIER)) {
            if (has() && peek().is(TokenType.LPAREN)) {
                advance(); // 跳过参数左括号
                return eatFunc(token.getValue());
            }
            return new Identifier(token.getValue());
        } else if (token.is(TokenType.NUMBER)) {
            return new Num(token.getValue());
        } else if (token.is(TokenType.STRING)) {
            return new Str(token.getValue());
        } else if (token.is(TokenType.TRUE)) {
            return new True();
        } else if (token.is(TokenType.FALSE)) {
            return new False();
        } else if (token.is(TokenType.NULL)) {
            return new Null();
        } else if (Tokens.LPAREN.equals(token)) {
            Node eat = eat();
            if (has() && peek().is(TokenType.RPAREN)) {
                advance(); // 跳过右括号
                return eat;
            }
            throw new ParseException("expected rparen, but: " + peek());
        } else {
            throw new ParseException("unexpected token: " + token);
        }
    }

    private Node eatFunc(String funcName) {
        boolean success = false;
        boolean hasArgs = false;
        List<Node> args = new ArrayList<>();
        while (has()) {
            Token op = peek();
            if (op.is(TokenType.RPAREN)) {
                success = true;
                advance(); // 跳过参数右括号
                break;
            } else if (!hasArgs) {
                // 第一个参数
                args.add(eat());
                hasArgs = true;
            } else {
                if (peek().in(TokenType.COMMA)) {
                    advance(); // 跳过逗号
                    args.add(eat()); // 第2~n个参数
                } else {
                    throw new ParseException("function parameter must be separated by comma");
                }
            }
        }
        if (success) {
            return new FuncOp(funcName, args.toArray(new Node[0]));
        }
        throw new ParseException("error while parsing function: " + funcName);
    }

    @Override
    public boolean has() {
        return !peek().is(TokenType.EOF);
    }
}
