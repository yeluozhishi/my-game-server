package com.whk.script.game;

import com.whk.actor.Player;
import com.whk.actor.component.attribute.RoleAttributeManager;
import com.whk.module.LevelModule;

public class LevelScript implements ILevelScript{

    public void levelUp(Player player){
        LevelModule levelModule = player.getPlayerModule().getModule(LevelModule.class);
        levelModule.setLevel(levelModule.getLevel() + 1);
        RoleAttributeManager.INSTANCE.calculateModuleAndRebuild(player.getAttributes(), levelModule);
    }


    public void addExp(){

    }
}
