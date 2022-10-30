package org.example.cache;

import org.example.entity.Resource;

import java.util.List;

public interface ResourceCacheService {
    List<Resource> getResources();

    void setResource(List<Resource> resources);
}