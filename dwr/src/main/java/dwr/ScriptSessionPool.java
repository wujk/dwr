package dwr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView;

import org.directwebremoting.ScriptSession;

public class ScriptSessionPool {
	
	private static class InitPool {
		private static ScriptSessionPool INSTANCE = new ScriptSessionPool();
	}
	
	private ConcurrentHashMap<String, ScriptSession> scriptSessions;
	
	private ScriptSessionPool() {
		scriptSessions = new ConcurrentHashMap<String, ScriptSession>();
	}
	
	public static ScriptSessionPool getInstance() {
		return InitPool.INSTANCE;
	}

	public ConcurrentHashMap<String, ScriptSession> getScriptSessions() {
		return scriptSessions;
	}

	public void setScriptSessions(ConcurrentHashMap<String, ScriptSession> scriptSessions) {
		this.scriptSessions = scriptSessions;
	}
	
	public ScriptSession addScriptSession(ScriptSession scriptSession) {
		if (scriptSession != null) {
			String id = scriptSession.getId();
			if (id != null) {
				System.out.println("添加scriptSession：" + id);
				return scriptSessions.putIfAbsent(id, scriptSession);
			}
		} 
		return scriptSession;
	}
	
	public ScriptSession removeScriptSession(ScriptSession scriptSession) {
		if (scriptSession != null) {
			String id = scriptSession.getId();
			if (id != null) {
				System.out.println("删除scriptSession：" + id);
				return scriptSessions.remove(id);
			}
		}
		return scriptSession;
	}
	
	public List<ConnectBean> managerScriptSession() {
		List<ConnectBean> list = new ArrayList<ConnectBean>();
		Set<Entry<String, ScriptSession>> entries = scriptSessions.entrySet();
		for (Entry<String, ScriptSession> entry : entries) {
			ScriptSession scriptSession = entry.getValue();
			ConnectBean connectBean = new ConnectBean();
			connectBean.setId(scriptSession.getId());
			connectBean.setIp((String)scriptSession.getAttribute("VISIT_IP"));
			connectBean.setType((String)scriptSession.getAttribute("TYPE"));
			list.add(connectBean);
		}
		return list;
	}
	
	public List<ScriptSession> getTypeScriptSession(String type) {
		List<ScriptSession> list = new ArrayList<ScriptSession>();
		Set<Entry<String, ScriptSession>> entries = scriptSessions.entrySet();
		for (Entry<String, ScriptSession> entry : entries) {
			ScriptSession scriptSession = entry.getValue();
			String _type = (String)scriptSession.getAttribute("TYPE");
			if (type.equals(_type)) {
				list.add(scriptSession);
			}
		}
		return list;
	}
	

}
