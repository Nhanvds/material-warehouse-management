package com.demo.mwm.utils;

import com.demo.mwm.dto.PageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


public class Utils {

    public static Pageable getPageable(Integer page, Integer size) {
        if (size.equals(Constants.Paging.PAGE_SIZE_ZERO)) {
            return Pageable.unpaged();
        }
        return PageRequest.of(page, size);
    }

}
