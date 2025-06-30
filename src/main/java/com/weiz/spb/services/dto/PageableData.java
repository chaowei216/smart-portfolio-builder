package com.weiz.spb.services.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageableData {

    private int pageNumber;
    private int pageSize;
    private int totalPage;
    private long totalRecord;

    public PageableData setPageNumber(final int pageNumber) {

        this.pageNumber = pageNumber + 1;
        return this;
    }

    public PageableData setPageSize(final int pageSize) {

        this.pageSize = pageSize;
        return this;
    }

    public PageableData setTotalPage(final int totalPage) {

        this.totalPage = totalPage;
        return this;
    }

    public PageableData setTotalRecord(final long totalRecord) {

        this.totalRecord = totalRecord;
        return this;
    }
}
