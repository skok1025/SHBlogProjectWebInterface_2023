package com.cafe24.shkim30.dto.seoulinfo;


import lombok.Data;

@Data
public class CultureDetailDTO {
    private String EVENT_NM;
    private String EVENT_PERIOD;
    private String EVENT_PLACE;
    private String EVENT_X;
    private String EVENT_Y;
    private String EVENT_AREA_CODE;
    private String EVENT_ETC_DETAIL; // 추가되거나 변경된 부분
}