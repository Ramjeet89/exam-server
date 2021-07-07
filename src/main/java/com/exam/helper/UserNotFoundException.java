package com.exam.helper;

import com.exam.constant.ExamPortalConstant;

public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
        super(ExamPortalConstant.USER_NAME_NOT_FOUND);
    }

    public UserNotFoundException(String msg) {
        super(msg);
    }
}
