package com.conference.demo.util;

import org.springframework.data.domain.Page;

public final class Pagination {
    private Pagination() {
    }

    public static int getStartPage(Page<?> pages) {
        int start = Math.max(1, pages.getNumber());
        if (pages.getNumber() == pages.getTotalPages() - 1 && pages.getNumber() > 1)
            start--;
        return start;
    }

    public static int getLastPage(Page<?> pages) {
        int start = Math.max(1, pages.getNumber());
        return Math.min(start + 2, pages.getTotalPages());
    }
}
