package com.cafe24.shkim30.dto.seoulinfo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CultureInfoDTO {

    String collect_date;
    Integer total_count;
    List<Map<String,String>> all_event_list;
}
