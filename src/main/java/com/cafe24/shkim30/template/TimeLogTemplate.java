package com.cafe24.shkim30.template;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeLogTemplate {

    public <T> T execute(String checkPage, TimeLogCallback<T> callback) throws Exception {
        long startTime = System.currentTimeMillis();
        T result = callback.call();
        long endTime = System.currentTimeMillis();

        log.info("checkPage={}, resultTime={}ms", checkPage, endTime - startTime);

        return result;
    }
}
