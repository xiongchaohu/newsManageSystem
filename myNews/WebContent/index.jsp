<%@page import="dao.impl.NewsDAOImpl"%>
<%@page  import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"   pageEncoding="utf-8"%>
<%@ page import="dao.NewsDAO,entity.News" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
    
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<base href="<%=basePath%>">
<title>首页</title>
<link rel="stylesheet" href="css/common.css"/>
<style>

        /*中间内容样式*/
        #content{
            width:1000px;
            margin:0 auto;
        }

        #content .news_left{
            float:left;
            width:760px;
        }

        #content .side_bar{
            float: right;
            width:230px;
            height:310px;
            padding:10px 0;
            border:1px solid #cccccc;
            margin:20px auto;
        }

        /*中间div通用样式*/
        #content .news_common{
            width:340px;
            height: 310px;
            border:1px solid #E5E5E5;
            float:left;
            margin-left:15px;
            margin-top:20px;
            padding:10px;
            overflow:hidden;
        }

        #content .news_common .header{
            line-height:30px;
            border-bottom: 1px solid #E5E5E5;
            margin-bottom:10px;
        }

        #content .news_common h1{
            float:left;
            font-size:16px;
            background: url("images/list_ico.png") no-repeat 0px;
            padding-left: 26px;
        }

        #content .news_common span a{
            float:right;
            color: #9F9F9F;
        }

        #content .news_common .content a{
            overflow:hidden;
            line-height:28px;
            height:28px;
            font-size: 14px;
           color:#5a5a5a;
        }

        #content .news_common li{
            background: url("images/dot.gif") no-repeat 0 0;
            padding:0 0 0 15px;
            height: 28px;
            overflow: hidden;
        }

        /*登陆框样式表*/
        #login_form .inputBox{
            margin-top: 20px;
            position: relative;
            height: 40px;
        }

        #login_form .login_text{
            width: 150px;
            padding: 0 0 0 10px;
            line-height: 18px;
            height: 36px;
            font-size: 14px;
            position: absolute;
            left: 55px;
        }

        #login_form .userIcon, #login_form .passwordIcon {
            display: block;
            width: 40px;
            height: 40px;
            position: absolute;
            left: 15px;
        }

        #login_form .userIcon {
            background: url("images/login_image.png") no-repeat;
        }

        #login_form .passwordIcon {
            background: url("images/login_image.png") 0px -40px no-repeat;
        }

        #login_form .login_btn{
            width: 210px;
            height: 42px;
            line-height: 42px;
            font-size: 16px;
            font-family: "幼圆";
            cursor: pointer;
            margin: 20px 10px;
            outline: none;
            border-radius: 5px;
            background: #f7671d;
            border: 1px solid #f06923;
            color:#fff;
        }

        .side_bar .reg_link{
            padding:5px 10px;
            font-size:14px;
        }

        .side_bar .reg_link a{
            color: #f06923;
        }

        .side_bar .login_title h1{
            font-size:16px;
            line-height:30px;
            padding-left: 10px;
            height:30px;
            border-bottom: 1px solid #cccccc;
        }



    </style>
