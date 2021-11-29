package org.example.common.util;

import org.example.configuration.ApplicationConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

public class I18nUtils {
    private static final MessageSource MESSAGE_SOURCE = ApplicationConfiguration.getBean(MessageSource.class);

    private I18nUtils() {
    }

    public static String getMessage(String code) {
        return getMessage(code, new Object[]{});
    }

    public static String getMessage(String code, String defaultMessage) {
        return getMessage(code, null, defaultMessage);
    }

    public static String getMessage(String code, String defaultMessage, Locale locale) {
        return getMessage(code, null, defaultMessage, locale);
    }

    public static String getMessage(String code, Locale locale) {
        return getMessage(code, null, "", locale);
    }

    public static String getMessage(String code, Object[] args) {
        return getMessage(code, args, "");
    }

    public static String getMessage(String code, Object[] args, Locale locale) {
        return getMessage(code, args, "", locale);
    }

    public static String getMessage(String code, Object[] args, String defaultMessage) {
        Locale locale = LocaleContextHolder.getLocale();
        return getMessage(code, args, defaultMessage, locale);
    }

    public static String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return MESSAGE_SOURCE.getMessage(code, args, defaultMessage, locale);
    }
}