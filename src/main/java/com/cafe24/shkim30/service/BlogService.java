package com.cafe24.shkim30.service;

import com.cafe24.shkim30.dto.BlogDTO;
import com.cafe24.shkim30.dto.BlogInsertDTO;
import com.cafe24.shkim30.providor.BlogProvidor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogProvidor blogProvidor;

    public BlogInsertDTO addBlog(BlogInsertDTO blogInsertDTO) {
        return blogProvidor.insertBlog(blogInsertDTO);
    }

    public List<BlogDTO> getMainBlogList(int startIndex) {
        return blogProvidor.getMainBlogList(startIndex);
    }
}
