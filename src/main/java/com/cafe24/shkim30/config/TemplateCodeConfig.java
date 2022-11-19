package com.cafe24.shkim30.config;

import com.cafe24.shkim30.template.TimeLogTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * 탬플릿 형태의 코드들을 bean 으로 등록하는 config
 * @package com.cafe24.shkim30.config
 * @author  Kim Seok Hyun < skok1025@naver.com >
 * @since   2022. 11. 19
 * @version 1.0
 */
@Configuration
public class TemplateCodeConfig {

    /**
     * 함수의 실행시간을 측정하는 탬플릿 (endTime - startTime, 측정단위:ms)
     * @return TimeLogTemplate
     */
    @Bean
    public TimeLogTemplate timeLogTemplate() {
        return new TimeLogTemplate();
    }
}
