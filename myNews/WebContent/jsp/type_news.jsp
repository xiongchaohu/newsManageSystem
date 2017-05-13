<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="dao.impl.NewsDAOImpl,entity.News,entity.NewsType"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<base href="<%=basePath%>">
<title>国内新闻</title>
<link rel="stylesheet" href="./css/common.css" />
<link rel="stylesheet" href="./css/type_news.css" />
</head>
<body>

	<div id="wrap">
		<div id="header" class="clear">
			<div class="ns_area">
				<h1 class="logo">
					<a href="./index1.jsp">新闻连连看</a>
				</h1>

				<form action="./SearchServlet">

					<div class="top_search">
						<input type="text" class="search_input" name="title"
							placeholder="请输入关键字" value="${title }" />
						<button id="search" type="submit"></button>
					</div>
				</form>

			</div>

			<%
				if (session.getAttribute("username") == null) {
			%>
			<div class="button_group">
				<a href="jsp/userLogin.jsp#toLogin" id="login_btn">登录</a>/ <a href="jsp/userLogin.jsp#toregister" id="register_btn">注册</a>
			</div>
			<%
				} else {
			%>
			<div class="button_group">
				<img src="./images/user.png" /> <span><%=session.getAttribute("username")%></span>
				<span><a href="./ExitServlet?type=user">注销</a></span>
			</div>
			<%
				}
			%>

			<div class="nav_menu clear">
				<ul>
					<li><a href="./index1.jsp">首页</a></li>
					<%
						ArrayList<NewsType> typeArr = (ArrayList<NewsType>) request.getAttribute("typeLists");
						int length=typeArr.size();
						for (int i = 0; i < typeArr.size(); i++) {
					%>
					<li><a
						href="${pageContext.request.contextPath}/servlet/NewsServlet?cid=<%=typeArr.get(i).getNews_type_id()%>"><%=typeArr.get(i).getNews_type_name()%></a></li>
					<%
						}
					%>
				</ul>
			</div>
		</div>

		<div class="center clear">
			<div class="news_articles clear">

				<!-- 进行分页显示 -->
				<%
					int currentPage = 1; //当前页
					int allCount = 0; //总记录数
					int allPageCount = 0; //总页数
					News news = null;
					//查看request中是否有currentPage信息，如没有，则说明首次访问该页
					if (request.getAttribute("allNews") != null) {
						//获取servlet返回的信息
						currentPage = ((Integer) request.getAttribute("currentPage")).intValue();
						ArrayList<News> list = (ArrayList<News>) request.getAttribute("allNews");
						allCount = ((Integer) request.getAttribute("allCount")).intValue();
						allPageCount = ((Integer) request.getAttribute("allPageCount")).intValue();
						if (list != null && list.size() > 0) {
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
						<!-- <span class="com-num">
                    <a href="#">6</a>
                </span> -->
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
				<!-- 分页显示 -->
				<div class="page">
					<nav>
						<ul class="pagination clear">
							<%-- <li>共（${allCount }）条纪录</li>
						<li>共（${allPageCount }）页</li>
						<li>当前页为第${currentPage }页</li> --%>
							<li><a href="NewsServlet?currentPage=1&cid=${news_type_id}">首页</a></li>
							<li><a
								href="NewsServlet?currentPage=<%=(currentPage-1)<1?1:(currentPage-1)%>&cid=${news_type_id}">上一页</a></li>
							<li><a
								href="NewsServlet?currentPage=<%=(currentPage+1)>allPageCount?allPageCount:(currentPage+1)%>&cid=${news_type_id}">下一页</a></li>
							<li><a
								href="NewsServlet?currentPage=<%=allPageCount%>&cid=${news_type_id}">末页</a></li>
						</ul>
					</nav>
				</div>

				<%
					}
				%>

			</div>

			<div class="top_list">
				<h3>点击排行榜</h3>
				<ul>
				<%
					ArrayList<News> listCount=(ArrayList<News>)request.getAttribute("listCount");
					for(int i=0;i<listCount.size();i++){
						News item=listCount.get(i);	
					
						if(i<3){
						
						
					
				%>
					<li class="red"><span><%=i+1 %></span> <a href="./NewsDetailServlet?id=<%=item.getId() %>"><%=item.getTitle() %></a></li>
					<%
						}else{
					%>
					
					<li class="gray"><span><%=i+1 %></span> <a href="./NewsDetailServlet?id=<%=item.getId() %>"><%=item.getTitle() %></a></li>
					<%
						}
					}
					%>
					
				</ul>
			</div>

		</div>

		<div id="footer">
			<h1>
				<a href="jsp/login.jsp">进入后台</a>
			</h1>
			<p>Copyright © 1998 - 2017 hxc. All Rights Reserved</p>
			<p>胡雄超 版权所有</p>
			<p>联系电话：18829292337</p>
			<p>联系邮箱：1732139715@qq.com</p>
		</div>

	</div>

<%
	String queryString=request.getQueryString();
	session.setAttribute("url", "./servlet/NewsServlet"+"?"+queryString);
%>
	<script type="text/javascript">
		if ("${news_type_id}") {
			var i = "${news_type_id}";

			var nav_menu = document.getElementsByClassName("nav_menu")[0];

			var arr = nav_menu.getElementsByTagName("a");
			arr[i].className = "active";
		}
		
	</script>
	<script src="js/jquery-3.2.0.min.js"></script>
	<script>
		var l="<%=length%>";
		var ave=(830-l*20)/l;
		$('.nav_menu a').css("width",ave+"px");
	</script>
</body>
</html>
