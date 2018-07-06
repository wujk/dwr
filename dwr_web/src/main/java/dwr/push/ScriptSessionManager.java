package dwr.push;

import dwr.DwrScriptSessionManagerUtil;

public class ScriptSessionManager {
	public void init() {
		DwrScriptSessionManagerUtil util = new DwrScriptSessionManagerUtil();
		util.initScriptSession();
	}
}
