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
    public static final Token BIT_REVERSE = newSymbol(TokenType.BIT_REVERSE);
    public static final Token L_SHIFT = newSymbol(TokenType.L_SHIFT);
    public static final Token R_SHIFT = newSymbol(TokenType.R_SHIFT);
    public static final Token UNSIGNED_R_SHIFT = newSymbol(TokenType.UNSIGNED_R_SHIFT);
    public static final Token LT = newSymbol(TokenType.LT);
    public static final Token LTE = newSymbol(TokenType.LTE);
    public static final Token GT = newSymbol(TokenType.GT);
    public static final Token GTE = newSymbol(TokenType.GTE);
    public static final Token EQ = newSymbol(TokenType.EQ);
    public static final Token NEQ = newSymbol(TokenType.NEQ);
    public static final Token DOT = newSymbol(TokenType.DOT);
    public static final Token TRUE = newSymbol(TokenType.TRUE);
    public static final Token FALSE = newSymbol(TokenType.FALSE);
    public static final Token NOT = newSymbol(TokenType.NOT);
    public static final Token AND = newSymbol(TokenType.AND);
    public static final Token OR = newSymbol(TokenType.OR);
    public static final Token XOR = newSymbol(TokenType.XOR);


    public static Token newSymbol(String tokenType) {
        return new Token.TokenImpl(null, tokenType);
    }

    public static Token newNumber(String number) {
        return new Token.TokenImpl(number, TokenType.NUMBER);
    }
}
