package com.whk.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.LayoutBase;
import cn.hutool.json.JSONObject;
import com.whk.StackUtil;

public class LogbackExceptionLayout extends LayoutBase<ILoggingEvent> {
    @Override
    public String doLayout(ILoggingEvent event) {
        try {
            IThrowableProxy throwableProxy = event.getThrowableProxy();
            if (throwableProxy == null) {
                return "";
            }
            String threadName = event.getThreadName();
            String loggerName = event.getLoggerName();
            StackTraceElement[] stackTraces = event.getCallerData();
            int time = (int) (event.getTimeStamp() / 1000);
            String msg = throwableProxy.getMessage();
            String clazz = throwableProxy.getClassName();

            String trace = StackUtil.stackToString(stackTraces);
            JSONObject json = new JSONObject();
            json.set("threadName", threadName);
            json.set("loggerName", loggerName);
            json.set("clazz", clazz);
            json.set("msg", msg);
            json.set("trace", trace);
            json.set("time", time);

            String jsonString = json.toJSONString(0);
            return jsonString + CoreConstants.LINE_SEPARATOR;
        } catch (Exception e) {
            return "";
        }
    }


}
