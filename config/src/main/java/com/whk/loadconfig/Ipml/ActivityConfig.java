package com.whk.loadconfig.Ipml;

import com.whk.entity.ActivityDef;
import com.whk.loadconfig.FileConfig;
import com.whk.loadconfig.annotation.ConfigInit;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author Administrator
 */
@ConfigInit(fileName = "activity")
public class ActivityConfig extends FileConfig<ActivityDef> {

    public static Map<Integer, ActivityDef> map = new HashMap<>();

    @Override
    protected void afterLoad(LinkedList<ActivityDef> linkedList) {
        linkedList.forEach(d -> map.put(d.id, d));
    }
}
