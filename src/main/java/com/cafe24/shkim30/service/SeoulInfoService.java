package com.cafe24.shkim30.service;

import com.cafe24.shkim30.dto.seoulinfo.CultureInfoDTO;
import com.cafe24.shkim30.providor.SeoulInfoProvidor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeoulInfoService {
    private final SeoulInfoProvidor seoulInfoProvidor;

    public CultureInfoDTO getCultureList() {
        return seoulInfoProvidor.selectCulttureList();
    }
}
