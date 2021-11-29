package org.example.common.util;

import java.util.UUID;

public class CommonUtils {
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}