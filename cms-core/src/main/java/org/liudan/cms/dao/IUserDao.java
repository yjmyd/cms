/**   
* @Title: IUserDao.java 
* @Package org.liudan.cms.dao 
* @Description:  
* @author liudan 
* @date 2015年10月25日 上午11:00:13 
* @version V1.0   
*/
package org.liudan.cms.dao;

import java.util.List;

import org.liudan.basic.dao.IBasicDao;
import org.liudan.basic.model.Pager;
import org.liudan.cms.model.Group;
import org.liudan.cms.model.Role;
import org.liudan.cms.model.RoleType;
import org.liudan.cms.model.User;
import org.liudan.cms.model.UserGroup;
import org.liudan.cms.model.UserRole;

public interface IUserDao extends IBasicDao<User> {
	/**
	 * 获取用户的所有角色信息
	 * @param userId
	 * @return
	 */
	public List<Role> listUserRoles(int userId);
	/**
	 * 获取用户的所有角色ID
	 * @param userId
	 * @return
	 */
	public List<Integer> listUserRoleIds(int userId);
	/**
	 * 获取用户的所有组信息
	 * @param userId
	 * @return
	 */
	public List<Group> listUserGroups(int userId);
	/**
	 * 获取用户的所有组id
	 * @param userId
	 * @return
	 */
	public List<Integer> listUserGroupIds(int userId);
	/**
	 * 根据用户和角色获取用户角色的关联对象
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public UserRole loadUserRole(int userId,int roleId);
	/**
	 * 根据用户和组获取用户组关联对象
	 * @param userId
	 * @param groupId
	 * @return
	 */
	public UserGroup loadUserGroup(int userId,int groupId);
	/**
	 * 根据用户名获取用户对象
	 */
	public User loadByUsername(String username);
	/**
	 * 根据角色id获取用户列表
	 * @param roleId
	 * @return
	 */
	public List<User> listRoleUsers(int roleId);
	/**
	 * 根据用户角色类型获取用户列表
	 * @param roleType
	 * @return
	 */
	public List<User> listRoleUsers(RoleType roleType);
	/**
	 * 根据组ID获取用户列表
	 * @param gid
	 * @return
	 */
	public List<User> listGroupUsers(int gid);
	/**
	 * 添加用户角色
	 * @param user
	 * @param role
	 */
	public void addUserRole(User user,Role role);
	/**
	 * 添加用户组
	 * @param user
	 * @param group
	 */
	public void addUserGroup(User user,Group group);
	/**
	 * 删除用户角色
	 * @param uid
	 */
	public void deleteUserRole(int uid);
	/**
	 * 删除用户组
	 * @param uid
	 */
	public void deleteUserGroup(int uid);
	
	
	public Pager<User> findUser();
	
	public void deleteUserRoles(int uid,int rid);
	
	public void deleteUserGroups(int uid,int gid);
	
	
	
	
	
	
}
