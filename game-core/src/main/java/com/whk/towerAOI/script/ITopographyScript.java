package com.whk.towerAOI.script;

import com.whk.entity.MapDef;
import com.whk.towerAOI.entity.Topography;
import script.scriptInterface.IScript;

public interface ITopographyScript extends IScript {
    void initTopography(Topography topography, MapDef mapDef);
}
