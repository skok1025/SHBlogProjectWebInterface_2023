package com.cafe24.shkim30.library;

import com.cafe24.shkim30.library.trace.TraceStatus;

public interface LogTrace {
    TraceStatus begin(String message);
    void end(TraceStatus status);
    void exception(TraceStatus status, Exception e);
}
