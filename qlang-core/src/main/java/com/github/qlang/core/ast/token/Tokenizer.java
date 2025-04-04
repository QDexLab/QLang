package com.github.qlang.core.ast.token;

import com.github.qlang.core.ast.Iterator;
import com.github.qlang.core.exception.TokenException;

/**
 * 分词器
 */
public class Tokenizer extends Iterator<Character> {

    public Tokenizer(String input) {
        super(input.chars().mapToObj(c -> (char) c).toArray(Character[]::new));
    }

    private void skipWhitespace() {
        while (has() && Character.isWhitespace(peek())) {
            advance();
        }
    }

    public Token nextToken() {
        skipWhitespace();
        if (has()) {
            char c = peek();
            switch (c) {
                case '(':
                    advance();
                    return Tokens.LPAREN;
                case ')':
                    advance();
                    return Tokens.RPAREN;
                case '+':
                    advance();
                    return Tokens.PLUS;
                case '-':
                    advance();
                    return Tokens.MINUS;
                case '*':
                    advance();
                    return Tokens.MUL;
                case '/':
                    advance();
                    return Tokens.DIV;
                case '%':
                    advance();
                    return Tokens.MOD;
                case '^':
                    advance();
                    return Tokens.POW;
                case '.':
                    advance();
                    return Tokens.DOT;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    return nextNumber();

                default:
                    throw new TokenException("unexpected token: " + c);
            }
        }
        return Tokens.EOF;
    }

    private Token nextNumber() {
        boolean dotFound = false;
        StringBuilder sb = new StringBuilder();
        while (has()) {
            char c = peek();
            if (isNumber(c)) {
                sb.append(c);
            } else if (c == '.' && !dotFound && hasEnough(2) && isNumber(next())) {
                dotFound = true;
                sb.append(c);
            } else {
                break;
            }
            advance();
        }
        return Tokens.newNumber(sb.toString());
    }

    private boolean isNumber(char c) {
        return Character.isDigit(c);
    }
}
