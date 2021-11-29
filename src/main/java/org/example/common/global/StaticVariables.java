package org.example.common.global;

public interface StaticVariables {
    String ACCESS_CONTROL_ALLOW_ORIGIN_HEADER = "Access-Control-Allow-Origin";
    String ACCESS_CONTROL_ALLOW_ORIGIN_VALUE = "*";
    String CACHE_CONTROL_HEADER = "Cache-Control";
    String CACHE_CONTROL_VALUE = "no-cache";
    String CHARACTER_ENCODING = "UTF-8";
    String CONTENT_TYPE = "application/json";

    String VERIFY_CODE_REGEX = "\\d{6}";
    String PHONE_REGEX = "^(13[0-9]|14[5|7]|15[0|123456789]|18[0|123456789])\\d{8}$";
    String EMAIL_REGEX = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
}