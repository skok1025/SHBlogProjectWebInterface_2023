package com.cafe24.shkim30.library.trace;

public class TraceStatus {
    private TraceId traceId;
    private Long statusTimeMs;
    private String message;

    public TraceStatus(TraceId traceId, Long statusTimeMs, String message) {
        this.traceId = traceId;
        this.statusTimeMs = statusTimeMs;
        this.message = message;
    }

    public TraceId getTraceId() {
        return traceId;
    }

    public Long getStatusTimeMs() {
        return statusTimeMs;
    }

    public String getMessage() {
        return message;
    }
}
