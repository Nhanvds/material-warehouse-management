package com.demo.mwm.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AuthoritiesConstants {

    public static final String ROLE_ = "ROLE_";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String TOKEN_PREFIX_BEARER_ = "Bearer ";
    public static final int STARTING_POSITION_TOKEN = 7;

    // role
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";

    // permission
    public static final String ADMIN_READ = "ADMIN:READ";
    public static final String ADMIN_CREATE = "ADMIN:CREATE";
    public static final String ADMIN_DELETE= "ADMIN:DELETE";
    public static final String ADMIN_UPDATE = "ADMIN:UPDATE";
    public static final String ADMIN_READ_ALL = "ADMIN:READ_ALL";
    public static final String USER_READ = "USER:READ";
    public static final String USER_CREATE = "USER:CREATE";
    public static final String USER_DELETE = "USER:DELETE";
    public static final String USER_UPDATE = "USER:UPDATE";
    public static final String USER_READ_ALL = "USER:READ_ALL";

    // whitelisted
    public static final List<String> WHITE_LISTED = Arrays.asList(
            "/auth",
            "/swagger-ui-custom.html"
    );


}