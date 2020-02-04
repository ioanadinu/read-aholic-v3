package com.app.readaholicv3.dto;

import java.util.List;

public class CustomPageImpl<T> {
    public List<T> content;
    public Integer totalPages;
    public Long totalElements;

    public CustomPageImpl() {
    }

    public CustomPageImpl(List<T> content, Integer totalPages, Long totalElements) {
        this.content = content;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }
}
