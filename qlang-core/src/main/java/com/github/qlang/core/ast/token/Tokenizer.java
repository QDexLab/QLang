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
                case '~':
                    advance();
                    return Tokens.BIT_REVERSE;
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
                case 't':
                    if (hasEnough(4) && followWord("true")) {
                        advance(4);
                        return Tokens.TRUE;
                    } else {
                        throwUnexpectedTokenException(c);
                    }
                case 'f':
                    if (hasEnough(5) && followWord("false")) {
                        advance(5);
                        return Tokens.FALSE;
                    } else {
                        throwUnexpectedTokenException(c);
                    }
                case '!':
                    advance();
                    return Tokens.NOT;

                default:
                    throwUnexpectedTokenException(c);
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

    private boolean followWord(String word) {
        char[] chars = word.toCharArray();
        int length = chars.length;
        if (!hasEnough(length)) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (peek(i) != chars[i]) {
                return false;
            }
        }
        if (hasEnough(length + 1)) {
            return !isIdentifierChar(peek(length));
        }
        return true;
    }

    private boolean isIdentifierChar(char c) {
        return c >= '0' && c <= '9'
                || c >= 'a' && c <= 'z'
                || c >= 'A' && c <= 'Z'
                || c == '_';
    }

    private void throwUnexpectedTokenException(char c) {
        throw new TokenException("unexpected token: " + c);
    }
}
