package dwr;

import org.directwebremoting.ScriptSessions;

public class PushAll {
	public void sendAll(String funcName, String msg) {
		ScriptSessions.addFunctionCall(funcName, msg);
	}
}
