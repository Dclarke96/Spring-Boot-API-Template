package com.dylanclarke.springbootapitemplate.api;

import java.util.List;

import org.springframework.data.domain.Page;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Standardized pagination response containing the requested page of data and pagination metadata."
)
public class PageResponse<T> {

    @Schema(
            description = "Data contained in the current page."
    )
    private final List<T> content;

    @Schema(
            description = "Zero-based index of the current page.",
            example = "0"
    )
    private final int page;

    @Schema(
            description = "Number of records requested per page.",
            example = "20"
    )
    private final int size;

    @Schema(
            description = "Total number of records matching the request.",
            example = "47"
    )
    private final long totalElements;

    @Schema(
            description = "Total number of available pages.",
            example = "3"
    )
    private final int totalPages;

    public PageResponse(Page<T> pageData) {
        this.content = pageData.getContent();
        this.page = pageData.getNumber();
        this.size = pageData.getSize();
        this.totalElements = pageData.getTotalElements();
        this.totalPages = pageData.getTotalPages();
    }

    public List<T> getContent() {
        return content;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
