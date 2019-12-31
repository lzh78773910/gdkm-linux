package com.gdkm.vo;

import com.gdkm.model.User;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PageVo<T> {

    private Integer  totalPage;
    private Integer  currentPage;
    List<T> list;
}
