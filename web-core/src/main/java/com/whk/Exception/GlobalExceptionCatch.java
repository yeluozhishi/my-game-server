package com.whk.Exception;

import com.whk.network_param.MapBean;
import com.whk.network_param.WebCenterError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.logging.Logger;


/**
 * 全局异常
 */
@ControllerAdvice
public class GlobalExceptionCatch extends Throwable {
    private static final Logger logger = Logger.getLogger(GlobalExceptionCatch.class.getName());

    @ResponseBody
    @ExceptionHandler(value = Throwable.class)
    public MapBean exceptionHandler(Throwable ex){
        // 自定义异常 取异常信息返回给客户端
        ex.printStackTrace();
        return new MapBean(WebCenterError.UNKNOWN);
    }
}
