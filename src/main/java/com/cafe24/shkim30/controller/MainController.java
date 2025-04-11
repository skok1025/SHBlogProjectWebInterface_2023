package com.cafe24.shkim30.controller;

import com.cafe24.shkim30.dto.CategoryDTO;
import com.cafe24.shkim30.dto.fconline.FcOnlineInfoDTO;
import com.cafe24.shkim30.dto.seoulinfo.CultureInfoDTO;
import com.cafe24.shkim30.service.BlogService;
import com.cafe24.shkim30.service.CategoryService;
import com.cafe24.shkim30.service.FcOnlineService;
import com.cafe24.shkim30.service.SeoulInfoService;
import com.cafe24.shkim30.template.TimeLogTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final BlogService blogService;
    private final CategoryService categoryService;
    private final FcOnlineService fcOnlineService;
    private final SeoulInfoService seoulInfoService;

    private final TimeLogTemplate timeLogTemplate;

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${constant.fconlineUserName}")
    public String fcOnlineUserName;


    @GetMapping("/basic-template")
    public String basicTemplate() {
        return "basic-template";
    }

    @GetMapping({"/", "/index"})
    public String indexPage(
            Model model,
            HttpSession httpSession,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "") Integer category_no,
            @RequestParam(required = false) String keyword) {
        return timeLogTemplate.execute("MainPage", () -> {
            if (httpSession != null) {
                model.addAttribute("blogList", blogService.getMainBlogList(currentPage, category_no, keyword));
                model.addAttribute("pagination", blogService.getPaging(currentPage));
            }

            List<CategoryDTO> categoryList = categoryService.getCategoryList(null);
            model.addAttribute("categoryList", categoryList);
            model.addAttribute("category_no", category_no);
            model.addAttribute("keyword", keyword);


            FcOnlineInfoDTO fcOnlineInfoDTO = fcOnlineService.getInfoDTO(fcOnlineUserName);
            model.addAttribute("fcOnlineInfoDTO", fcOnlineInfoDTO);

            System.out.println(fcOnlineInfoDTO);

            // redis 에서 주식정보 가져오기
            HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();

            model.addAttribute("s_ptodayInfo", hashOps.get("stock:S&P500", "today_info"));
            model.addAttribute("s_pexdayInfo", hashOps.get("stock:S&P500", "exday_info"));
            model.addAttribute("s_ptimeInfo", hashOps.get("stock:S&P500", "time_info"));

            model.addAttribute("nasdaq_todayInfo", hashOps.get("stock:NASDAQ", "today_info"));
            model.addAttribute("nasdaq_exdayInfo", hashOps.get("stock:NASDAQ", "exday_info"));
            model.addAttribute("nasdaq_timeInfo", hashOps.get("stock:NASDAQ", "time_info"));

            model.addAttribute("dow_todayInfo", hashOps.get("stock:DOW", "today_info"));
            model.addAttribute("dow_exdayInfo", hashOps.get("stock:DOW", "exday_info"));
            model.addAttribute("dow_timeInfo", hashOps.get("stock:DOW", "time_info"));

            return "index";
        });
    }

    @GetMapping("/seoul-culture-list")
    public String seoulCultureList(Model model) {
        CultureInfoDTO cultureInfoDTO = seoulInfoService.getCultureList();
        System.out.println(cultureInfoDTO);

        model.addAttribute("cultureInfoDTO", cultureInfoDTO);

        return "seoul-culture-list";
    }

    @GetMapping("/my-career")
    public String myCareer() {
        return "my-career";
    }

    @GetMapping("/lunch-select")
    public String lunchSelect(Model model) {
        return "lunch-select";
    }

}
