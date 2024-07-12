package com.whk.loadconfig.entity;

import com.whk.loadconfig.annotation.Column;
import com.whk.loadconfig.convert.JsonObjectConverter;
import com.whk.loadconfig.convert.PoundArrayConverters;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class CharacterLevelDef {
    public int id;
    public int level;
    @Column(converter = PoundArrayConverters.PoundToIntegerList.class)
    public List<Integer> career;
    @Column(converter = JsonObjectConverter.class)
    public HashMap<String, Long> attribute;
}
