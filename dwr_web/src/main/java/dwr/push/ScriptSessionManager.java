package dwr.push;

import dwr.DwrScriptSessionManagerUtil;

public class ScriptSessionManager {
	public void init(String group) {
		DwrScriptSessionManagerUtil util = new DwrScriptSessionManagerUtil();
		util.initScriptSession(group);
	}
}
