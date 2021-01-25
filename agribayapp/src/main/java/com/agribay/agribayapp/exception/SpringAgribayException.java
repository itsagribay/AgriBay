package com.agribay.agribayapp.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpringAgribayException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SpringAgribayException(String exMessage, Exception exception) {
        log.error(exMessage,exception);
    }

    public SpringAgribayException(String exMessage) {
        log.error(exMessage);
    }
}
