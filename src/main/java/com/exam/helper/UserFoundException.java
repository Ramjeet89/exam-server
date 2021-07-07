package com.exam.helper;

import com.exam.constant.ExamPortalConstant;

public class UserFoundException extends Exception {

    public UserFoundException() {
        super(ExamPortalConstant.USER_NAME_ALREADY);
    }

    public UserFoundException(String msg) {
        super(msg);
    }
}
