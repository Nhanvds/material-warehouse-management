package com.demo.mwm.utils;


import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class Constants {

    public static final DateTimeFormatter ISO_DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;
    public static final DateTimeFormatter CUSTOM_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final Boolean IS_ACTIVE_TRUE = Boolean.TRUE;
    public static final String DEFAULT_LANGUAGE = "vi";
    public static final List<Locale> SUPPORTED_LOCATES = List.of(new Locale("vi"), new Locale("en"));
    public static final String EMPTY_STRING = "";
    public interface Paging{
        Integer PAGE_SIZE_ZERO = 0;
        String PAGE_SIZE_DEFAULT = "0";
        String PAGE_NUMBER_DEFAULT = "0";
        String ASC = "ASC";
        String DESC = "DESC";
    }

    public interface Query{
        String PERCENT_SIGN = "%";
    }

    public interface Encryption{
        String RSA = "RSA";
        String AES = "AES";
        String AES_GCM_NO_PADDING = "AES/GCM/NoPadding";
        int AES_KEY_LENGTH = 256;
        int GCM_IV_LENGTH = 12; // 96 bits
        int GCM_TAG_LENGTH = 16; // 128 bits
        int GCM_TAG_LENGTH_BITS = 128;
        int RSA_KEY_LENGTH = 2048;
    }
    public interface MaterialField {
        String ID = "id";
        String MATERIAL_CODE = "materialCode";
        String MATERIAL_NAME = "materialName";
        String MATERIAL_PRICE = "materialPrice";
        String MATERIAL_QUANTITY = "materialQuantity";
        String MATERIAL_NOTE = "materialNote";
        String SUPPLIER_ID = "supplierId";
        String IS_ACTIVE = "isActive";
    }
    public interface MaterialColumn {
        String TABLE_NAME = "materials";
        String ID = "id";
        String MATERIAL_CODE = "material_code";
        String MATERIAL_NAME = "material_name";
        String MATERIAL_PRICE = "material_price";
        String MATERIAL_QUANTITY = "material_quantity";
        String MATERIAL_NOTE = "material_note";
        String SUPPLIER_ID = "supplier_id";
        String IS_ACTIVE = "is_active";
    }
    public interface SupplierField {
        String ID = "id";
        String SUPPLIER_CODE = "supplierCode";
        String SUPPLIER_NAME = "supplierName";
        String SUPPLIER_ADDRESS = "supplierAddress";
        String SUPPLIER_PHONE_NUMBER = "supplierPhoneNumber";
        String SUPPLIER_NOTE = "supplierNote";
        String IS_ACTIVE = "isActive";
    }

    public interface SupplierColumn {
        String TABLE_NAME = "suppliers";
        String ID = "id";
        String SUPPLIER_CODE = "supplier_code";
        String SUPPLIER_NAME = "supplier_name";
        String SUPPLIER_ADDRESS = "supplier_address";
        String SUPPLIER_PHONE_NUMBER = "supplier_phone_number";
        String SUPPLIER_NOTE = "supplier_note";
        String IS_ACTIVE = "is_active";
    }
    public interface Procedure{
        String FIND_SUPPLIER_BY_ID_AND_IS_ACTIVE_TRUE = "find_supplier_by_id_and_is_active_true";
    }



}
