package script;

import script.annotation.Script;
import script.scriptInterface.IScript;

public enum ScriptHolder {
    INSTANCE;

    private final ScriptEngine scriptEngine = new ScriptEngine();


    public void init(boolean dev,String artifactId, String scriptArtifactId) {
        if (dev){
            scriptEngine.reload(Script.class, "com.whk.script", artifactId, scriptArtifactId);
        } else {
            scriptEngine.reload("E:\\");
        }
    }

    public <T extends IScript> T getScript(Class<T> key) {
        return scriptEngine.getScript(key);
    }
}
