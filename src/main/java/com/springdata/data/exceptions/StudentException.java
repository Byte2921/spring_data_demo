package com.springdata.data.exceptions;

public class StudentException extends RuntimeException {
    public StudentException(Iterable<Long> id) {
        super("Student(s) cannot be found with id(s) : " + id);
    }
}
