package com.agribay.agribayapp.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpringAgribayException extends RuntimeException {
    public SpringAgribayException(String exMessage, Exception exception) {
        log.error(exMessage,exception);
    }

    public SpringAgribayException(String exMessage) {
        log.error(exMessage);
    }
}
