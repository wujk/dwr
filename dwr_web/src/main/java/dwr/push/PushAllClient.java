package dwr.push;

import dwr.PushAll;

public class PushAllClient { 
	public void pushAll(String funcName, String msg) {
		new PushAll().sendAll(funcName, msg);
	}
}
