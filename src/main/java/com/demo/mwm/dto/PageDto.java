package com.demo.mwm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageDto<T extends AbstractDto>{

    private List<T> content;
    private Long totalElements;


    public List<T> getContent() {
        return content;
    }

    public PageDto<T> content(List<T> content) {
        this.content = content;
        return this;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public PageDto<T> totalElements(Long totalElements) {
        this.totalElements = totalElements;
        return this;
    }
}
