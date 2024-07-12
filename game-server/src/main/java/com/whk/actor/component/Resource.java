package com.whk.actor.component;

import io.protostuff.Tag;
import lombok.Data;

import java.util.HashMap;

@Data
public class Resource {
    @Tag(1)
    private HashMap<Integer, Long> coins = new HashMap<>();

}
