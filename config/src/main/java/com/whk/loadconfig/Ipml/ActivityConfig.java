package com.whk.loadconfig.Ipml;

import com.whk.db.entity.ActivityDef;
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
        var temp = new HashMap<Integer, ActivityDef>();
        linkedList.forEach(d -> temp.put(d.id, d));
        if (!temp.isEmpty()){
            map = temp;
        }
    }
}
