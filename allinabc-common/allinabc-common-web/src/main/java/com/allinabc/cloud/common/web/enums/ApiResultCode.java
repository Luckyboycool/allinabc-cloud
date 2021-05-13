package com.allinabc.cloud.common.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiResultCode implements ResultCode{

    SUCCESS(20000, "success"),
    FAILED(50000, "failed"),
    FORBIDDEN(50001, "forbidden"),
    VALIDATE_PARAMS_FAILED(50100, "validate params failed"),
    VALIDATE_TOKEN_FAILED(50200, "validate token failed"),
    VALIDATE_AUTH_FAILED(50300, "validate authority failed");

    private final int     code;
    private final String  message;

}
