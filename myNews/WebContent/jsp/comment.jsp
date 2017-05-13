<!DOCTYPE HTML>
<%@page import="dao.impl.ReplyDAOImpl"%>
<html>
<head>
<%@ page import="entity.Comment,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<base href="<%=basePath%>">
<title>评论页</title>
<link href="./css/common.css" rel="stylesheet" />
<link rel="stylesheet" href="./css/detail.css" />
<link rel="stylesheet" href="./css/comment.css" />
<style>
body{
	width:100%;
	height:100%;
}
.button_group img {
	width: 25px;
	height: 25px;
}

#footer {
	position: relative;
	top: 400px;
}

#footer p {
	font-size: 12px;
}

.reply {
	display: none;
}

.replyBtn{
	background:url("./images/icons1.gif") no-repeat -443px -125px;
	padding-left:19px;
	font-size:12px;
	font-family: SimSun,Arial Narrow,HELVETICA;
	margin-left:35px;
	color:#999;
}

.replyBtn:hover{
	color:#1A649D;
	background-position:-443px -144px;
}
.view{
	font-size:12px;
	font-family: SimSun,Arial Narrow,HELVETICA;
	margin-left:20px;
	color:#999;
	padding-left:19px;
}

.viewImg{
	width:15px;
	height:15px;
	background:url("./images/icons1.gif") no-repeat -5px -83px;
	border:0px;
	vertical-align: middle;
}

.viewImg:hover{
	background-position:-43px -83px;
}
#bg {
	width: 100%;
	height: 100%;
	background-color: #000;
	position: absolute;
	top: 0;
	left: 0;
	z-index: 2;
	opacity: 0.6;
	/*兼容IE8及以下版本浏览器*/
	filter: alpha(opacity = 0.6);
	display: none;
}

#viewReply {
	width: 780px;
	height: 200px;
	background-color:#ffffff;
	margin: auto;
	position: absolute;
	z-index: 3;
	top: 0;
	bottom: 0;
	left: 0;
	right: 0;
	display: none;
}

textarea{
	resize: none;
	width: 100%;
	box-sizing: border-box;
	font-size: 14px;
	line-height: 24px;
	padding: 4px 14px;
	border-bottom: 1px solid #1d87e4;
	overflow: auto;
}
.loginReply,.commitReply{
	background:#1d87e4;
	color:#fff;
	line-height:30px;
	width:130px;
	font-size:16px;
	padding:10px;
	position:relative;
	left:655px;
}
</style>
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
				int count = ((Integer) request.getAttribute("allCount")).intValue();
			%>
			<h2>${title1}<span class="allCount">全部评论（共<%=count%>条）
				</span>
			</h2>

			<hr />

			<div class="comment clear">

				<!-- 进行分页显示 -->
				<%
					int currentPage = 1; //当前页
					int allCount = 0; //总记录数
					int allPageCount = 0; //总页数
					Comment comment = new Comment();
					//查看request中是否有currentPage信息，如没有，则说明首次访问该页
					if (request.getAttribute("allComments") != null) {
						//获取servlet返回的信息
						currentPage = ((Integer) request.getAttribute("currentPage")).intValue();
						ArrayList<Comment> list = (ArrayList<Comment>) request.getAttribute("allComments");
						allCount = ((Integer) request.getAttribute("allCount")).intValue();
						allPageCount = ((Integer) request.getAttribute("allPageCount")).intValue();
						if (list != null && list.size() > 0) {
							for (int i = 0; i < list.size(); i++) {
								comment = list.get(i);
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

						<div>
							<a
							href="javascript:;" class="replyBtn">回复</a>
							
							<%
								ReplyDAOImpl dao=new ReplyDAOImpl();
								int allcount=dao.getAllCount(comment.getId());
								if(allcount>0){
							%>
							
							<a href="./ReplyServlet?method=view&comment_id=<%=comment.getId() %>" class="view"><img src="./images/transparent.gif" class="viewImg"/>查看回复(<%=allcount %>)</a>
							<%
								}
							%>
						</div>
						
						<%
							if (session.getAttribute("username") == null) {
						%>
						<div class="reply">
							<form id="replyForm" method="post">
								<div>
									<textarea rows="5" cols="123"></textarea>
									<button class="loginReply">登录并回复</button>
								</div>

							</form>
						</div>

						<%
							} else {
								String username = (String) session.getAttribute("username");
						%>
						<div class="reply">
							<form id="replyForm" method="post" action="./ReplyServlet?method=add&comment_id=<%=comment.getId()%>&username=<%=username %>&news_id=<%=comment.getNews_id()%>">
								<div>
									<textarea rows="5" cols="123" name="replyContent"></textarea>
									<button class="commitReply">回复</button>
								</div>

							</form>
						</div>
						<%
							}
						%>
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
								href="CommentServlet?method=view&currentPage=1&news_id=${news_id}">首页</a></li>
							<li><a
								href="CommentServlet?method=view&currentPage=<%=(currentPage-1)<1?1:(currentPage-1)%>&news_id=${news_id}">上一页</a></li>
							<li><a
								href="CommentServlet?method=view&currentPage=<%=(currentPage+1)>allPageCount?allPageCount:(currentPage+1)%>&news_id=${news_id}">下一页</a></li>
							<li><a
								href="CommentServlet?method=viem&currentPage=<%=allPageCount%>&news_id=${news_id}">末页</a></li>
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

	</div>


	<div id="bg">
		<div id="viewReply"></div>
	</div>
	<%
		String queryString = request.getQueryString();
		session.setAttribute("url", "./CommentServlet" + "?" + queryString);
	%>

<script src="./js/jquery-3.2.0.min.js"></script>
	<script type="text/javascript">
		var aReply = document.getElementsByClassName("reply");
		var aReplyBtn = document.getElementsByClassName("replyBtn");
		var flag = [];
		for (var i = 0; i < aReply.length; i++) {
			(function(i) {
				flag[i] = false;
				aReplyBtn[i].onclick = function() {
					if (flag[i] === false) {
						flag[i] = true;
						aReply[i].style.display = "block";
					} else {
						flag[i] = false;
						aReply[i].style.display = "none";
					}

				}
			})(i);
		}

		var aLoginBtn = document.getElementsByClassName("loginReply");
		for (var i = 0; i < aLoginBtn.length; i++) {
			aLoginBtn[i].onclick = function() {
				alert("请登录之后再回复！！");
				window.location.href = "jsp/userLogin.jsp";
				return false;
			}
		}
		
		/* var aView = document.getElementsByClassName("view");
		for (var i = 0; i < aView.length; i++) {
			aView[i].onclick = function() {
				var s = document.getElementById("bg");
	            s.style.display = "block";

	            var l = document.getElementById("viewReply");
	            l.style.display = "block";
	            
	            document.body.style.overflow="hidden";
			}
		}
		
		$(document).bind('click', function(e) {
	        var e = e || window.event; //浏览器兼容性
	        var elem = e.target || e.srcElement;
	        while (elem) {
	            //循环判断至跟节点，防止点击的是div子元素
	            if (elem.id && elem.id == 'viewReply') {
	                return;
	            }
	            elem = elem.parentNode;
	        }
	        //点击的不是div或其子元素
	        $('#viewReply,#bg').hide();
	    }); */
		
	</script>
</body>
</html>
