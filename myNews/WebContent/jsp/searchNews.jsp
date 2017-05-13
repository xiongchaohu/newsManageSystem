<!DOCTYPE HTML>
<html>
<head>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="entity.News"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="./css/common.css" />
<link rel="stylesheet" href="./css/searchNews.css" />
<base href="<%=basePath%>">
<title>搜索结果页</title>
<style type="text/css">
	#footer{
		position:relative;
		top:200px;
	}
	
	#footer p{
		font-size:12px;
	}
</style>
</head>
<body>
	<div id="wrap">
		<div id="header" class="clear">
			<div class="ns_area">
				<h1 class="logo">
					<a href="index1.jsp">新闻连连看</a>
				</h1>

				<!-- 搜索导航 -->
				<form action="./SearchServlet">

					<div class="top_search">
						<input type="text" class="search_input" name="title"
							placeholder="请输入关键字" value="${title }" />
						<button id="search" type="submit"></button>
					</div>
				</form>

			</div>

		</div>
		<!-- 显示搜索结果 -->
		<div class="searchResult">
			<h1>搜索结果页</h1>
			<%
				int currentPage = 1; //当前页
				int allCount = 0; //总记录数
				int allPageCount = 0; //总页数
				News news = null;
				//查看request中是否有currentPage信息，如没有，则说明首次访问该页
				if (request.getAttribute("allNewsByTitle") != null) {
					//获取Action返回的信息
					currentPage = ((Integer) request.getAttribute("currentPage")).intValue();
					ArrayList<News> list = (ArrayList<News>) request.getAttribute("allNewsByTitle");
					allCount = ((Integer) request.getAttribute("allCount")).intValue();
					allPageCount = ((Integer) request.getAttribute("allPageCount")).intValue();
					if (list.size() == 0) {
						out.print("暂无搜索结果，请重新输入");
					} else if (list != null && list.size() > 0) {
						for (int i = 0; i < list.size(); i++) {
			%>
			<div class="article">
				<div class="news_title clear">
					<h3>
						<a href="./NewsDetailServlet?id=<%=list.get(i).getId()%>"><%=list.get(i).getTitle()%></a>
					</h3>
					<span class="read_count">点击量：<%=list.get(i).getNews_count()%></span>
				</div>


				<div class="clear">
					<p class="time">
						发表于：<%=list.get(i).getCreat_time()%></p>
					<!-- <span class="com-num"> <a href="#">6</a> -->
					</span>
				</div>
			</div>

			<%
				}
					}
				}
			%>

			<%
				if (allPageCount > 1) {
			%>
			<div class="page">
				<nav>
					<ul class="pagination clear">
						<%-- <li>共（${allCount }）条纪录</li>
						<li>共（${allPageCount }）页</li>
						<li>当前页为第${currentPage }页</li> --%>
						<li><a href="SearchServlet?currentPage=1&title=${title}">首页</a></li>
						<li><a
							href="SearchServlet?currentPage=<%=(currentPage-1)<1?1:(currentPage-1)%>&title=${title}">上一页</a></li>
						<li><a
							href="SearchServlet?currentPage=<%=(currentPage+1)>allPageCount?allPageCount:(currentPage+1)%>&title=${title}">下一页</a></li>
						<li><a
							href="SearchServlet?currentPage=<%=allPageCount%>&title=${title}">末页</a></li>
					</ul>
				</nav>
			</div>

			<%
				}
			%>
		</div>
		
		<div id="footer">
			<h4>
				<a href="jsp/admin/login.jsp">进入后台</a>
			</h4>
			<p>Copyright © 1998 - 2017 hxc. All Rights Reserved</p>
			<p>胡雄超 版权所有</p>
			<p>联系电话：18829292337</p>
			<p>联系邮箱：1732139715@qq.com</p>
		</div>
	</div>
</body>
</html>
