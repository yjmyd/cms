/**   
* @Title: UserRole.java 
* @Package org.liudan.cms.dao 
* @Description:  
* @author liudan 
* @date 2015年10月25日 上午11:25:52 
* @version V1.0   
*/
package org.liudan.cms.dao;

import java.util.List;

import org.liudan.basic.dao.BaseDao;
import org.liudan.basic.model.Pager;
import org.liudan.cms.model.Group;
import org.liudan.cms.model.Role;
import org.liudan.cms.model.RoleType;
import org.liudan.cms.model.User;
import org.liudan.cms.model.UserGroup;
import org.liudan.cms.model.UserRole;
import org.springframework.stereotype.Repository;
@SuppressWarnings("unchecked")
@Repository("userDao")
public class UserDao extends BaseDao<User> implements IUserDao {
	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IUserDao#listUserRoles(int)
	 */
	public List<Role> listUserRoles(int userId) {
		String hql = "select ur.role from UserRole ur where ur.user.id=?";
		return this.getSession().createQuery(hql).setParameter(0, userId).list();
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IUserDao#listUserRoleIds(int)
	 */
	public List<Integer> listUserRoleIds(int userId) {
		String hql = "select ur.role.id from UserRole ur where ur.user.id=?";
		return this.getSession().createQuery(hql).setParameter(0, userId).list();
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IUserDao#listUserGroups(int)
	 */
	public List<Group> listUserGroups(int userId) {
		String hql = "select ug.group from UserGroup ug where ug.user.id=?";
		return this.getSession().createQuery(hql).setParameter(0, userId).list();
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IUserDao#listUserGroupIds(int)
	 */
	public List<Integer> listUserGroupIds(int userId) {
		String hql = "select ug.group.id from UserGroup ug where ug.user.id=?";
		return this.getSession().createQuery(hql).setParameter(0, userId).list();
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IUserDao#loadUserRole(int, int)
	 */
	public UserRole loadUserRole(int userId, int roleId) {
		String hql = "select ur from UserRole ur left join fetch ur.user u left join fetch ur.role r where u.id=? and r.id=?";
		return (UserRole)this.getSession().createQuery(hql).setParameter(0, userId).setParameter(1, roleId).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IUserDao#loadUserGroup(int, int)
	 */
	public UserGroup loadUserGroup(int userId, int groupId) {
		String hql = "select ug from UserGroup ug left join fetch ug.user u left join fetch ug.group g where u.id=? and g.id=?";
		return (UserGroup)this.getSession().createQuery(hql).setParameter(0, userId).setParameter(1, groupId).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IUserDao#loadByUsername(java.lang.String)
	 */
	public User loadByUsername(String username) {
		String hql = "from User where username=?";
		return (User)this.getSession().createQuery(hql).setParameter(0, username).uniqueResult();
		
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IUserDao#listRoleUsers(int)
	 */
	public List<User> listRoleUsers(int roleId) {
		String hql = "select ur.user from UserRole ur where ur.role.id=?";
		return this.list(hql, roleId);
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IUserDao#listRoleUsers(org.liudan.cms.model.RoleType)
	 */
	public List<User> listRoleUsers(RoleType roleType) {
		String hql = "select ur.user from UserRole ur where ur.role.roleType=?";
		return this.list(hql, roleType);
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IUserDao#listGroupUsers(int)
	 */
	public List<User> listGroupUsers(int gid) {
		String hql = "select ug.user from UserGroup ug where ug.group.id=?";
		return this.list(hql, gid);
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IUserDao#addUserRole(org.liudan.cms.model.User, org.liudan.cms.model.Role)
	 */
	@Override
	public void addUserRole(User user, Role role) {
		UserRole ur = this.loadUserRole(user.getId(), role.getId());
		if(ur!=null) return;
		ur = new UserRole();
		ur.setUser(user);
		ur.setRole(role);
		this.getSession().save(ur);
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IUserDao#addUserGroup(org.liudan.cms.model.User, org.liudan.cms.model.Group)
	 */
	@Override
	public void addUserGroup(User user, Group group) {
		UserGroup ug = this.loadUserGroup(user.getId(), group.getId());
		if(ug!=null) return;
		ug = new UserGroup();
		ug.setUser(user);
		ug.setGroup(group);
		this.getSession().save(ug);
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IUserDao#deleteUserRole(int)
	 */
	@Override
	public void deleteUserRole(int uid) {
		String hql = "delete from UserRole ur where ur.user.id=?";
		this.updateByHql(hql, uid);
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IUserDao#deleteUserGroup(int)
	 */
	@Override
	public void deleteUserGroup(int uid) {
		String hql = "delete from UserGroup ug where ug.user.id=?";
		this.updateByHql(hql, uid);
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IUserDao#findUser()
	 */
	@Override
	public Pager<User> findUser() {
		return this.find("from User");
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IUserDao#deleteUserRoles(int, int)
	 */
	@Override
	public void deleteUserRoles(int uid, int rid) {
		String hql = "delete from UserRole ur where ur.user.id=? and ur.role.id=?";
		this.updateByHql(hql, new Object[]{uid,rid});
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IUserDao#deleteUserGroups(int, int)
	 */
	@Override
	public void deleteUserGroups(int uid, int gid) {
		String hql = "delete from UserGroup ug where ug.user.id=? and ug.group.id=?";
		this.updateByHql(hql,new Object[]{uid,gid});
	}

	

}
