package com.whk.actor.component;

import com.whk.module.Item;
import io.protostuff.Tag;
import lombok.Data;

import java.util.HashMap;

@Data
public class Bag {
    @Tag(1)
    private HashMap<Long, Item> items = new HashMap<>();
}
