package stu.napls.nabootauth.core.dictionary;

import lombok.Getter;

public interface StatusConst {

    @Getter
    enum Identity {
        NORMAL(StatusCode.NORMAL), PREREGISTER(1);

        private final int value;

        Identity(int value) {
            this.value = value;
        }
    }

    @Getter
    enum Token {
        NORMAL(StatusCode.NORMAL), INVALID(1);

        private final int value;

        Token(int value) {
            this.value = value;
        }
    }

}
