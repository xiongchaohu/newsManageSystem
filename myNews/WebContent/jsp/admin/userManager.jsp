<!DOCTYPE HTML>
<%@page import="entity.User"%>
<html>
<head>
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%-- <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> --%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- <base href="<%=basePath%>"> --%>
<title>用户管理</title>

<style>
.table {
	margin-top: -25px;
}

table {
	background: #fff;
	border: 4px solid #eaeaea;
	width: 79%;
	table-layout: fixed;
	float: right;
	margin-bottom: 50px;
}

tr {
	border-bottom: 3px solid #aaaaaa;
	line-height: 40px;
	height: 40px;
}

td, th {
	white-space: nowrap;
	overflow: hidden;
	text-align: center;
	text-overflow: ellipsis;
	vertical-align: middle;
	height: 40px;
	padding-left: 5px;
}

.gray {
	background: #eaeaea;
}

#searchInput {
	line-height: 30px;
	padding-left: 10px;
	margin: 10px 15px;
	width: 200px;
}

#search {
	width: 100px;
	height: 30px;
	font-size: 16px;
}

.page {
	margin: 50px auto;
	padding-left: 50px;
	text-align: center;
	width: 500px;
	height: 50px;
	position:relative;
	top:-20px;
	left:150px;
}

.pagination{
	height:50px;
}

.page nav {
	margin: 0 auto;
}

.page li {
	float: left;
	margin: 0 30px;
}
</style>
</head>
<body>

	<%@include file="./index.jsp"%>

	<div class="table clear">

		<form method="post" action="./UserManagerServlet?method=search"
			name="searchForm">
			<input type="text" id="searchInput" placeholder="请输入用户名进行搜索"
				name="username" value="${searchUsername }" />
			<button type="submit" id="search">搜索</button>
		</form>

		<table>
			<thead>

				<th width="30%">用户名</th>
				<th width="30%">邮箱地址</th>
				<th colspan="2" width="40%">操作</th>
			</thead>

			<tbody>
				<%
					if (request.getAttribute("user") != null) {
						User user = (User) request.getAttribute("user");
						out.println("<tr class='gray'>");
				%>

				<td><%=user.getUsername()%></td>
				<td><%=user.getEmail()%></td>
				<td><a
					href="./UserManagerServlet?method=update&id=<%=user.getId()%>">修改</a></td>
				<td><a class="delete"
					href="./UserManagerServlet?method=delete&id=<%=user.getId()%>">删除</a></td>
				<%
					} else {
				%>

				<%
					int currentPage = 1; //当前页
						int allCount = 0; //总记录数
						int allPageCount = 0; //总页数
						User user = null;
						//查看request中是否有currentPage信息，如没有，则说明首次访问该页
						if (request.getAttribute("allUsers") != null) {
							//获取Action返回的信息
							currentPage = ((Integer) request.getAttribute("currentPage")).intValue();
							ArrayList<User> list = (ArrayList<User>) request.getAttribute("allUsers");

							allCount = ((Integer) request.getAttribute("allCount")).intValue();
							allPageCount = ((Integer) request.getAttribute("allPageCount")).intValue();
							if (list != null && list.size() > 0) {
								for (int i = 0; i < list.size(); i++) {
									user = list.get(i);

									if (i % 2 == 0) {
										out.println("<tr class='gray'>");
									} else {
										out.println("<tr>");
									}
				%>

				<td><%=user.getUsername()%></td>
				<td><%=user.getEmail()%></td>
				<td><a
					href="./UserManagerServlet?method=edit&id=<%=user.getId()%>">修改</a></td>
				<td><a class="delete"
					href="./UserManagerServlet?method=delete&id=<%=user.getId()%>">删除</a></td>
				</tr>
				<%
					}
							}
						}
				%>
			</tbody>
		</table>

		<%
			if (allPageCount > 1) {
		%>
		<div class="page">
			<nav>
				<ul class="pagination clear">
					<%-- <li>共（${allCount }）条纪录</li>
						<li>共（${allPageCount }）页</li>
						<li>当前页为第${currentPage }页</li> --%>
					<li><a href="./UserManagerServlet?method=view&currentPage=1">首页</a></li>
					<li><a
						href="./UserManagerServlet?method=view&currentPage=<%=(currentPage - 1) < 1 ? 1 : (currentPage - 1)%>">上一页</a></li>
					<li><a
						href="./UserManagerServlet?method=view&currentPage=<%=(currentPage + 1) > allPageCount ? allPageCount : (currentPage + 1)%>">下一页</a></li>
					<li><a
						href="./UserManagerServlet?method=view&currentPage=<%=allPageCount%>">末页</a></li>
				</ul>
			</nav>
		</div>

		<%
			}
			}
		%>
	</div>
	
	<script type="text/javascript" src="././js/jquery-3.2.0.min.js"></script>
	<script type="text/javascript" src="././js/deleteConfirm.js"></script>
</body>
</html>
