package com.springdata.data.exceptions;

public class SubjectException extends RuntimeException {
    public SubjectException(Iterable<Long> ids) {
        super("Subject(s) cannot be found with id(s) : " + ids);
    }
}
