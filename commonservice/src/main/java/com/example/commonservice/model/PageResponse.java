package com.example.commonservice.model;

import lombok.Data;

import java.util.Collections;
import java.util.List;
import org.springframework.data.domain.Page;
@Data
public class PageResponse<T> {
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
}
