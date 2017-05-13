package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.impl.CommentDAOImpl;
import dao.impl.NewsDAOImpl;
import entity.News;

/**
 * Servlet implementation class News_detail
 */
@WebServlet(name = "NewsDetailServlet", urlPatterns = { "/NewsDetailServlet" })
public class News_detail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public News_detail() {
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
		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String str = request.getParameter("id");
		News result = null;
		if (str != null) {
			int id = Integer.parseInt(str);
			NewsDAOImpl dao = new NewsDAOImpl();
			CommentDAOImpl cDao = new CommentDAOImpl();
			result = dao.getNewsById(id);

			cDao.getAllCommentByNewsId(1, id);
			int count = cDao.getAllCount();
			String newsType = dao.findTypeNameByType(result.getNews_type_id());

			request.setAttribute("result", result);
			dao.updateCount(result.getNews_count(), result.getId());
			request.setAttribute("pos", newsType);
			request.setAttribute("allCount", count);
			request.getRequestDispatcher("/jsp/detail.jsp").forward(request, response);
		}
	}

}
