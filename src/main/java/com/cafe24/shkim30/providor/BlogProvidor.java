package com.cafe24.shkim30.providor;

import com.cafe24.shkim30.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BlogProvidor {
    private final RestTemplate restTemplate;

    @Value("${constant.backendUrl}")
    public String BACKEND_URL;

    public BlogInsertDTO insertBlog(BlogInsertDTO blogInsertDTO) {
        JSONResultBlogInserDTO jsonresult =
                restTemplate.postForObject(BACKEND_URL + "/blog-api/blog/contents", blogInsertDTO, JSONResultBlogInserDTO.class);

        return jsonresult.getData();
    }

    public List<BlogDTO> getMainBlogList(int startIndex, Integer categoryNo, String keyword) {
        String callUrl = BACKEND_URL + "/blog-api/blog/contents-list?page_content_size=5&start_index=" + startIndex;

        if (categoryNo != null) {
            callUrl += "&category_no=" + categoryNo;
        }

        if (keyword != null) {
            callUrl += "&keyword=" + keyword;
        }

        JSONResultBlogDTOList jsonresult =
                restTemplate.getForObject(
                        callUrl ,
                        JSONResultBlogDTOList.class
                );

        return jsonresult.getData();
    }

    public BlogDTO getBlog(Long blogNo) {
        String callUrl = BACKEND_URL + "/blog-api/blog/contents/" + blogNo;
        JSONResultBlogDTO jsonresult =
                restTemplate.getForObject(
                        callUrl,
                        JSONResultBlogDTO.class
                );

        return jsonresult.getData();
    }

    public void updateBlog(BlogUpdateDTO blogUpdateDTO) {
        String callUrl = BACKEND_URL + "/blog-api/blog/contents/";
        restTemplate.put(callUrl, blogUpdateDTO);
    }

    private static class JSONResultBlogInserDTO extends JSONResult<BlogInsertDTO> {}
    private static class JSONResultBlogDTO extends JSONResult<BlogDTO> {}
    private static class JSONResultBlogDTOList extends JSONResult<List<BlogDTO>> {}

    private static class JSONResultBlogUpdateDTO extends JSONResult<BlogUpdateDTO> {}
}
