package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.NewsDAO;
import dao.impl.NewsDAOImpl;
import dao.impl.UserDAOImpl;
import entity.News;
import entity.NewsType;
import entity.User;

/**
 * Servlet implementation class TypeManagerServlet
 */
@WebServlet("/TypeManagerServlet")
public class TypeManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TypeManagerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setContentType("text/html;charset=utf-8");

		String method = request.getParameter("method");

		if (method.equals("view")) {
			view(request, response);
		} else if (method.equals("delete")) {
			delete(request, response);
		} else if (method.equals("add")) {
			add(request, response);
		} else if (method.equals("edit")) {
			edit(request, response);
		} else if (method.equals("update")) {
			update(request, response);
		}
	}

	private void view(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<NewsType> list = new ArrayList<NewsType>();
		NewsDAOImpl dao = new NewsDAOImpl();

		list = dao.getAllType();

		request.setAttribute("allNewsType", list);

		try {
			request.getRequestDispatcher("/jsp/admin/typeManager.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		boolean result = false;
		int id = Integer.parseInt(request.getParameter("news_type_id"));
		if (id > 0) {
			NewsDAOImpl dao = new NewsDAOImpl();
			result = dao.deleteNewsTypeById(id);
			if (result) {
				out.println(
						"<script type='text/javascript'>alert('删除成功!');window.location.href='./TypeManagerServlet?method=view'</script>");
			} else {
				out.println("<script type='text/javascript'>alert('删除失败!');JavaScript:history.back();");
			}

		}

	}

	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		String typeName = request.getParameter("typeName");
		NewsType newsType = new NewsType();

		if (typeName.equals("")) {
			out.println("<script type='text/javascript'>alert('类别为必填项，不能为空，请输入!');JavaScript:history.back();</script>");
			return;
		}

		newsType.setNews_type_name(typeName);
		NewsDAOImpl dao = new NewsDAOImpl();
		System.out.println("类名");
		System.out.println(typeName);

		try {
			// 判断是否存在该类别，若存在，则提示已存在，请重新输入；否则进行新闻类别的添加
			if (dao.isExistType(typeName)) {
				out.println("<script type='text/javascript'>alert('所添加内容已经存在，请重新输入!');history.back();</script>");
			} else {// 若不存在该类别则添加数据库
				boolean result = dao.addNewsType(newsType);
				if (result) {

					/* request.setAttribute("result", "添加成功!"); */
					out.println(
							"<script type='text/javascript'>alert('添加成功!');window.location.href='./TypeManagerServlet?method=view'</script>");
				} else {
					/* request.setAttribute("result", "添加失败!"); */
					out.println("<script type='text/javascript'>alert('添加失败!');history.back();</script>");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NewsDAOImpl dao = new NewsDAOImpl();
		int id = Integer.parseInt(request.getParameter("news_type_id"));
		NewsType newsType = new NewsType();

		if (id > 0) {
			newsType = dao.findTypeNameByTypeId(id);
		}

		request.setAttribute("newsType", newsType);

		request.getRequestDispatcher("jsp/admin/addNewsType.jsp").forward(request, response);
	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		int id = Integer.parseInt(request.getParameter("news_type_id"));
		String newTypeName = request.getParameter("newTypeName");

		/*
		 * if (email.equals("") || username.equals("")) { out.println(
		 * "<script type='text/javascript'>alert('邮箱、用户名为必填项，请检查是否输入!');JavaScript:history.back();</script>"
		 * ); return; }
		 */

		NewsDAOImpl dao = new NewsDAOImpl();
		NewsType newsType = new NewsType();

		newsType.setNews_type_id(id);
		newsType.setNews_type_name(newTypeName);

		boolean result = dao.updateNewsType(newsType);
		try {
			if (result) {
				out.println(
						"<script type='text/javascript'>alert('更新成功!');window.location.href='./TypeManagerServlet?method=view'</script>");
			} else {
				out.println(
						"<script type='text/javascript'>alert('更新失败，请重新检查输入!');JavaScript:history.back();</script>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
