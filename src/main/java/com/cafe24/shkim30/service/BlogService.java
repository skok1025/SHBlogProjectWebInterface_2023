package com.cafe24.shkim30.service;

import com.cafe24.shkim30.dto.BlogDTO;
import com.cafe24.shkim30.dto.BlogInsertDTO;
import com.cafe24.shkim30.library.LogTrace;
import com.cafe24.shkim30.library.trace.TraceStatus;
import com.cafe24.shkim30.providor.BlogProvidor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.cafe24.shkim30.library.libFrontPaging;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlogService {
    @Value("${spring.profiles.active}")
    public String PROFILES_ACTIVE;
    private final BlogProvidor blogProvidor;

    private int listSize = 5;
    private int pageSize = 5;

    public BlogInsertDTO addBlog(BlogInsertDTO blogInsertDTO) {
        return blogProvidor.insertBlog(blogInsertDTO);
    }

    public List<BlogDTO> getMainBlogList(Integer currentPage, Integer categoryNo) {
        Integer totalCount = 5000; // TODO 총 블로그 개수 작업필요.
        Integer startIndex = libFrontPaging.getStartRecordNum(currentPage, totalCount, pageSize);

        List<BlogDTO> mainBlogList = blogProvidor.getMainBlogList(startIndex, categoryNo);

        return mainBlogList;
    }

    public Object getPaging(Integer currentPage) {
        Integer totalCount = 5000; // TODO 총 블로그 개수 작업필요.
        Object pagingVariable = libFrontPaging.getPagingVariable(currentPage, totalCount, pageSize, listSize);

        return pagingVariable;
    }
}
