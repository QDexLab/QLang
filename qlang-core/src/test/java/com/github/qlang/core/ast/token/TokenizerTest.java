package com.github.qlang.core.ast.token;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {

    @Test
    void nextNumber() {
        assertEquals(Tokens.newNumber("123"), new Tokenizer("123").nextToken());
        assertEquals(Tokens.newNumber("001"), new Tokenizer("001").nextToken());
        assertEquals(Tokens.newNumber("0.12"), new Tokenizer("0.12").nextToken());

        Tokenizer tokenizer1 = new Tokenizer("00.");
        assertEquals(Tokens.newNumber("00"), tokenizer1.nextToken());
        assertEquals(Tokens.DOT, tokenizer1.nextToken());

        Tokenizer tokenizer2 = new Tokenizer(".001");
        assertEquals(Tokens.DOT, tokenizer2.nextToken());
        assertEquals(Tokens.newNumber("001"), tokenizer2.nextToken());

        Tokenizer tokenizer3 = new Tokenizer("0.12.34.56");
        assertEquals(Tokens.newNumber("0.12"), tokenizer3.nextToken());
        assertEquals(Tokens.DOT, tokenizer3.nextToken());
        assertEquals(Tokens.newNumber("34.56"), tokenizer3.nextToken());
    }

    @Test
    void eof() {
        assertEquals(Tokens.EOF, new Tokenizer("\n\t     ").nextToken());
        assertEquals(Tokens.EOF, new Tokenizer("").nextToken());
    }

    @Test
    void bool() {
        Tokenizer tokenizer = new Tokenizer("true false!");
        assertEquals(Tokens.TRUE, tokenizer.nextToken());
        assertEquals(Tokens.FALSE, tokenizer.nextToken());
        assertEquals(Tokens.NOT, tokenizer.nextToken());
    }
}