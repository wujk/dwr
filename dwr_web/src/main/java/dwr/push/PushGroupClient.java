package dwr.push;

import dwr.PushGroup;

public class PushGroupClient {
	public void sendGroup(String group, String funcName, String msg) {
		new PushGroup().sendGroup(group, funcName, msg);
	}
}
