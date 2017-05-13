<!DOCTYPE HTML>
<html>
<head>
<%@page import="entity.User"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/common.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/admin/admin_index.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/admin/common.css" />
<title>修改用户信息</title>

</head>
<body>

	<%@include file="./index.jsp"%>

	<%
		if (request.getAttribute("user") != null) {
			User user = new User();
			user = (User) request.getAttribute("user");
	%>
	<div>
		<form id="editNews" name="editNews"
			action="./UserManagerServlet?method=update&id=<%=user.getId()%>"
			method="post">

			<table>
				<tr>
					<td><label for="title">用户名：</label></td>
					<td><input type="text" id="username" name="username"
						value="<%=user.getUsername()%>" /></td>
				</tr>

				<tr>
					<td><label for="type">邮箱地址：</label></td>
					<td><input type="text" name="email"
						value="<%=user.getEmail()%>" /></td>
				</tr>

				<tr style="text-align:center">
					<td style="margin:0 auto" colspan="2">
						<button type="submit" style="margin-right:20px;">提交</button>
						<button id="cancle">取消</button>
					</td>
				</tr>
			</table>

		</form>
	</div>
	<%
		} else {
	%>

	<div>
		<form id="editNews" name="editNews"
			action="./UserManagerServlet?method=add" method="post">

			<table>
				<tr>
					<td><label for="title">用户名：</label></td>
					<td><input type="text" id="username" name="username"
						placeholder="请输入用户名" /></td>
				</tr>

				<tr>
					<td><label for="type">邮箱地址：</label></td>
					<td><input type="text" name="email" placeholder="请输入邮箱地址" /></td>
				</tr>

				<tr>
					<td><label for="type">密码：</label></td>
					<td><input type="text" name="password" placeholder="请输入密码" /></td>
				</tr>

				<tr style="text-align:center">
					<td style="margin:0 auto" colspan="2">
						<button type="submit" style="margin-right:20px;">提交</button>
						<button id="cancle">取消</button>
					</td>
				
					<!-- <td><button type="submit" style="margin-right:20px;">提交</button></td>
					<td><button id="cancle">取消</button></td> -->
				</tr>
			</table>

		</form>
	</div>
	<%
		}
	%>

	<script type="text/javascript">
		var cancleBtn = document.getElementById("cancle");
		cancleBtn.onclick = function() {
			history.back();
		}
	</script>
</body>
</html>
