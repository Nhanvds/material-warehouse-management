package com.demo.mwm.utils;


import java.util.List;
import java.util.Locale;

public class Constants {

    public static final Boolean IS_ACTIVE_TRUE = Boolean.TRUE;
    public static final String DEFAULT_LANGUAGE = "vi";
    public static final List<Locale> SUPPORTED_LOCATES = List.of(new Locale("vi"), new Locale("en"));

    public interface Paging{
        Integer PAGE_SIZE_ZERO = 0;
        String PAGE_SIZE_DEFAULT = "0";
        String PAGE_NUMBER_DEFAULT = "0";
        String ASC = "ASC";
        String DESC = "DESC";
    }

    public interface Query{

    }
    public interface Procedure{
        String FIND_SUPPLIER_BY_ID_AND_IS_ACTIVE_TRUE = "find_supplier_by_id_and_is_active_true";
    }

    public interface responseCode{
        String OK = "200";
    }

}
