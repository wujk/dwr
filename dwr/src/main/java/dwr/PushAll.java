package dwr;

import java.util.Collection;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;

public class PushAll {
	public void sendAll(String funcName, String msg) {

		Browser.withAllSessions(new Runnable() {

			@Override
			public void run() {
				ScriptBuffer script = DwrScriptbufferUtil.genScriptBuffer(funcName, msg);
				Collection<ScriptSession> sessions = Browser.getTargetSessions();
				for (ScriptSession scriptSession : sessions) {
					scriptSession.addScript(script);
				}
			}
		});
	}
}
