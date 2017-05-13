<%@page import="java.awt.event.ItemEvent"%>
<%@page import="entity.NewsType"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page
	import="dao.impl.NewsDAOImpl,entity.News,entity.NewsType,dao.NewsDAO"%>
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
<title>首页</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/common.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/index.css" />
<!-- <style>
	#header .button_group {
	position: absolute;
	width: 100px;
	top: 35px;
	left: 1120px;
} -->
</style>
</head>
<body>

	<div id="wrap">
		<div id="header" class="clear">
			<div class="ns_area">
				<h1 class="logo">
					<a href="index1.jsp">新闻连连看</a>
				</h1>


				<form action="./SearchServlet">

					<div class="top_search">
						<input type="text" class="search_input" name="title"
							placeholder="请输入关键字" value="${title }" />
						<button id="search" type="submit"></button>
					</div>
				</form>
			</div>

			<div class="nav_menu clear">
				<ul>
					<li><a href="${pageContext.request.contextPath}/index.jsp"
						class="active">首页</a></li>
					<!-- 从数据库中获取新闻类别即导航条内容 -->
					<%
						NewsDAOImpl newsDao = new NewsDAOImpl();
						ArrayList<NewsType> typeArr = newsDao.getTypeName();
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
			<!-- 判断用户是否登录，若登录则显示登录信息，否则显示登录注册 -->
			<%
				if (session.getAttribute("username") == null) {
			%>
			<div class="button_group">
				<a href="jsp/userLogin.jsp#toLogin" id="login_btn">登录</a>/ <a
					href="jsp/userLogin.jsp#toregister" id="register_btn">注册</a>
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


		</div>

		<div id="content" class="clear">
			<div class="news_left clear">
				<!-- //根据新闻类别生成几个div -->
				<%
					for (int i = 0; i < typeArr.size(); i++) {
						NewsType item1 = typeArr.get(i);
				%>
				<div class="home_news news_common">
					<div class="header clear">
						<h1><%=item1.getNews_type_name()%></h1>
						<span><a
							href="${pageContext.request.contextPath}/servlet/NewsServlet?cid=<%=item1.getNews_type_id()%>">更多</a></span>
					</div>

					<div class="content">
						<ul>
							<!--  显示新闻 -->
							<%
								ArrayList<News> list = newsDao.getAllNewsByTypeId(item1.getNews_type_id());

									if (list != null && list.size() > 0) {
										for (int j = 0; j < list.size(); j++) {
											News item = list.get(j);
							%>
							<li><a href="./NewsDetailServlet?id=<%=item.getId()%>"
								title=<%=item.getTitle()%>><%=item.getTitle()%></a></li>
							<%
								}
									}
							%>

						</ul>
					</div>
				</div>
				<%
					}
				%>

			</div>

			<div class="side_bar">

				<%
					if (session.getAttribute("username") == null) {
				%>
				<div class="login_wrap">
					<div class="login_title">
						<h1>用户登录</h1>
					</div>

					<form id="login_form" action="./LoginServlet?type=user"
						name="login_form" method="post">
						<div class="inputBox">
							<label for="userID" title="会员名" class="userIcon"></label> <input
								type="text" class="login_text" name='userName' id="userID"
								placeholder="用户名/邮箱/电话" />
						</div>

						<div class="inputBox">
							<lable for="password" title="密码" class="passwordIcon"></lable>
							<input type="password" class="login_text" name="password"
								id="password" placeholder="请输入密码" />
						</div>


						<button class="login_btn" type="submit" onclick="fcheck()">登录</button>

					</form>
					<p class="reg_link">
						还没有账号？<a href="jsp/userLogin.jsp#toregister">立即注册</a>
					</p>

				</div>


				<%
					}
				%>

				<div id="links">
					<h1 class="title">友情链接</h1>
					<ul>
						<li><a href="http://news.163.com/">网易新闻网</a></li>
						<li><a href="http://news.sina.com.cn/">新浪新闻网</a></li>
						<li><a href="http://news.qq.com/">腾讯新闻网</a></li>
						<li><a href="http://news.sohu.com/">搜狐新闻网</a></li>
						<li><a href="http://www.thepaper.cn/">澎湃新闻网</a></li>
					</ul>
				</div>
			</div>


		</div>

		<div id="footer">
			<h2>
				<a href="jsp/admin/login.jsp">进入后台</a>
			</h2>
			<p>Copyright © 1998 - 2017 hxc. All Rights Reserved</p>
			<p>胡雄超 版权所有</p>
			<p>联系电话：18829292337</p>
			<p>联系邮箱：1732139715@qq.com</p>
		</div>
	</div>

	<%
		session.setAttribute("url", "./index1.jsp");
	%>
	<script src="js/jquery-3.2.0.min.js"></script>
	<script src="js/index1.js">
		
	</script>
	<script>
		var l="<%=length%>";
		var ave=(830-l*20)/l;
		$('.nav_menu a').css("width",ave+"px");
	</script>

</body>
</html>
