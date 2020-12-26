package com.agribay.signup_Signin.exception;

public class SpringAgribayException extends RuntimeException {
    public SpringAgribayException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public SpringAgribayException(String exMessage) {
        super(exMessage);
    }
}
