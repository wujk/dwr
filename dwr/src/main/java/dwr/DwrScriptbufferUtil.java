package dwr;

import org.directwebremoting.ScriptBuffer;

public class DwrScriptbufferUtil {
	public static ScriptBuffer genScriptBuffer(String funcName, String... params) {
		ScriptBuffer script = new ScriptBuffer();
		script.appendScript(funcName).appendScript("(");
		for (int i = 0; i < params.length; i++) {
			if (i != 0) {
				script.appendScript(",");
			}
			script.appendData(params[i]);
		}
		script.appendScript(");");
		return script;
	}
}
