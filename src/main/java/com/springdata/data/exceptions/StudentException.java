package com.springdata.data.exceptions;

public class StudentException extends RuntimeException {
    public StudentException(Iterable<Long> ids) {
        super("Student(s) cannot be found with id(s) : " + ids);
    }
}
