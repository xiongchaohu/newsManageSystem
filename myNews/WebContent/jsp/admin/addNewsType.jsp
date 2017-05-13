<!DOCTYPE HTML>
<html>
<head>
<%@ page language="java" import="entity.NewsType" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%-- <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> --%>
    
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="./css/common.css"/>
<link rel="stylesheet" href="./css/admin/admin_index.css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/admin/common.css"/>
<%-- <base href="<%=basePath%>"> --%>
<title>添加新闻类别</title>
</head>
<body>

<%@include file="./index.jsp"%>
<%
	if(request.getAttribute("newsType")!=null){
		NewsType newsType=(NewsType)request.getAttribute("newsType");
%>
<form action="./TypeManagerServlet?method=update&news_type_id=<%=newsType.getNews_type_id() %>" method="post" name="updateForm">

	<table>
		
		<tr>
			<td><label>修改前新闻类别名称：</label></td>
			<td><input type="text" placeholder="请输入新闻类别名称" name="typeName" value="<%=newsType.getNews_type_name() %>"/></td>
		</tr>
		
		<tr>
			<td><label>修改后的新闻类别名称:</label></td>
			<td><input type="text" placeholder="请输入修改后的新闻类别名称" name="newTypeName"/></td>
		</tr>
		<tr>
			<td><button type="submit">提交</button></td>
			<td><button id="cancle">取消</button></td>
		</tr>
	</table>
</form>

<%
	}else{
%>
<form action="./TypeManagerServlet?method=add" method="post" name="addNewsType">
	<table>
		
		<tr>
			<td><label>要添加的新闻类别名称：</label></td>
			<td><input type="text" placeholder="请输入新闻类别名称" name="typeName"/></td>
		</tr>
		
		<tr>
			<td><button type="submit">提交</button></td>
			<td><button id="cancle">取消</button></td>
		</tr>
	</table>
</form>

<%
	}
%>
	<script type="text/javascript">
		var cancleBtn = document.getElementById("cancle");
		cancleBtn.onclick = function() {
			history.back();
		}
	</script>
</body>
</html>
