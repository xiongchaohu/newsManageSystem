package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AdminDAO;
import dao.impl.AdminDAOImpl;

/**
 * Servlet implementation class ChangePwdServlet
 */
@WebServlet("/ChangePwdServlet")
public class ChangePwdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChangePwdServlet() {
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

		PrintWriter out = response.getWriter();

		String name = request.getParameter("name");
		String oldPwd = request.getParameter("oldPwd");
		String newPwd = request.getParameter("newPwd");

		String confirmPwd = request.getParameter("confirmPwd");

		if (!newPwd.equals(confirmPwd)) {
			out.println("<script>alert('修改失败,新密码输入不一致');JavaScript:history.back();</script>");
		} else {
			AdminDAOImpl dao = new AdminDAOImpl();
			if (dao.isAdmin(name, oldPwd)) {
				int id = dao.findAdminByName(name).getId();
				if (dao.updatePassword(id, newPwd)) {
					out.println("<script>alert('密码修改成功！');window.location.href='./jsp/admin/index.jsp'</script>");
				} else {
					out.println("<script>alert('修改失败！！');JavaScript:history.back();</script>");
				}
			} else {
				out.println("<script>alert('旧密码与原先不一致，请重新输入');JavaScript:history.back();</script>");
			}
		}

	}

}
