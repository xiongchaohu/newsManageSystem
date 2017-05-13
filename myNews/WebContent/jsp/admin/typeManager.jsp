<!DOCTYPE HTML>
<%@page import="java.util.ArrayList"%>
<%@page import="entity.NewsType"%>
<html>
<head>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%-- <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> --%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%-- <base href="<%=basePath%>"> --%>
<title>新闻类别管理</title>
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

.addTypeBtn {
	width: 150px;
	height: 40px;
	position: relative;
	left: 15px;
	font-size: 16px;
	margin-bottom: 20px;
	background: #eaeaea;
}
</style>
</head>
<body>

	<%@include file="./index.jsp"%>

	<div class="table clear">

		<%-- <form method="post" action="./NewsManagerServlet?method=search" name="searchForm">
        		<input type="text" id="searchInput" placeholder="请输入新闻标题" name="title" value="${searchTitle }"/>
        		<button type="submit" id="search">搜索</button>
        	</form> --%>

		<form method="post" action="./jsp/admin/addNewsType.jsp" name="add">
			<button type="submit" class="addTypeBtn">添加新闻类别</button>

		</form>

		<table>
			<thead>

				<th width="30%">新闻类别ID</th>
				<th width="30%">类别名称</th>
				<th colspan="2" width="40%">操作</th>
			</thead>

			<tbody>
				<%
					ArrayList<NewsType> list = (ArrayList<NewsType>) request.getAttribute("allNewsType");
					if (list != null && list.size() > 0) {
						for (int i = 0; i < list.size(); i++) {
							NewsType newsType = list.get(i);
							if (i % 2 == 0) {
								out.println("<tr class='gray'>");
							} else {
								out.println("<tr>");
							}
				%>

				<td><%=newsType.getNews_type_id()%></td>
				<td><%=newsType.getNews_type_name()%></td>
				<td><a
					href="./TypeManagerServlet?method=edit&news_type_id=<%=newsType.getNews_type_id()%>">修改</a></td>
				<td><a class="delete"
					href="./TypeManagerServlet?method=delete&news_type_id=<%=newsType.getNews_type_id()%>">删除</a></td>
				</tr>

				<%
					}
					}
				%>
			</tbody>
		</table>

	</div>

	<script type="text/javascript" src="././js/jquery-3.2.0.min.js"></script>
	<script type="text/javascript" src="././js/deleteConfirm.js"></script>
</body>
</html>
