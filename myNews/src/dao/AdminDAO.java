package dao;

import entity.Admin;

public interface AdminDAO {

	
	/**检查管理员用户名和密码是否正确
	 * @param name 用户名
	 * @param password 密码
	 * @return  true or false
	 */
	public boolean isAdmin(String name,String password);
	
	/**修改管理员密码
	 * @param id  管理员id
	 * @param password 管理员的新密码
	 * @return  若修改成功，则返回true，否则false
	 */
	public boolean updatePassword(int id,String password);
	
	/**根据用户名找到管理员
	 * @param name 用户名
	 * @return  该管理员信息
	 */
	public Admin findAdminByName(String name);
}
