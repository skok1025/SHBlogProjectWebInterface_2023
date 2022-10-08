package com.cafe24.shkim30.library;

import com.cafe24.shkim30.library.trace.TraceId;
import com.cafe24.shkim30.library.trace.TraceStatus;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
public class libTrace implements LogTrace {
    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    private ThreadLocal<TraceId> traceId = new ThreadLocal<>();

    public TraceStatus begin(String message) {
        syncTraceInfo();
        TraceId traceId = this.traceId.get();
        Long startTimems = System.currentTimeMillis();

        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);

        // 로그 출력
        return new TraceStatus(traceId, startTimems, message);
    }

    private void syncTraceInfo() {
        TraceId traceInfo = this.traceId.get(); // 이미 기존의 trace 정보

        if (traceInfo == null) {
            this.traceId.set(new TraceId());
        } else {
            this.traceId.set(traceInfo.createNextId());
        }
    }

    public void end(TraceStatus status) {
        complete(status, null);
    }

    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    private void complete(TraceStatus status, Exception e) {
        Long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStatusTimeMs();
        TraceId traceId = status.getTraceId();
        if (e == null) {
            log.info("[{}] {}{} time={}ms", traceId.getId(),
                    addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(),
                    resultTimeMs);
        } else {
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(),
                    addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs,
                    e.toString());

            this.traceId.remove();
        }

        realeaseTraceId();
    }

    private void realeaseTraceId() {
        TraceId traceId = this.traceId.get();
        if (traceId.isFirstLevel()) {
            this.traceId.remove();
        } else {
            this.traceId.set(traceId.createPreviousId());
        }
    }

    private static String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append( (i == level - 1) ? "|" + prefix : "| ");
        }
        return sb.toString();
    }
}
