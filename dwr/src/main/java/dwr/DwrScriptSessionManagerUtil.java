package dwr;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.event.ScriptSessionEvent;
import org.directwebremoting.event.ScriptSessionListener;
import org.directwebremoting.impl.DefaultScriptSessionManager;

public class DwrScriptSessionManagerUtil extends DefaultScriptSessionManager {
	
	public DwrScriptSessionManagerUtil() {
		ScriptSessionPool pool = ScriptSessionPool.getInstance();
		this.addScriptSessionListener(new ScriptSessionListener() {

			@Override
			public void sessionDestroyed(ScriptSessionEvent ev) {
				System.out.println("ScriptSession销毁");
				ScriptSession scriptSession = ev.getSession();
				if (scriptSession == null)
					return;
				System.out.println("id:" + scriptSession.getId() + "组：" + scriptSession.getAttribute(Constants.GROUP) + " 访问ip：" + scriptSession.getAttribute(Constants.VISITIP) + " httpSessionID：" + scriptSession.getAttribute(Constants.HTTPSESSIONID));
				pool.removeScriptSession(scriptSession);
			}

			@Override
			public void sessionCreated(ScriptSessionEvent ev) {
				System.out.println("ScriptSession创建");
				HttpServletRequest request = WebContextFactory.get().getHttpServletRequest();
				HttpSession httpSession = WebContextFactory.get().getSession();
				String ip = request.getHeader("X-Real-IP");
				if (ip == null) {
					ip = request.getRemoteAddr();
				}
				ScriptSession scriptSession = ev.getSession();
				if (scriptSession == null)
					return;
				if (httpSession == null) {
					return;
				}
				
				scriptSession.setAttribute(Constants.HTTPSESSIONID, httpSession != null ? httpSession.getId() : null);
				String group = (String) request.getAttribute(Constants.GROUP);
				if (group == null) {
					group = (String) httpSession.getAttribute(Constants.GROUP);
				}
				if (group != null) {
					scriptSession.setAttribute(Constants.GROUP, group);
					ScriptSession _scriptSession = pool.getSessionScriptSession(httpSession.getId());
					if ( _scriptSession != null) {
						String oldGroup = (String) _scriptSession.getAttribute(Constants.GROUP);
						if (oldGroup != null && !group.equals(oldGroup)) {
							ScriptBuffer script = DwrScriptbufferUtil.genScriptBuffer("changeGroup", oldGroup, group);
							_scriptSession.addScript(script);
						}
					}
				}
				if (ip != null) {
					scriptSession.setAttribute(Constants.VISITIP, ip);
				}
				
				System.out.println("id:" + scriptSession.getId() + "组：" + scriptSession.getAttribute(Constants.GROUP) + " 访问ip：" + scriptSession.getAttribute(Constants.VISITIP) + " httpSessionID：" + scriptSession.getAttribute(Constants.HTTPSESSIONID));
				pool.addScriptSession(scriptSession);
				ScriptBuffer script = DwrScriptbufferUtil.genScriptBuffer("connectSuccess", ip);
				scriptSession.addScript(script);
				if (group != null) {
					script = DwrScriptbufferUtil.genScriptBuffer("hasConnect",
							pool.getSessionScriptSession(httpSession.getId()).getAttribute(Constants.GROUP));
					scriptSession.addScript(script);
				}
				
				
			}
		});
	}

}
