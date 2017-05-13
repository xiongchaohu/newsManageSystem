<!DOCTYPE HTML>
<html>
<head>
<%@ page language="java" contentType="text/html; charset=utf-8"
	import="entity.Comment,java.util.*,entity.Reply" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<base href="<%=basePath%>">
<link href="./css/common.css" rel="stylesheet" />
<link rel="stylesheet" href="./css/detail.css" />
<link rel="stylesheet" href="./css/comment.css" />
<style>
body {
	width: 100%;
	height: 100%;
}

.button_group img {
	width: 25px;
	height: 25px;
}

#footer {
	position: relative;
	top: 200px;
}

#footer p {
	font-size: 12px;
}

.page {
	margin: 50px auto;
	padding-left: 50px;
	text-align: center;
	position:relative;
	left:150px;
}

.page nav {
	margin: 0 auto;
}

.page li {
	float: left;
	margin: 0 30px;
}
</style>
<title>查看回复页</title>
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
						<button type="submit"></button>
					</div>
				</form>

			</div>

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

		<div class="center clear">
			<%
				Comment comment = (Comment) request.getAttribute("comment");
			%>
			<div class="article">
				<div class="news_title clear">
					<h3>
						<img src="images/userImage.png" /> <span class="username"><%=comment.getUsername()%></span>
					</h3>
					<!--  使用toLocalString()将timestamp后的.0去掉 -->
					<span class="time"><%=comment.getTime().toLocaleString()%></span>
				</div>

				<div class="clear">
					<p class="content"><%=comment.getContent()%></p>
				</div>
			</div>
			<hr />

				<!-- 进行分页显示 -->
				<%
					int currentPage = 1; //当前页
					int allCount = 0; //总记录数
					int allPageCount = 0; //总页数
					Reply reply = new Reply();
					//查看request中是否有currentPage信息，如没有，则说明首次访问该页
					if (request.getAttribute("allReplys") != null) {
						//获取servlet返回的信息
						currentPage = ((Integer) request.getAttribute("currentPage")).intValue();
						ArrayList<Reply> list = (ArrayList<Reply>) request.getAttribute("allReplys");
						allCount = ((Integer) request.getAttribute("allCount")).intValue();
						allPageCount = ((Integer) request.getAttribute("allPageCount")).intValue();
						if (list != null && list.size() > 0) {
							for (int i = 0; i < list.size(); i++) {
								reply = list.get(i);
				%>
				<div class="article" style="margin-left: 40px;">
					<div class="news_title clear">
						<h3>
							<img src="images/userImage.png" /> <span class="username"><%=reply.getUsername()%>
								回复 <%=comment.getUsername()%></span>
						</h3>
						<!--  使用toLocalString()将timestamp后的.0去掉 -->
						<span class="time"><%=reply.getTime().toLocaleString()%></span>
					</div>

					<div class="clear">
						<p class="content"><%=reply.getContent()%></p>
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
						<li><a
							href="./ReplyServlet?method=view&currentPage=1&comment_id=${comment_id}">首页</a></li>
						<li><a
							href="./ReplyServlet?method=view&currentPage=<%=(currentPage-1)<1?1:(currentPage-1)%>&comment_id=${comment_id}">上一页</a></li>
						<li><a
							href="./ReplyServlet?method=view&currentPage=<%=(currentPage+1)>allPageCount?allPageCount:(currentPage+1)%>&comment_id=${comment_id}">下一页</a></li>
						<li><a
							href="./ReplyServlet?method=viem&currentPage=<%=allPageCount%>&comment_id=${comment_id}">末页</a></li>
					</ul>
				</nav>
			</div>

			<%
				}
			%>
		</div>
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


</body>
</html>
