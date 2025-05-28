package com.example.commonservice.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import org.springframework.data.domain.Page;
@Data
public class PageResponse<T> implements Serializable {
    int currentPage;
    int totalPage;
    int pageSize;
    Long totalElements;
    private List<T> data = Collections.emptyList();

    public PageResponse(Page<T> page) {
        this.currentPage = page.getNumber();
        this.totalPage = page.getTotalPages();
        this.pageSize = page.getSize();
        this.totalElements = page.getTotalElements();
        this.data = page.getContent();
    }
    public PageResponse(int page, int size, long totalElements, int totalPages, List<T> content) {
        this.currentPage = page;
        this.pageSize = size;
        this.totalElements = totalElements;
        this.totalPage = totalPages;
        this.data = content;
    }
}
