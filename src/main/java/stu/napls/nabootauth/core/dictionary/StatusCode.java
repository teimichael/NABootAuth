package stu.napls.nabootauth.core.dictionary;

import lombok.Getter;

public interface StatusCode {

    @Getter
    enum Identity {
        NORMAL(0), PREREGISTER(1);

        private final int value;

        Identity(int value) {
            this.value = value;
        }
    }

    @Getter
    enum Token {
        NORMAL(0), INVALID(1);

        private final int value;

        Token(int value) {
            this.value = value;
        }
    }

}
