package com.springdata.data.exceptions;

public class LaptopException extends RuntimeException {
    public LaptopException(Iterable<Long> ids){
        super("Laptop(s) cannot be found with id(s) : " + ids);
    }
}
