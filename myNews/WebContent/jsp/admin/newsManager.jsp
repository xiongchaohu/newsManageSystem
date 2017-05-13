<!DOCTYPE HTML>
<%@page import="dao.impl.NewsDAOImpl"%>
<%@page import="entity.NewsType"%>
<html>
<head>
<%@ page language="java" import="java.util.*,entity.News"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%-- <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%> --%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- <link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/common.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/admin/admin_index.css">
 --%>

<%-- <base href="<%=basePath%>"> --%>
<title>新闻管理页</title>
<style>
p {
	word-break: break-all;
	text-overflow: ellipsis
}

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
	margin: 30px auto;
	padding-left: 50px;
	text-align: center;
	width: 500px;
	height: 50px;
	position:relative;
	top:-20px;
	left:150px;
}

.page nav {
	margin: 0 auto;
}

.page li {
	float: left;
	margin: 0 30px;
}

.pagination{
	height:50px;
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

	<div class="table clear">

		<form method="post" action="./NewsManagerServlet?method=search"
			name="searchForm">
			<input type="text" id="searchInput" placeholder="请输入新闻标题"
				name="title" value="${searchTitle }" />
			<button type="submit" id="search">搜索</button>
		</form>

		<table>
			<thead>

				<th width="10%">新闻类别</th>
				<th width="20%">新闻标题</th>
				<th width="20%">新闻内容</th>
				<th width="20%">新闻创建事件</th>
				<th width="10%">新闻点击率</th>
				<th colspan="2" width="20%">操作</th>
			</thead>

			<tbody>
				<%
					int currentPage = 1; //当前页
					int allCount = 0; //总记录数
					int allPageCount = 0; //总页数
					News news = null;
					//查看request中是否有currentPage信息，如没有，则说明首次访问该页
					if (request.getAttribute("allNews") != null) {
						//获取Action返回的信息
						currentPage = ((Integer) request.getAttribute("currentPage")).intValue();
						ArrayList<News> list = (ArrayList<News>) request.getAttribute("allNews");

						allCount = ((Integer) request.getAttribute("allCount")).intValue();
						if(allCount==0){
							out.println("<p style='text-align:center;position:relative;top:80px;'>暂时没有该条新闻的纪录</p>");
						}
						allPageCount = ((Integer) request.getAttribute("allPageCount")).intValue();
						if (list != null && list.size() > 0) {
							for (int i = 0; i < list.size(); i++) {
								news = list.get(i);

								NewsDAOImpl dao = new NewsDAOImpl();
								String name = dao.findTypeNameByType(news.getNews_type_id());
								if (i % 2 == 0) {
									out.println("<tr class='gray'>");
								} else {
									out.println("<tr>");
								}
				%>

				<td><%=name%></td>
				<td title=""><a href="#"><%=news.getTitle()%></a></td>
				<td width="20%"><div><%=news.getContent()%></div></td>
				<td><%=news.getCreat_time()%></td>
				<td><%=news.getNews_count()%></td>
				<td><a
					href="./NewsManagerServlet?method=edit&id=<%=news.getId()%>">编辑</a></td>
				<td><a class="delete"
					href="./NewsManagerServlet?method=delete&id=<%=news.getId()%>">删除</a></td>
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
					<li><a
						href="./NewsManagerServlet?method=view&currentPage=1&title=${searchTitle }">首页</a></li>
					<li><a
						href="./NewsManagerServlet?method=view&currentPage=<%=(currentPage-1)<1?1:(currentPage-1)%>&title=${searchTitle}">上一页</a></li>
					<li><a
						href="./NewsManagerServlet?method=view&currentPage=<%=(currentPage+1)>allPageCount?allPageCount:(currentPage+1)%>&title=${searchTitle}">下一页</a></li>
					<li><a
						href="./NewsManagerServlet?method=view&currentPage=<%=allPageCount%>">末页</a></li>
				</ul>
			</nav>
		</div>

		<%
			}
		%>
	</div>

	<script type="text/javascript" src="././js/jquery-3.2.0.min.js"></script>
	
	<script type="text/javascript" src="././js/deleteConfirm.js"></script>
</body>
</html>
