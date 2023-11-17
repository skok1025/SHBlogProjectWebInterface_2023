package com.cafe24.shkim30.service;

import com.cafe24.shkim30.dto.fconline.FcOnlineInfoDTO;
import com.cafe24.shkim30.providor.FcOnlineProvidor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FcOnlineService {
    private final FcOnlineProvidor fcOnlineProvidor;

    public FcOnlineInfoDTO getInfoDTO(String fcOnlineUserName) {
        return fcOnlineProvidor.getInfoDTO(fcOnlineUserName);
    }
}
