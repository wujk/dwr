package dwr.push;

import dwr.DwrScriptSessionManagerUtil;

public class ScriptSessionManager {

	public void initManager() {
		init("0");
	}

	public void initCommon() {
		init("1");
	}

	public synchronized void init(String group) {
		DwrScriptSessionManagerUtil util = new DwrScriptSessionManagerUtil();
		util.initScriptSession(group);
	}
}
