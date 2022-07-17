package com.cafe24.shkim30.service;

import com.cafe24.shkim30.dto.BlogDTO;
import com.cafe24.shkim30.dto.BlogInsertDTO;
import com.cafe24.shkim30.providor.BlogProvidor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.cafe24.shkim30.library.libFrontPaging;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogProvidor blogProvidor;

    private int listSize = 5;
    private int pageSize = 5;

    public BlogInsertDTO addBlog(BlogInsertDTO blogInsertDTO) {
        return blogProvidor.insertBlog(blogInsertDTO);
    }

    public List<BlogDTO> getMainBlogList(Integer currentPage) {
        Integer totalCount = 100; // TODO 총 블로그 개수 작업필요.
        Integer startIndex = libFrontPaging.getStartRecordNum(currentPage, totalCount, pageSize);

        log.info("currentPage: {}", currentPage);
        log.info("startIndex : {}", startIndex);

        return blogProvidor.getMainBlogList(startIndex);
    }

    public Object getPaging(Integer currentPage) {
        Integer totalCount = 100; // TODO 총 블로그 개수 작업필요.
        return libFrontPaging.getPagingVariable(currentPage, totalCount, pageSize, listSize);
    }
}
