package dwr;

import java.util.Collection;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;

public class PushGroup {
	
	public void sendGroup(String group, String funcName, String msg) {

		Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
			
			@Override
			public boolean match(ScriptSession session) {
					String type = (String) session.getAttribute(Constants.GROUP);
					return group.equals(type);
			}
		}, new Runnable() {
			
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
