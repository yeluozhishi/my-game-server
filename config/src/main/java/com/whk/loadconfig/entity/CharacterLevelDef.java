package com.whk.loadconfig.entity;

import com.whk.loadconfig.annotation.Column;
import com.whk.loadconfig.convert.JsonObjectConvertor;
import com.whk.loadconfig.convert.PoundArrayConvertor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class CharacterLevelDef {
    public int id;
    public int level;
    @Column(convertor = PoundArrayConvertor.PoundToIntegerList.class)
    public List<Integer> career;
    @Column(convertor = JsonObjectConvertor.class)
    public HashMap<String, Long> attribute;
}
