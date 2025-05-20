package com.github.qdexlab.qlang.core.ast;

public abstract class Iterator<T> {
    private int pos;
    private final T[] elements;

    public Iterator(T[] elements) {
        this.pos = 0;
        this.elements = elements;
    }

    public void advance() {
        this.pos++;
    }

    public void advance(int count) {
        this.pos += count;
    }

    public T peek() {
        return elements[this.pos];
    }

    public T peek(int next) {
        return elements[this.pos + next];
    }

    public T next() {
        return elements[this.pos + 1];
    }

    public boolean hasEnough(int n) {
        return pos + n - 1 < elements.length;
    }

    public boolean has() {
        return pos < elements.length;
    }
}
