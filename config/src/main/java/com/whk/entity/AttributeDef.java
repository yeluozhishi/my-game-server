package com.whk.entity;

import com.whk.loadconfig.IDefine;
import com.whk.loadconfig.annotation.Column;
import com.whk.loadconfig.convert.JsonObjectConvertor;
import com.whk.loadconfig.convert.PoundArrayConvertor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class AttributeDef implements IDefine {
    public String id;
    public int attrId;
}
