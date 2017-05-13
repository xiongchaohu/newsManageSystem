<!DOCTYPE HTML>
<html>
<head>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
    
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<base href="<%=basePath%>">
<link rel="stylesheet" href="css/common.css"/>
    <link rel="stylesheet" href="css/login&sign.css"/>
    <style>
        .wrap {
            width: 400px;
            margin: 0 auto;
            border: 1px solid #ddd;
            border-radius: 5px;
            background: #F3F7F8;
        }

        .logo {
            background: url("images/未标题-2.png") no-repeat;
            width: 100%;
            height: 100px;
        }

        .wrap .nav {
            height: 40px;
            background: #B7D1D6;
            border-radius: 5px;
        }

        .wrap .nav li {
            float: left;
            font-size: 24px;
            width: 50%;
            line-height: 40px;
            text-align: center;
            cursor: pointer;
            border-top-left-radius: 5px;
            border-top-right-radius: 5px;
            font-weight: bold;
        }

        .wrap .nav .active {
            background: #F3F7F8;
            margin-bottom: 1px;
        }

        #login_box {
            width: 300px;
            padding: 25px 25px 23px;
            color: #6c6c6c;
            margin: 0 auto;

        }

        #loginForm .inputBox, #registerForm .inputBox {
            margin-top: 20px;
            position: relative;
            height: 40px;
        }

        #loginForm .login_text, #registerForm .login_text {
            width: 230px;
            padding: 0 0 0 10px;
            line-height: 18px;
            height: 36px;
            font-size: 14px;
            position: absolute;
            left: 55px;
        }

        #registerForm {
            display: none;
        }

        #registerForm .login_text {
            left: 110px;
        }

        #loginForm .userIcon, #loginForm .passwordIcon {
            display: block;
            width: 40px;
            height: 40px;
            position: absolute;
            left: 15px;
        }

        #loginForm .userIcon {
            background: url("images/login_image.png") no-repeat;
        }

        #loginForm .passwordIcon {
            background: url("images/login_image.png") 0px -40px no-repeat;
        }

        #loginForm .login_btn, #registerForm .register_btn {
            width: 300px;
            height: 42px;
            line-height: 42px;
            font-size: 16px;
            background: #B7D1D6;
            font-family: "幼圆";
            cursor: pointer;
            margin-top: 20px;
            outline: none;
            border-radius: 5px;
        }

        #registerForm .register_btn {
            margin: 20px auto;
            width: 246px;
            position: relative;
            left: 110px;
        }

        #registerForm .inputBox .info {
            display: block;
            width: 100px;
            height: 40px;
            line-height: 40px;
            position: absolute;
            left: 1px;
            text-align: right;
        }

        #registerForm .inputBox .tip, #registerForm .inputBox .error {
            position: absolute;
            top: 40px;
            left: 110px;
            line-height: 20px;
            font-size: 12px;
            color: #6c6c6c;
        }
    </style>
    <title>登录与注册页面</title>
</head>
<body>
<h1 class="logo"></h1>

<div class="wrap">
    <!-- 导航条 -->
    <ul class="nav">
        <li class="active" id="login_nav">用户登录</li>
        <li id="register_nav">快速注册</li>
    </ul>
    <!-- 注册框 -->
    <div id="register_box">
        <form id="registerForm" name="registerForm" action="register" method="post" action="./RegisterServlet">

            <div class="inputBox">
                <label for="reg_email" class="info">邮箱:</label>
                <input type="text" class="login_text" name='userName' id="reg_email" placeholder="请输入邮箱(必填项)" onblur="checkEmail(this.value)?"/>
                <span class="tip" id="emailTip"></span>
                <span class="error"></span>
            </div>

            <div class="inputBox">
                <lable for="reg_name" class="info">用户名:</lable>
                <input type="text" class="login_text" name="password" id="reg_name" placeholder="请设置用户名" onblur=""/>
                <span class="tip" id="usernameTip">
                </span>
            </div>

            <div class="inputBox">
                <lable for="reg_password" class="info">密 码:</lable>
                <input type="password" class="login_text" name="password" id="reg_password" placeholder="请输入密码" onblur=""/>
                <span class="tip" id="passwordTip"></span>
            </div>

            <div class="inputBox">
                <lable for="reg_second_password" class="info">确认密码:</lable>
                <input type="password" class="login_text" name="password" id="reg_second_password"
                       placeholder="请再次输入密码" onblur=""/>
                <span class="tip" id="confirmPwdTip"></span>
            </div>


            <button class="register_btn">注册</button>

        </form>

    </div>

    <!-- 登录框 -->
    <div id="login_box">
        <form id="loginForm" name="loginForm" action="" method="post">
            <div class="inputBox">
                <label for="userID" title="会员名" class="userIcon"></label>
                <input type="text" class="login_text" name='userName' id="userID" placeholder="用户名/邮箱/电话" data-validate="required:必填"/>
            </div>

            <div class="inputBox">
                <lable for="password" title="密码" class="passwordIcon"></lable>
                <input type="password" class="login_text" name="password" id="password" placeholder="请输入密码"/>
            </div>


            <button class="login_btn">登录</button>

        </form>
    </div>
</div>

<script type="text/javascript" src="./js/jquery-3.2.0.min.js"></script>
<script type="text/javascript">

    var loginNav = document.getElementById("login_nav");
    var registerNav = document.getElementById("register_nav");

    var loginBox = document.getElementById("login_box");
    var registerBox = document.getElementById("registerForm");

    //控制导航条来分别显示登录和注册框
    loginNav.onclick = function () {
        loginBox.style.display = "block";
        registerBox.style.display = "none";
        loginNav.className = "active";
        registerNav.className = "";
    };

    registerNav.onclick = function () {
        loginBox.style.display = "none";
        registerBox.style.display = "block";
        registerNav.className = "active";
        loginNav.className = "";
    };

    var aInput = registerBox.getElementsByTagName("input");
    var aSpan = document.getElementsByClassName("tip");
    //var errorsSpan = document.getElementsByClassName("error");

    //控制文本框焦点提示信息
    
    /* $("#reg_email").click(function(){
    	$("#emailTip").html("请输入正确的邮箱格式");
    });
    
    $("#reg_email").blur(function(){
    	if(checkEmail($("#reg_email").val())){
    		$("#emailTip").html()	
    	}
    	
    })
     */
    (function () {
        var tipArray = ["请输入正确的邮箱格式", "至少6位最多14位，可包含英文，数字，下划线", "6-16个字符，建议使用字母加数字或符号组合", "请再次输入密码"]
        for (var i = 0; i < aInput.length; i++) {
            aInput[i].index = i;
            aInput[i].onfocus = function () {

                aSpan[this.index].innerHTML = tipArray[this.index];
            };

            /* aInput[i].onblur = function () {
            	if()
                
            }; */
        }
    })();


    
    //前端校验
    function checkEmail(email) {
        var re = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
        if (re.test(email)) {
            return true;
        } else {
            return false;
        }
    }

    function checkUserName(str) {
        var re = /^[a-zA-z]\w{3,13}$/;
        if (re.test(str)) {
            return true;;
        } else {
            return false;
        }
    }

    function checkPassWord(password) {
        return /^[0-9a-zA-Z_]{6,14}$/.test(password);
    }
    
    function confirmPwd(password1,password2){
    	if(password1==password2){
    		return true;
    	}
    	return false;
    }


</script>


</body>
</html>
