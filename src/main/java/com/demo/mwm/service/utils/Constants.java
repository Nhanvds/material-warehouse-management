package com.demo.mwm.service.utils;

import org.springframework.http.HttpStatus;

public class Constants {

    public static final Boolean IS_ACTIVE_TRUE = Boolean.TRUE;

    public interface Paging{
        Integer PAGE_SIZE_ZERO = 0;
        String ASC = "ASC";
        String DESC = "DESC";
    }

    public interface responseCode{
        String OK = "200";
    }

}
