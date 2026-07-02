package com.whk.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.whk.message.MapBean;


/**
 * 全局异常
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionCatch extends Throwable {

    @ResponseBody
    @ExceptionHandler(value = Throwable.class)
    public MapBean exceptionHandler(Throwable ex){
        MapBean mapBean = new MapBean();
        // 自定义异常 取异常信息返回给客户端
        if (ex instanceof FastGameErrorException fastGameErrorException){
            mapBean.setTip(fastGameErrorException.getCode(), fastGameErrorException.getMessage());
        } else if (ex instanceof GameErrorException gameErrorException){
            ex.printStackTrace();
            mapBean.setTip(gameErrorException.getCode(), gameErrorException.getMessage());
        } else if (ex instanceof ClientAbortException ||
                ex instanceof HttpMessageNotWritableException ||
                (ex.getCause() != null && ex.getCause() instanceof java.io.IOException)) {
            log.warn("客户端连接中断: {}", ex.getMessage());
        } else {
            ex.printStackTrace();
        }

        return mapBean;
    }
}
