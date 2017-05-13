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
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="Login and Registration Form with HTML5 and CSS3" />
<meta name="keywords"
	content="html5, css3, form, switch, animation, :target, pseudo-class" />
<meta name="author" content="Codrops" />
<link rel="shortcut icon" href="../favicon.ico">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/login/demo.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/login/style2.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/login/animate-custom.css" />
<base href="<%=basePath%>">
<title>用户登录注册</title>
</head>
<body>

	<div class="container">
		<header>
			<h1>新闻系统用户登录注册界面</h1>

		</header>
		<section>
			<div id="container_demo">
				<!-- hidden anchor to stop jump http://www.css3create.com/Astuce-Empecher-le-scroll-avec-l-utilisation-de-target#wrap4  -->
				<a class="hiddenanchor" id="toregister"></a> <a class="hiddenanchor"
					id="tologin"></a>
				<div id="wrapper">
					<div id="login" class="animate form">
						<form action="./LoginServlet?type=user" autocomplete="on"
							method="post">
							<h1>登录</h1>
							<p>
								<label for="username" class="uname" data-icon="u">用户名 </label> <input
									id="username" name="userName" required="required" type="text"
									placeholder="myusername or mymail@mail.com" />
							</p>
							<p>
								<label for="password" class="youpasswd" data-icon="p">
									密码 </label> <input id="password" name="password" required="required"
									type="password" placeholder="eg. X8df!90EO" />
							</p>
							<p class="keeplogin">
								<input type="checkbox" name="loginkeeping" id="loginkeeping"
									value="loginkeeping" /> <label for="loginkeeping">Keep
									me logged in</label>
							</p>
							<p class="login button">
								<input type="submit" value="登录" />
							</p>
							<p class="change_link">
								还没有账号？ <a
									href="<%=request.getContextPath()%>/jsp/userLogin.jsp#toregister"
									class="to_register">立即注册 </a>
							</p>
						</form>
					</div>

					<div id="register" class="animate form">
						<form action="./RegisterServlet" autocomplete="on" method="post">
							<h1>注册</h1>
							<p>
								<label for="usernamesignup" class="uname" data-icon="u">用户名</label>
								<input id="usernamesignup" name="username" required="required"
									type="text" placeholder="mysuperusername690" />
							</p>
							<p>
								<label for="emailsignup" class="youmail" data-icon="e">
									邮箱地址</label> <input id="emailsignup" name="email" required="required"
									type="email" placeholder="mysupermail@mail.com" />
							</p>
							<p>
								<label for="passwordsignup" class="youpasswd" data-icon="p">密码
								</label> <input id="passwordsignup" name="password" required="required"
									type="password" placeholder="eg. X8df!90EO" />
							</p>
							<p>
								<label for="passwordsignup_confirm" class="youpasswd"
									data-icon="p">请再次输入密码</label> <input
									id="passwordsignup_confirm" name="confirmPwd"
									required="required" type="password" placeholder="eg. X8df!90EO" />
							</p>
							<p class="signin button">
								<input type="submit" value="注册" />
							</p>
							<p class="change_link">
								已有账号？ <a
									href="<%=request.getContextPath()%>/jsp/userLogin.jsp#tologin"
									class="to_register"> 立即登录 </a>
							</p>
						</form>
					</div>

				</div>
			</div>
		</section>
		<div style="text-align: center; clear: both"></div>
	</div>

	<%
		/* System.out.println(request.getParameter("path")); */
		if (request.getParameter("path") != null) {
			session.setAttribute("url", request.getParameter("path"));
		}
	%>
	<script type="text/javascript">
		
	</script>
</body>
</html>
