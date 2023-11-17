package com.cafe24.shkim30.dto.fconline;

import lombok.Data;

@Data
public class MaxDivisionInfoDTO {
    public MaxDivisionInfoDTO() {
    }

    private Integer matchType;
    private Integer division;
    private String matchTypeName;
    private String divisionName;
    private String achievementDate;
}