</head>
<body>
	
	<div id="wrap">
    <div id="header" class="clear">
        <div class="ns_area">
            <h1 class="logo">
                <a href="index.html">新闻连连看</a>
            </h1>

           <!-- <div class="button_group">
                <a href="#" id="login_btn">登录</a>
                <a href="#" id="register_btn">注册</a>
            </div>-->

            <div class="top_search">
                <input type="text" class="search_input" name="search_input" placeholder="请输入关键字"/>
                <button type="submit"></button>
            </div>


        </div>

        <div class="nav_menu clear">
            <ul>
                <li><a href="${pageContext.request.contextPath}/index.jsp" class="active">首页</a></li>
                <li><a href="${pageContext.request.contextPath}/servlet/NewsServlet?cid=1">国内</a></li>
                <li><a href="#">国际</a></li>
                <li><a href="#">娱乐</a></li>
                <li><a href="#">体育</a></li>
                <li><a href="#">军事</a></li>
                <li><a href="#">财经</a></li>
            </ul>
        </div>
        
        <div class="button_group">
            <a href="#" id="login_btn">登录</a>/
            <a href="#" id="register_btn">注册</a>
        </div>
    </div>

    <div id="content" class="clear">
        <div class="news_left clear">
            <div class="home_news news_common">
                <div class="header clear">
                    <h1>国内要闻</h1>
                    <span><a href="${pageContext.request.contextPath}/servlet/NewsServlet?cid=1">更多</a></span>
                </div>

                <div class="content">
                    <ul>
                    
                    <%
                    	NewsDAOImpl newsDao = new NewsDAOImpl();
                    	int news_type_id=1;
                    	ArrayList<News> list=newsDao.getAllNewsByTypeId(news_type_id);
                    	if(list!=null&&list.size()>0){
                    		for(int i=0;i<list.size();i++){
                    			News item = list.get(i);
                    			
                    		
                    %>
                        <li><a href="./NewsDetailServlet?id=<%=item.getId()%>" title=<%=item.getTitle() %>><%=item.getTitle() %></a></li>
                    <%
                    		
                    		}
                    	}
                    %>
                        
                    </ul>
                </div>
            </div>

            <div class="world_news news_common">
                <div class="header clear">
                    <h1>国际要闻</h1>
                    <span><a href="#">更多</a></span>
                </div>

                <div class="content">
                    <ul>
                    
                    <%
                    	
                    	ArrayList<News> list1=newsDao.getAllNewsByTypeId(++news_type_id);
                    	if(list1!=null&&list1.size()>0){
                			for(int i=0;i<list1.size();i++){
                				News item = list1.get(i);
                    	
                    		
                    %>
                        <li><a href="./NewsDetailServlet?id=<%=item.getId()%>" title=<%=item.getTitle() %>><%=item.getTitle() %></a></li>
                     <%
                    		}
                    	}
                     
                     %>
                        
                    </ul>
                </div>
            </div>

            <div class="ent_news news_common">
                <div class="header clear">
                    <h1>娱乐动态</h1>
                    <span><a href="#">更多</a></span>
                </div>

                <div class="content">
                    <ul>
                    
                    <%
                    	ArrayList<News> list2=newsDao.getAllNewsByTypeId(++news_type_id);
                		if(list2!=null&&list2.size()>0){
            				for(int i=0;i<list2.size();i++){
            					News item = list2.get(i);
                    	
                    		
                    %>
                        <li><a href="./NewsDetailServlet?id=<%=item.getId()%>" title=<%=item.getTitle() %>><%=item.getTitle() %></a></li>
                        
                    <% 
                    		}
                    	}
                    %>
                        
                    </ul>
                </div>
            </div>

            <div class="sports_news news_common">
                <div class="header clear">
                    <h1>体育频道</h1>
                    <span><a href="#">更多</a></span>
                </div>

                <div class="content">
                    <ul>
                    
                    <%
                    	ArrayList<News> list3=newsDao.getAllNewsByTypeId(++news_type_id);
            			if(list3!=null&&list3.size()>0){
        					for(int i=0;i<list3.size();i++){
        						News item = list3.get(i);
                    %>
                        <li><a href="./NewsDetailServlet?id=<%=item.getId()%>" title=<%=item.getTitle() %>><%=item.getTitle() %></a></li>
                    <%
        					}
            			}
                    %>
                        
                    </ul>
                </div>
            </div>

            <div class="mil_news news_common">
                <div class="header clear">
                    <h1>军事新闻</h1>
                    <span><a href="#">更多</a></span>
                </div>

                <div class="content">
                    <ul>
                    <%
                    	ArrayList<News> list4=newsDao.getAllNewsByTypeId(++news_type_id);
            			if(list4!=null&&list4.size()>0){
        					for(int i=0;i<list4.size();i++){
        						News item = list4.get(i);
                    %>
                        <li><a href="./NewsDetailServlet?id=<%=item.getId()%>" title=""><%=item.getTitle() %></a></li>
                        
                    <%
                    
        					}
            			}
                    %>
                        
                    </ul>
                </div>
            </div>

            <div class="finance_news news_common">
                <div class="header clear">
                    <h1>财经动态</h1>
                    <span><a href="#">更多</a></span>
                </div>

                <div class="content">
                    <ul>
                    
                    <%
                    	ArrayList<News> list5=newsDao.getAllNewsByTypeId(++news_type_id);
            			if(list5!=null&&list5.size()>0){
        					for(int i=0;i<list5.size();i++){
        						News item = list5.get(i);
                    %>
                        <li><a href="./NewsDetailServlet?id=<%=item.getId()%>" title=<%=item.getTitle() %>><%=item.getTitle() %></a></li>
                     <%
        					}
            			}
                     %>
                        
                    </ul>
                </div>
            </div>

        </div>

        <div class="side_bar">
            <div>
                <div class="login_title">
                    <h1>用户登录</h1>
                </div>

                <form id="login_form" action="" name="login_form" method="post">
                    <div class="inputBox">
                        <label for="userID" title="会员名" class="userIcon"></label>
                        <input type="text" class="login_text" name='userName' id="userID" placeholder="用户名/邮箱/电话"/>
                    </div>

                    <div class="inputBox">
                        <lable for="password" title="密码" class="passwordIcon"></lable>
                        <input type="password" class="login_text" name="password" id="password" placeholder="请输入密码"/>
                    </div>


                    <button class="login_btn">登录</button>

                </form>
                <p class="reg_link">还没有账号？<a href="#">立即注册</a></p>

            </div>

            <!--<div class="links">
                <h1>友情链接</h1>
                <ul>
                    <li><a href="http://news.163.com/">网易新闻网</a></li>
                    <li><a href="http://news.sina.com.cn/">新浪新闻网</a></li>
                    <li><a href="http://news.qq.com/">腾讯新闻网</a></li>
                    <li><a href="http://news.sohu.com/">搜狐新闻网</a></li>
                </ul>
            </div>-->
        </div>


    </div>

    <div id="footer">
        <h1><a href="#">进入后台</a></h1>
        <p>Copyright © 1998 - 2017 hxc. All Rights Reserved</p>
        <p>胡雄超  版权所有</p>
        <p>联系电话：18829292337</p>
        <p>联系邮箱：1732139715@qq.com</p>
    </div>
</div>

<!-- <script src="js/jquery-3.2.0.min.js"></script> -->
<script>


    var nav_menu=document.getElementsByClassName("nav_menu")[0];
    //使用事件冒泡机制给切换导航栏添加事件
    nav_menu.onclick=function (event){
        //获取类选择器为active的DOM元素设置类名为空
        var activeDom=document.getElementsByClassName("active");
        if(activeDom){
            activeDom[0].className="";
        }
        //事件冒泡机制
        var ev=event||window.event;
        var target=ev.target||e.srcElement;
        if(target.nodeName!=="A"){
            return false;
        }
        target.className="active";
    };

</script>
</body>
</html>
