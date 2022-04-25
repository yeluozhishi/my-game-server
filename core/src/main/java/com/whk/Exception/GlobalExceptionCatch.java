package com.whk.Exception;

import com.whk.network_param.IServerError;
import com.whk.network_param.ResponseEntity;
import com.whk.network_param.WebCenterError;
import com.whk.util.GsonUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.logging.Logger;


/**
 * 全局异常
 */
@ControllerAdvice
public class GlobalExceptionCatch{
    private static Logger logger = Logger.getLogger(GlobalExceptionCatch.class.getName());

    @ResponseBody
    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<String> exceptionHandler(Throwable ex){
        IServerError serverError;
        ex.printStackTrace();
        // 自定义异常 取异常信息返回给客户端
        if (ex instanceof GameErrorException gameErrorException){
            serverError = gameErrorException.getError();
        } else {
            serverError = WebCenterError.UNKNOWN;
        }
        Map map = Map.of("errorCode", serverError.getErrorCode(), "errorDesc", ex.getMessage());
        logger.severe(String.valueOf(map));
        ResponseEntity<String> responseEntity = new ResponseEntity<>(serverError);
        responseEntity.setData(GsonUtil.INSTANCE.GsonString(map));
        return responseEntity;
    }
}
