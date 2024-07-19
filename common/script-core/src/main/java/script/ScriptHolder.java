package script;

import script.annotation.Script;
import script.scriptInterface.IScript;

public enum ScriptHolder {
    INSTANCE;

    private final ScriptEngine scriptEngine = new ScriptEngine();

    public void init() {
        scriptEngine.reload(Script.class, null);
    }

    public <T extends IScript> T getScript(Class<T> key) {
        return scriptEngine.getScript(key);
    }
}
