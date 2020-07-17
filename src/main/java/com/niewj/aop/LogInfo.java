package com.niewj.aop;

import com.google.gson.Gson;
import lombok.Data;

@Data
public class LogInfo {
    private String bizMethodName;
    private String bizMethodParamsJson;
    private long logTime;
    private LogTypeEnum logType;
    private Exception exception;
    private String returnJson;

    @Override
    public String toString() {
        return "[" + this.logType.getValue() + "]==>\t" + new Gson().toJson(this);
    }
}
