package com.demo.mwm.utils;

import java.util.Arrays;
import java.util.List;

public class AuthoritiesConstants {

    public static final String ROLE_ = "ROLE_";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String TOKEN_PREFIX_BEARER_ = "Bearer ";
    public static final int STARTING_POSITION_TOKEN = 7;

    public enum HTTP_METHOD{
        GET,
        PUT,
        POST,
        DELETE
    }
    public enum ROLE{
        ADMIN,
        USER
    }
    public enum PERMISSION {
        GET_SUPPLIERS_ALL,
        POST_SUPPLIERS_SAVE,
        DELETE_SUPPLIERS_$ID_DELETE,
        GET_SUPPLIERS_$ID_DETAIL,
        PUT_SUPPLIERS_$ID_UPDATE,
        GET_MATERIALS_GET_LIST,
        POST_MATERIALS_SAVE,
        DELETE_MATERIALS_$ID_DELETE,
        GET_MATERIALS_$ID_DETAIL,
        PUT_MATERIALS_$ID_UPDATE,
    }

    public static final List<PERMISSION> ADMIN_PERMISSION = Arrays.asList(
            PERMISSION.GET_SUPPLIERS_ALL,
            PERMISSION.POST_SUPPLIERS_SAVE,
            PERMISSION.DELETE_SUPPLIERS_$ID_DELETE,
            PERMISSION.GET_SUPPLIERS_$ID_DETAIL,
            PERMISSION.PUT_SUPPLIERS_$ID_UPDATE,
            PERMISSION.GET_MATERIALS_GET_LIST,
            PERMISSION.POST_MATERIALS_SAVE,
            PERMISSION.DELETE_MATERIALS_$ID_DELETE,
            PERMISSION.GET_MATERIALS_$ID_DETAIL,
            PERMISSION.PUT_MATERIALS_$ID_UPDATE
    );
    public static final List<PERMISSION> USER_PERMISSION = Arrays.asList(
            PERMISSION.GET_SUPPLIERS_ALL,
            PERMISSION.GET_MATERIALS_GET_LIST
    );



    // whitelisted
    public static final List<String> WHITE_LISTED = Arrays.asList(
            "/auth",
            "/swagger-ui",
            "/v3/api-docs"
    );


}