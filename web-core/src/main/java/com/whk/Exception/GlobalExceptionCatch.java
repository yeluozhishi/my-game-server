package com.whk.Exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.whk.message.MapBean;


/**
 * 全局异常
 */
@ControllerAdvice
public class GlobalExceptionCatch extends Throwable {

    @ResponseBody
    @ExceptionHandler(value = Throwable.class)
    public MapBean exceptionHandler(Throwable ex){
        MapBean mapBean = new MapBean();
        // 自定义异常 取异常信息返回给客户端
        if (ex instanceof FastGameErrorException fastGameErrorException){
            mapBean.setErr(fastGameErrorException.getCode(), fastGameErrorException.getMessage());
        } else if (ex instanceof GameErrorException gameErrorException){
            ex.printStackTrace();
            mapBean.setErr(gameErrorException.getCode(), gameErrorException.getMessage());
        } else {
            ex.printStackTrace();
        }

        return mapBean;
    }
}
