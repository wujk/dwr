<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="dwr_web/engine.js"></script>
<script type="text/javascript" src="dwr_web/util.js"></script>
<script type="text/javascript" src="dwr_web/interface/PushAllClient.js"></script>
<script type="text/javascript" src="dwr_web/interface/ScriptSessionManager.js"></script>
<script type="text/javascript">  
    function testPush() {  
    	var value = document.getElementById("text").value;
    	PushAllClient.pushAll("showMessage",value);  
    } 
    function showMessage(msg) {  
    	alert(msg);
    }
    function onPageLoad() {  
    	dwr.engine.setActiveReverseAjax(true);
    	dwr.engine.setNotifyServerOnPageUnload(true);
    	ScriptSessionManager.init('0');
    }
    function connectSuccess(ip) {  
    	var div = document.getElementById("ip");
    	var p = document.createElement("p");
    	p.setAttribute("name", ip);
    	p.innerHTML = ip;
    	div.appendChild(p);
    }
    </script>  
</head>
<body onload="onPageLoad()">
<input type="button" value="Send" onclick="testPush()"  /> 
<input type="text" value="" id="text"/>
ip地址：<div id="ip"></div> 
</body>
</html>