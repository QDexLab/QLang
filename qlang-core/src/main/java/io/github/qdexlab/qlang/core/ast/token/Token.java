package io.github.qdexlab.qlang.core.ast.token;

import java.util.Arrays;
import java.util.Objects;

public interface Token {

    String getValue();

    String getType();

    default boolean is(String tokenType) {
        return Objects.equals(getType(), tokenType);
    }

    default boolean in(String... tokenType) {
        return Arrays.asList(tokenType).contains(getType());
    }

    class TokenImpl implements Token {
        private final String value;
        private final String type;

        public TokenImpl(String value, String type) {
            this.value = value;
            this.type = type;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public String getType() {
            return type;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            TokenImpl token = (TokenImpl) o;
            return Objects.equals(value, token.value) && Objects.equals(type, token.type);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, type);
        }

        @Override
        public String toString() {
            return "TokenImpl{" +
                    "value='" + value + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }
}
