<!DOCTYPE HTML>
<html>
<head>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@page import="entity.News"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<base href="<%=basePath%>">
<title>新闻正文</title>
<link href="./css/common.css" rel="stylesheet" />
<link rel="stylesheet" href="./css/detail.css" />
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
				<a href="#" id="login_btn">登录</a>/ <a href="#" id="register_btn">注册</a>
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
			<%
				News news = (News) request.getAttribute("result");
			%>
			<div class="posTitle">
				<span class="pos"><a href="index1.jsp">新闻中心</a>>><a
					href="${pageContext.request.contextPath}/servlet/NewsServlet?cid=<%=news.getNews_type_id()%>"><%=request.getAttribute("pos")%>新闻</a>>><a
					href="#">新闻正文</a></span>
			</div>
		</div>

		<div></div>

		<article class="content">


			<h1 class="title"><%=news.getTitle()%></h1>
			<p class="time"><%=news.getCreat_time()%></p>
			<hr />
			<div>
				<p>
					<%=news.getContent()%>
				</p>


			</div>


			<div id="comment">
				<h1>
					评论<a href="./CommentServlet?method=view&news_id=<%=news.getId()%>">共(<%=request.getAttribute("allCount")%>)条评论
					</a>
				</h1>

				<%
					if (session.getAttribute("username") == null) {
				%>
				<form name="" action="./jsp/userLogin.jsp" method="post">
					<input type="hidden" name="path" id="hide" />
					<textarea></textarea>
					<div>

						<button class="loginComment" id="loginComment">登录并评论</button>
					</div>
				</form>
				<%
					} else {
						String username = (String) session.getAttribute("username");
				%>
				<form
					action="./CommentServlet?method=add&news_id=<%=news.getId()%>&username=<%=username%>"
					method="post" name="commentForm">

					<textarea name="commentContent"></textarea>
					<div>
						<button class="submit">发表评论</button>
					</div>

				</form>


				<%
					}
				%>


			</div>

			<%
				/* /* System.out.println("111");
				System.out.println(basePath);
				System.out.println("222");
					String url=request.getRequestURI() + "?" + request.getQueryString();
					session.setAttribute("url", url);
				System.out.println(session.getAttribute("url")); */
				/*String   url=request.getScheme()+"://";   
				url+=request.getHeader("host");   
				url+=request.getRequestURI();   
				if(request.getQueryString()!=null)   
				      url+="?"+request.getQueryString();   
				    String aa=request.getQueryString();
				System.out.println(url);  */
			%>
		</article>
		<div id="footer">
			<h1>
				<a href="jsp/admin/login.jsp">进入后台</a>
			</h1>
			<p>Copyright © 1998 - 2017 hxc. All Rights Reserved</p>
			<p>胡雄超 版权所有</p>
			<p>联系电话：18829292337</p>
			<p>联系邮箱：1732139715@qq.com</p>
		</div>
	</div>

	<script type="text/javascript" src="./js/jquery-3.2.0.min.js"></script>
	<script type="text/javascript">
		$("#loginComment").click(function() {
			alert("请登录之后再进行评论！！！");
			/* window.location.href = "jsp/userLogin.jsp"; */
			/* return false; */
		});

		$("#hide").val(location.href);
		/* console.log($("#hide").val());
		
		console.log(location.href); */
	</script>
</body>
</html>
