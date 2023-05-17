package com.task.blog.taskblog.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 3545937571146934479L;

    private String message;
    private String[] varValues;

    public ValidationException(String message, Throwable cause, String... varValues) {
        super(message, cause);
        this.message = message;
        this.varValues = varValues;
    }

    public ValidationException(String message, String... varValues) {
        super(message);
        this.message = message;
        this.varValues = varValues;
    }

    public ValidationException(String message, Throwable cause) {
        super(cause);
        this.message = message;
    }

}
