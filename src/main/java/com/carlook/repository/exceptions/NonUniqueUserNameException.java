package com.carlook.repository.exceptions;

public class NonUniqueUserNameException extends Exception {

    @Override
    public String getMessage(){

        return "The user name already exists";
    }
}
