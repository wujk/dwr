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
				HttpSession httpSession = WebContextFactory.get().getSession();
				ScriptSession scriptSession = ev.getSession();
				if (scriptSession == null)
					return;
				String httpSessionId = httpSession.getId();
				String _httpSessionId = (String) scriptSession.getAttribute(Constants.HTTPSESSIONID);
				if (httpSessionId != null && httpSessionId.equals(_httpSessionId)) {
					String ip = (String) scriptSession.getAttribute(Constants.VISITIP);
					if (ip != null) {
						System.out.println("销毁ip地址：" + ip);
					}
					System.out.println("httpSessionId地址：" + httpSessionId);
					pool.removeScriptSession(scriptSession);
				}
				System.out.println("ScriptSession销毁");
			}

			@Override
			public void sessionCreated(ScriptSessionEvent ev) {
				HttpServletRequest request = WebContextFactory.get().getHttpServletRequest();
				HttpSession httpSession = WebContextFactory.get().getSession();
				String ip = request.getHeader("X-Real-IP");
				if (ip == null) {
					ip = request.getRemoteAddr();
				}
				System.out.println("ip地址：" + ip);
				ScriptSession scriptSession = ev.getSession();
				if (scriptSession == null)
					return;
				String oldIp = (String) scriptSession.getAttribute(Constants.VISITIP);
				if (oldIp != null && oldIp.equals(ip)) {
					return;
				}
				scriptSession.setAttribute(Constants.GROUP, group);
				scriptSession.setAttribute(Constants.VISITIP, ip);
				scriptSession.setAttribute(Constants.HTTPSESSIONID, httpSession != null ? httpSession.getId() : null);
				ScriptBuffer script = DwrScriptbufferUtil.genScriptBuffer("connectSuccess", ip);
				scriptSession.addScript(script);
				pool.addScriptSession(scriptSession);
				System.out.println("ScriptSession创建");
			}
		});
	}

}
