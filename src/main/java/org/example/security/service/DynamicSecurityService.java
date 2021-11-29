package org.example.security.service;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

public interface DynamicSecurityService {
    Map<String, ConfigAttribute> loadSource();
}