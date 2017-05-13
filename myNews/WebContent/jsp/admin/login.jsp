<!DOCTYPE HTML>
<html>
<head>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
    
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<base href="<%=basePath%>">
<title>后台登录界面</title>
<link href="./css/admin/style.css" rel="stylesheet" type="text/css">
</head>
<body>

	<div class="main">
		<div class="login-form">
			<h1>后台登录</h1>
			<div class="head"><img src="<%=request.getContextPath() %>/images/user1.png" alt=""></div>
			<form action="./LoginServlet?type=admin" method="post">
				<input type="text" class="text" name='userName' value="用户名" onfocus="this.value = &#39;&#39;;" onblur="if (this.value == &#39;&#39;) {this.value = &#39;用户名&#39;;}">
				<input type="password" value="" name="password">
				<div class="submit"><input type="submit" onclick="myFunction()" value="登录"></div>	
				<p><a href="http://www.17sucai.com/preview/2/2015-01-23/MemberLogin/index.html#">忘记密码?</a></p>
			</form>
		</div>
		<div class="copy-right"><p>版权@hxc保留所有权利。</p> </div>
	</div>


</body></html>
