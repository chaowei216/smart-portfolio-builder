package com.weiz.spb.services.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PagingResponse<T> {

    private List<T> contents = new ArrayList<>();
    private PageableData paging;

    public PagingResponse<T> setContents(final List<T> contents) {

        this.contents = contents;
        return this;
    }

    public PagingResponse<T> setPaging(final PageableData paging) {

        this.paging = paging;
        return this;
    }
}

