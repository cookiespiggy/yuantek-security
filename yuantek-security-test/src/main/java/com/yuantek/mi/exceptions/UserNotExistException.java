package com.yuantek.mi.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNotExistException extends RuntimeException {

    private String id;

    public UserNotExistException(String id) {
        super("user not exist");
        this.id = id;
    }
}
