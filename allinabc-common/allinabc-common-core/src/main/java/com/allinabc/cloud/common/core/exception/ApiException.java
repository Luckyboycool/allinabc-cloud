package com.allinabc.cloud.common.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE, reason = "Service Unavailable")
public class ApiException extends RuntimeException {

    private static final String MSG_TEMPLATE      = "Service%s Unavailable";

    public ApiException(String serviceName) {
        super(String.format(MSG_TEMPLATE, " " + serviceName));
    }

    public ApiException() {
        super(String.format(MSG_TEMPLATE, ""));
    }

}
