package dwr;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;
import org.directwebremoting.WebContextFactory;

public class PushAll {
	public void sendAll(String funcName, String msg) {

		Browser.withAllSessionsFiltered(new ScriptSessionFilter() {

			@Override
			public boolean match(ScriptSession session) {
				// 过滤除自己以外的所有页面
				HttpSession httpSession = WebContextFactory.get().getSession();
				return !httpSession.getId().equals(session.getAttribute(Constants.HTTPSESSIONID)); // 不发给自己
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
