package com.cafe24.shkim30.dto.seoulinfo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CultureInfoDTO {

    String collect_date;
    Integer total_count;

    // TODO List<CultureDetailDTO> 로 받아오면 DTO 필드들이 모두 null 이 들어오는데.. 왜 그런지 모르겠음
    List<Map<String,String>> all_event_list;
}
