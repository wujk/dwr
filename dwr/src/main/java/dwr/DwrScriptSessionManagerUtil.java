package dwr;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.directwebremoting.Container;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.event.ScriptSessionEvent;
import org.directwebremoting.event.ScriptSessionListener;
import org.directwebremoting.extend.ScriptSessionManager;

public class DwrScriptSessionManagerUtil {

	public void initScriptSession(String group) {
		ScriptSessionPool pool = ScriptSessionPool.getInstance();
		Container cantainer = ServerContextFactory.get().getContainer();
		ScriptSessionManager manager = cantainer.getBean(ScriptSessionManager.class);
		manager.addScriptSessionListener(new ScriptSessionListener() {

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
				System.out.println(group);
				if(pool.getSessionScriptSession(httpSession.getId()) == null) {
					scriptSession.setAttribute(Constants.GROUP, group);
					scriptSession.setAttribute(Constants.VISITIP, ip);
					scriptSession.setAttribute(Constants.HTTPSESSIONID, httpSession != null ? httpSession.getId() : null);
					System.out.println("id:" + scriptSession.getId() + "组：" + scriptSession.getAttribute(Constants.GROUP) + " 访问ip：" + scriptSession.getAttribute(Constants.VISITIP) + " httpSessionID：" + scriptSession.getAttribute(Constants.HTTPSESSIONID));
					pool.addScriptSession(scriptSession);
					ScriptBuffer script = DwrScriptbufferUtil.genScriptBuffer("connectSuccess", ip);
					scriptSession.addScript(script);
				} else {
					if (group != null) {
						ScriptBuffer script = DwrScriptbufferUtil.genScriptBuffer("hasConnect", pool.getSessionScriptSession(httpSession.getId()).getAttribute(Constants.GROUP));
						scriptSession.addScript(script);
					}
				}
				
			}
		});
	}

}
