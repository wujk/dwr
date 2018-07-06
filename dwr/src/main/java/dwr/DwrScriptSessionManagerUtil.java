package dwr;

import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.Container;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.event.ScriptSessionEvent;
import org.directwebremoting.event.ScriptSessionListener;
import org.directwebremoting.extend.ScriptSessionManager;

public class DwrScriptSessionManagerUtil {
		
	public void initScriptSession() {
		Container cantainer = ServerContextFactory.get().getContainer();
		ScriptSessionManager manager = cantainer.getBean(ScriptSessionManager.class);
		manager.addScriptSessionListener(new ScriptSessionListener() {
			
			@Override
			public void sessionDestroyed(ScriptSessionEvent ev) {
				System.out.println("ScriptSession销毁");
				ScriptSession scriptSession = ev.getSession();
				String ip = (String) scriptSession.getAttribute("VISIT_IP");
				ScriptBuffer script = new ScriptBuffer("connectDestory('"+ ip +"')");
				scriptSession.addScript(script );
			}
			
			@Override
			public void sessionCreated(ScriptSessionEvent ev) {
				System.out.println("ScriptSession创建");
				HttpServletRequest request = WebContextFactory.get().getHttpServletRequest();
				String ip = request.getHeader("X-Real-IP");
				if (ip == null) {
					ip = request.getRemoteAddr();
				}
				System.out.println("ip地址：" + ip);
				ScriptSession scriptSession = ev.getSession();
				String oldIp = (String) scriptSession.getAttribute("VISIT_IP");
				if (oldIp != null &&  oldIp.equals(ip)) {
					return;
				}
				scriptSession.setAttribute("VISIT_IP", ip);
				ScriptBuffer script = new ScriptBuffer("connectSuccess('"+ ip +"')");
				scriptSession.addScript(script );
			}
		});
	}
}
