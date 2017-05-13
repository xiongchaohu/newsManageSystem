<!DOCTYPE HTML>
<html>
<head>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/common.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/admin/admin_index.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/admin/common.css" />
<%-- <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> --%>
    
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- <base href="<%=basePath%>"> --%>
<title>修改密码页</title>
</head>
<body>

<%@include file="./index.jsp"%>

<form action="./ChangePwdServlet?name=<%=session.getAttribute("adminname") %>" method="post">
	<table>
		<tbody>
			<tr>
				<td>旧密码：</td>
				<td><input type="password" name="oldPwd" placeholder="请输入旧密码"/></td>
			</tr>
			
			<tr>
				<td>新密码：</td>
				<td><input type="password" name="newPwd" placeholder="请输入新密码"/></td>
			</tr>
			
			<tr>
				<td>确认新密码：</td>
				<td><input type="password" name="confirmPwd" placeholder="请再次输入新密码"/></td>
			</tr>
			
			<tr style="text-align:center">
					<td style="margin:0 auto" colspan="2">
						<button type="submit" style="margin-right:20px;">提交</button>
						<button id="cancle">取消</button>
					</td>
				
			</tr>
		</tbody>
	</table>
</form>

</body>
</html>
