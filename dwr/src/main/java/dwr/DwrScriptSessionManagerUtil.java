package dwr;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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
		
	public void initScriptSession(String type) {
		ScriptSessionPool pool = ScriptSessionPool.getInstance();
		Container cantainer = ServerContextFactory.get().getContainer();
		ScriptSessionManager manager = cantainer.getBean(ScriptSessionManager.class);
		manager.addScriptSessionListener(new ScriptSessionListener() {
			
			@Override
			public void sessionDestroyed(ScriptSessionEvent ev) {
				ScriptSession scriptSession = pool.removeScriptSession(ev.getSession());
				if (scriptSession == null) return;
				String ip = (String) scriptSession.getAttribute("VISIT_IP");
				if (ip != null) {
					System.out.println("ip地址：" + ip);
					ScriptBuffer script = DwrScriptbufferUtil.genScriptBuffer("connectDestory", ip);
					scriptSession.addScript(script);
					scriptSession.removeAttribute("VISIT_IP");
				}
				System.out.println("ScriptSession销毁");
			}
			
			@Override
			public void sessionCreated(ScriptSessionEvent ev) {
				HttpServletRequest request = WebContextFactory.get().getHttpServletRequest();
				String ip = request.getHeader("X-Real-IP");
				if (ip == null) {
					ip = request.getRemoteAddr();
				}
				System.out.println("ip地址：" + ip);
				ScriptSession scriptSession = pool.addScriptSession(ev.getSession());
				if (scriptSession == null) return;
				
				ScriptBuffer script = DwrScriptbufferUtil.genScriptBuffer("managerConnect", pool.managerScriptSession());
				List<ScriptSession> list = pool.getTypeScriptSession("0");
				for (ScriptSession _scriptSession : list) {
					_scriptSession.addScript(script);
				}
				String oldIp = (String) scriptSession.getAttribute("VISIT_IP");
				if (oldIp != null &&  oldIp.equals(ip)) {
					return;
				}
				scriptSession.setAttribute("TYPE", type);
				scriptSession.setAttribute("VISIT_IP", ip);
				script = DwrScriptbufferUtil.genScriptBuffer("connectSuccess", ip);
				scriptSession.addScript(script );
				System.out.println("ScriptSession创建");
			}
		});
	}
	
}
