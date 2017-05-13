<!DOCTYPE HTML>
<%@page import="dao.impl.CommentDAOImpl"%>
<html>
<head>
<%@page import="entity.*"%>
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%-- <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> --%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="./css/common.css" />
<link rel="stylesheet" href="./css/admin/admin_index.css">
<%-- <base href="<%=basePath%>"> --%>
<title>评论审核页</title>
<style>
p {
	word-break: break-all;
	text-overflow: ellipsis
}

table {
	background: #fff;
	border: 4px solid #eaeaea;
	width: 76.5%;
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
	position: relative;
	top: -20px;
	left: 150px;
}

.page nav {
	margin: 0 auto;
}

.page li {
	float: left;
	margin: 0 30px;
}

td div {
	height: 40px;
	width: 100%;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
}
</style>
</head>
<body>

	<%@include file="./index.jsp"%>

	<table>
		<thead>

			<th width="20%">评论的新闻标题</th>
			<th width="20%">评论内容</th>
			<th width="20%">评论用户</th>
			<th width="20%">发表时间</th>
			<th width="20%">审核操作</th>
		</thead>

		<tbody>
			<%
				int currentPage = 1; //当前页
				int allCount = 0; //总记录数
				int allPageCount = 0; //总页数
				Comment comment = null;
				//查看request中是否有currentPage信息，如没有，则说明首次访问该页
				if (request.getAttribute("allComments") != null) {
					//获取Action返回的信息
					currentPage = ((Integer) request.getAttribute("currentPage")).intValue();
					ArrayList<Comment> list = (ArrayList<Comment>) request.getAttribute("allComments");

					allCount = ((Integer) request.getAttribute("allCount")).intValue();
					allPageCount = ((Integer) request.getAttribute("allPageCount")).intValue();
					
					if (list != null && list.size() > 0) {
					
						for (int i = 0; i < list.size(); i++) {
							comment = list.get(i);

							
							/* CommentDAOImpl dao = new CommentDAOImpl(); */
							/* String name = dao.findTypeNameByType(news.getNews_type_id()); */
							if (i % 2 == 0) {
								out.println("<tr class='gray'>");
							} else {
								out.println("<tr>");
							}
			%>

			<td><%=comment.getTitle()%></td>
			<td title="追寻习近平总书记的初心·正定篇"><a href="#"><%=comment.getContent()%></a></td>
			<td width="20%"><div><%=comment.getUsername()%></div></td>
			<td><%=comment.getTime().toLocaleString()%></td>
			<%-- <td><a
				href="./CommentServlet?method=pass&id=<%=comment.getId()%>">通过</a></td> --%>
			<td><a class="delete"
				href="./CommentServlet?method=del&id=<%=comment.getId()%>">删除</a></td>
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
				<li><a href="./CommentServlet?method=reviewAll&currentPage=1">首页</a></li>
				<li><a
					href="./CommentServlet?method=reviewAll&currentPage=<%=(currentPage - 1) < 1 ? 1 : (currentPage - 1)%>">上一页</a></li>
				<li><a
					href="./CommentServlet?method=reviewAll&currentPage=<%=(currentPage + 1) > allPageCount ? allPageCount : (currentPage + 1)%>">下一页</a></li>
				<li><a
					href="./CommentServlet?method=reviewAll&currentPage=<%=allPageCount%>">末页</a></li>
			</ul>
		</nav>
	</div>

	<%
		}
	%>


	<script type="text/javascript" src="././js/jquery-3.2.0.min.js"></script>

	<script type="text/javascript" src="././js/deleteConfirm.js"></script>
</body>
</html>
