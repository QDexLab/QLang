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
                    if (hasEnough(2) && follow("**", false)) {
                        advance(2);
                        return Tokens.POW;
                    }
                    advance();
                    return Tokens.MUL;
                case '/':
                    advance();
                    return Tokens.DIV;
                case '%':
                    advance();
                    return Tokens.MOD;
                case '~':
                    advance();
                    return Tokens.BIT_NOT;
                case '!':
                    if (hasEnough(2) && follow("!=", false)) {
                        advance(2);
                        return Tokens.NEQ;
                    }
                    advance();
                    return Tokens.NOT;
                case '.':
                    advance();
                    return Tokens.DOT;
                case '<':
                    if (hasEnough(2)) {
                        if (follow("<<", false)) {
                            advance(2);
                            return Tokens.L_SHIFT;
                        } else if (follow("<=", false)) {
                            advance(2);
                            return Tokens.LTE;
                        }
                    }
                    advance();
                    return Tokens.LT;
                case '>':
                    if (hasEnough(2)) {
                        if (follow(">>", false)) {
                            advance(2);
                            return Tokens.R_SHIFT;
                        } else if (follow(">=", false)) {
                            advance(2);
                            return Tokens.GTE;
                        }
                    }
                    advance();
                    return Tokens.GT;
                case '&':
                    if (hasEnough(2) && follow("&&", false)) {
                        advance(2);
                        return Tokens.AND;
                    }
                    advance();
                    return Tokens.BIT_AND;
                case '|':
                    if (hasEnough(2) && follow("||", false)) {
                        advance(2);
                        return Tokens.OR;
                    }
                    advance();
                    return Tokens.BIT_OR;
                case '^':
                    if (hasEnough(2) && follow("^^", false)) {
                        advance(2);
                        return Tokens.XOR;
                    }
                    advance();
                    return Tokens.BIT_XOR;
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
                case 'a':
                case 'b':
                case 'c':
                case 'd':
                case 'e':
                case 'f':
                    if (hasEnough(5) && followWord("false")) {
                        advance(5);
                        return Tokens.FALSE;
                    }
                case 'g':
                case 'h':
                case 'i':
                case 'j':
                case 'k':
                case 'l':
                case 'm':
                case 'n':
                case 'o':
                case 'p':
                case 'q':
                case 'r':
                case 's':
                case 't':
                    if (hasEnough(4) && followWord("true")) {
                        advance(4);
                        return Tokens.TRUE;
                    }
                case 'u':
                case 'v':
                case 'w':
                case 'x':
                case 'y':
                case 'z':
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                case 'G':
                case 'H':
                case 'I':
                case 'J':
                case 'K':
                case 'L':
                case 'M':
                case 'N':
                case 'O':
                case 'P':
                case 'Q':
                case 'R':
                case 'S':
                case 'T':
                case 'U':
                case 'V':
                case 'W':
                case 'X':
                case 'Y':
                case 'Z':
                case '_':
                    throwUnexpectedTokenException(c);
                case '=':
                    if (hasEnough(2) && follow("==", false)) {
                        advance(2);
                        return Tokens.EQ;
                    }
                    throwUnexpectedTokenException(c);

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
        return follow(word, true);
    }

    private boolean follow(String string, boolean isWord) {
        char[] chars = string.toCharArray();
        int length = chars.length;
        if (!hasEnough(length)) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (peek(i) != chars[i]) {
                return false;
            }
        }
        if (isWord && hasEnough(length + 1)) {
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
