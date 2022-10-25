package com.whk.network_param;

import java.util.HashMap;
import java.util.Map;

public class MapBean extends HashMap<String, Object> {

    public MapBean(){

    }

    /**
     * 返回错误信息
     * @param code
     */
    public MapBean(IServerError code){
        super.put(code.getErrorCode(), code.getErrorDesc());
    }

    public MapBean(Map map){
        super.putAll(map);
    }

}
