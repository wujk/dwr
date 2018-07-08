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
<script type="text/javascript" src="dwr_web/interface/ScriptSessionGroup.js"></script>
<script type="text/javascript">  
    function testPush() {  
    	var value = document.getElementById("text").value;
    	PushGroupClient.sendGroup('common', "showMessage",value);    
    } 
    function showMessage(msg) {  
    	alert(msg);
    }
    function onPageLoad() {  
    	dwr.engine.setActiveReverseAjax(true);
    	dwr.engine.setNotifyServerOnPageUnload(true);
    	ScriptSessionGroup.initData("super");
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
    function showGroup(msg) {
    	alert("已经在组内"+ msg);
    }
    function changeGroup(oldGroup, newGroup) {
    	alert("组变更"+ oldGroup + "到" + newGroup);
    }
    </script>  
</head>
<body onload="onPageLoad()">
<input type="button" value="Send" onclick="testPush()"  /> 
<input type="text" value="" id="text"/>
ip地址：<div id="ip"></div> 
</body>
</html>