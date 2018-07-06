package dwr.push;

import dwr.DwrScriptSessionManagerUtil;

public class ScriptSessionManager {
	public void init(String type) {
		DwrScriptSessionManagerUtil util = new DwrScriptSessionManagerUtil();
		util.initScriptSession(type);
	}
}
