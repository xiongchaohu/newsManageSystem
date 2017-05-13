<!DOCTYPE HTML>
<%@page import="entity.NewsType"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.impl.NewsDAOImpl"%>
<html>
<head>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%-- <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> --%>

<%
	request.setCharacterEncoding("utf-8");
%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/admin/editNews.css" />
	
<%-- <base href="<%=basePath%>"> --%>

<title>发布新闻</title>
</head>
<body>

	<%@include file="./index.jsp"%>

	<div class="editWrap">
		<form id="editNews" name="editNews"
			action="./NewsManagerServlet?method=add" method="post">

			<table>
				<tr>
					<td><label for="title">新闻标题：</label></td>
					<td><input type="text" id="title" name="title" /></td>
				</tr>

				<tr>
					<td><label for="type">新闻类别：</label></td>
					<td><select id="type" name="type">
							<%
								NewsDAOImpl dao = new NewsDAOImpl();
								ArrayList<NewsType> list = new ArrayList<NewsType>();
								list = dao.getAllType();
								for (int i = 0; i < list.size(); i++) {
							%>
							<option value="<%=list.get(i).getNews_type_id()%>"><%=list.get(i).getNews_type_name()%></option>

							<%
								}
							%>

					</select></td>
				</tr>

				<tr>

					<td><label for="content">新闻内容：</label></td>
					<td><textarea id="" rows="20" cols="40" name="content">

                </textarea></td>
				</tr>

				<tr>
					<td>
						<button type="submit">提交</button>
					</td>
					<td>
						<button class="cancle" onclick="cancle()">取消</button>
					</td>
				</tr>
			</table>

		</form>
	</div>

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/ckeditor/ckeditor.js"></script>

	<script type="text/javascript">
		CKEDITOR.replace('content');

		function cancle() {
			window.history.back();
			
		}
	</script>
</body>
</html>
