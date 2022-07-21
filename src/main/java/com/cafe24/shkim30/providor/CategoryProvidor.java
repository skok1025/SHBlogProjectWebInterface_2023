package com.cafe24.shkim30.providor;

import com.cafe24.shkim30.dto.CategoryDTO;
import com.cafe24.shkim30.dto.JSONResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryProvidor {
    private final RestTemplate restTemplate;

    @Value("${constant.backendUrl}")
    public String BACKEND_URL;

    public List<CategoryDTO> getCategoryList(Long loginUserNo) {
        JSONResultCategoryList jsonresult =
                restTemplate.getForObject(BACKEND_URL + "/blog-api/blog/category-list?member_no="+ loginUserNo
                        , JSONResultCategoryList.class);

        return jsonresult.getData();
    }

    private static class JSONResultCategoryList extends JSONResult<List<CategoryDTO>> {}
}
