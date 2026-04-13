package com.whk.config;

import com.whk.entity.AttributeDef;
import com.whk.loadconfig.AbstractConfig;
import com.whk.loadconfig.annotation.ConfigInit;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ConfigInit(fileName = "Ui_word_attribute")
@Getter
public class AttributeConfig extends AbstractConfig<AttributeDef> {
    @Getter
    public static AttributeConfig instance = new AttributeConfig();

    private Map<String, AttributeDef> attributeDefs = new HashMap<>();

    @Override
    public void afterLoad(List<AttributeDef> list) {
        attributeDefs = list.stream().collect(Collectors.toMap(AttributeDef::getId, f -> f));
    }


    @Override
    public void setInstance() {
        instance = this;
    }

}
