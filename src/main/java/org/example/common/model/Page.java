package org.example.common.model;

import lombok.Data;

@Data
public class Page {
    private int pageNumber;
    private int pageSize;
    private int currentPageNumber;
    private int total;
    private Object data;
}