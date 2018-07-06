package dwr;

import java.util.concurrent.ConcurrentHashMap;

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

}
