package dao;

import entity.User;

public interface UserDAO {

	
	/**根据用户名和密码查找用户
	 * @param name
	 * @param pwd
	 * @return
	 */
	public boolean findUser(String name,String pwd);
	/**
	* 添加用户
	* @param user
	*/
	public boolean addUser(User user);
	/**根据用户名来查找用户
	* @param userName
	* @return 查到到的用户
	*/
	public boolean deleteUserById(int id);
	public User findUserByName(String name);
	public boolean findUserByEmail(String email);
	
}
