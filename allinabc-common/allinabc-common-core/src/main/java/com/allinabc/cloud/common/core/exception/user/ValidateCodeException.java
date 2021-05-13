package com.allinabc.cloud.common.core.exception.user;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ValidateCodeException extends Exception {

    private static final long serialVersionUID = 3887472968823615091L;

    public ValidateCodeException(String msg) {
        super(msg);
    }

}