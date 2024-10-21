package script;

import script.annotation.Script;
import script.scriptInterface.IScript;

public enum ScriptHolder {
    INSTANCE;

    private final ScriptEngine scriptEngine = new ScriptEngine();


    public void init(boolean dev, String pathInModule) {
        if (dev){
            scriptEngine.reload(Script.class, pathInModule);
        } else {
            scriptEngine.reload("E:\\script-gate-1.0-SNAPSHOT.jar");
        }
    }

    public <T extends IScript> T getScript(Class<T> key) {
        return scriptEngine.getScript(key);
    }

    public ScriptEngine getScriptEngine() {
        return scriptEngine;
    }
}
