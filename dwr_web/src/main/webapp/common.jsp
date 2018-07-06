<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="dwr_web/engine.js"></script>
<script type="text/javascript" src="dwr_web/util.js"></script>
<script type="text/javascript" src="dwr_web/interface/PushGroupClient.js"></script>
<script type="text/javascript" src="dwr_web/interface/ScriptSessionManager.js"></script>
<script type="text/javascript">  
    function testPush() {  
    	var value = document.getElementById("text").value;
    	PushGroupClient.sendGroup('0', "showMessage",value);  
    } 
    function showMessage(msg) {  
    	alert(msg);
    }
    function onPageLoad() {  
    	dwr.engine.setActiveReverseAjax(true);
    	dwr.engine.setNotifyServerOnPageUnload(true);
    	ScriptSessionManager.initCommon();
    }
    function connectSuccess(ip) {  
    	var div = document.getElementById("ip");
    	while (div.hasChildNodes()) //当div下还存在子节点时 循环继续
		{
			div.removeChild(div.firstChild);
		}
    	var p = document.createElement("p");
    	p.setAttribute("name", ip);
    	p.innerHTML = ip;
    	div.appendChild(p);
    }
    function hasConnect(msg) {
    	alert("已经在组内"+ msg);
    }
    </script>  
</head>
<body onload="onPageLoad()">
<input type="button" value="Send" onclick="testPush()"  /> 
<input type="text" value="" id="text"/>
ip地址：<div id="ip"></div> 
</body>
</html>