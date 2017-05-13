<!DOCTYPE HTML>
<html>
<head>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Title</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/common.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/admin/admin_index.css">
<base href="<%=basePath%>">
<title>管理员首页</title>

</head>
<body>

	<div id="wrap">
		<div id="header" class="clear">
			<div class="ns_area clear">
				<h1 class="logo">
					<a href="index.html">新闻后台管理系统</a>
				</h1>

				<div class="btn_group">
					<span class="home_btn"> <a href="./index1.jsp"><img
							src="./images/home_page.png" />首页</a>
					</span> <span class="user"> <img src="./images/user.png" /> <%
 	if (session.getAttribute("adminname") != null) {
 %> <span>您好：<%=session.getAttribute("adminname")%></span>
						<%
							}
						%>
					</span> <span class="exit"> <a href="./ExitServlet?type=admin">
							<img src="./images/exit_btn.png" />注销
					</a>
					</span>
				</div>
			</div>
		</div>


		<div id="center">
			<div class="nav">
				<div>
					<h3 class="list_title">
						<a class="triangle_down_red" href="javascript:void(0)"></a>新闻管理中心
					</h3>
					<ul>
						<li><a href="./NewsManagerServlet?method=view">新闻信息管理</a></li>
						<li><a href="./jsp/admin/editNews.jsp">发布新闻管理</a></li>
						<li><a href="./TypeManagerServlet?method=view">新闻分类管理</a></li>
					</ul>
				</div>

				<div>
					<h3 class="list_title">
						<a class="triangle_down_red" href="javascript:void(0)"></a>用户管理中心
					</h3>
					<ul>
						<li><a href="./UserManagerServlet?method=view">用户管理</a></li>
						<li><a href="./jsp/admin/editUser.jsp">添加用户</a></li>
					</ul>
				</div>

				<div>
					<h3 class="list_title">
						<a class="triangle_down_red" href="javascript:void(0)"></a>评论管理中心
					</h3>
					<ul>
						<li><a href="./CommentServlet?method=reviewAll">评论管理</a></li>
					</ul>
				</div>

				<div>
					<h3 class="list_title">
						<a class="triangle_down_red" href="javascript:void(0)"></a>个人中心
					</h3>
					<ul>
						<li><a href="./jsp/admin/changePwd.jsp">密码修改</a></li>

					</ul>
				</div>
			</div>
		</div>

	</div>

	<script type="text/javascript" src="./js/jquery-3.2.0.min.js"></script>
	<script>
	//设置点击隐藏显示效果
		$(".list_title").each(function(){
			$(this).click(function(){
				var a=$(this).children("a");
				if(a.attr("class")==="triangle_down_red"){
					$(this).siblings('ul').css("display","none");
					a.attr("class","triangle_up")
				}else if(a.attr("class")==="triangle_up"){
					$(this).siblings('ul').css("display","block");
					a.attr("class","triangle_down_red")
				}
					
			});
		})
	</script>

</body>
</html>
