<!DOCTYPE HTML>
<%@page import="entity.NewsType"%>
<%@page import="entity.News"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.impl.NewsDAOImpl"%>
<html>
<head>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%
	request.setCharacterEncoding("utf-8");
%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/common.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/admin/admin_index.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/admin/editNews.css" />
<title>发布新闻</title>
</head>
<body>

	<%@include file="./index.jsp"%>

	<div class="editWrap">

		<%
			News news = new News();
			news = (News) request.getAttribute("news");
		%>
		<form id="editNews" name="editNews"
			action="./NewsManagerServlet?method=update&id=<%=news.getId()%>"
			method="post">


			<table>
				<tr>
					<td><label for="title">新闻标题：</label></td>
					<td><input type="text" id="title" name="title"
						value="<%=news.getTitle()%>" /></td>
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
					<td><textarea id="content" rows="20" cols="40" name="content"
							value="<%=news.getContent()%>">
					<%=news.getContent()%>	
                </textarea></td>
				</tr>

				<tr>
					<td><button type="submit">提交</button></td>
					<td><button onclick="cancle()">取消</button></td>
				</tr>
			</table>

		</form>


	</div>

	<!-- </div> -->



	<!-- </div> -->


	<script type="text/javascript"
		src="<%=request.getContextPath()%>/ckeditor/ckeditor.js"></script>
	<script>
		function cancle() {
			history.back();
		}

		CKEDITOR.replace('content');
	</script>
</body>
</html>
