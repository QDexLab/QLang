package com.github.qlang.core.ast.token;

public class Tokens {
    public static final Token EOF = newSymbol(TokenType.EOF);
    public static final Token LPAREN = newSymbol(TokenType.LPAREN);
    public static final Token RPAREN = newSymbol(TokenType.RPAREN);
    public static final Token PLUS = newSymbol(TokenType.PLUS);
    public static final Token MINUS = newSymbol(TokenType.MINUS);
    public static final Token MUL = newSymbol(TokenType.MUL);
    public static final Token DIV = newSymbol(TokenType.DIV);
    public static final Token MOD = newSymbol(TokenType.MOD);
    public static final Token POW = newSymbol(TokenType.POW);
    public static final Token DOT = newSymbol(TokenType.DOT);


    public static Token newSymbol(String tokenType) {
        return new Token.TokenImpl(null, tokenType);
    }

    public static Token newNumber(String number) {
        return new Token.TokenImpl(number, TokenType.NUMBER);
    }
}
