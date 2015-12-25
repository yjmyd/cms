/**   
* @Title: UserService.java 
* @Package org.liudan.cms.service 
* @Description:  
* @author liudan 
* @date 2015年10月26日 下午6:01:53 
* @version V1.0   
*/
package org.liudan.cms.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.liudan.basic.model.Pager;
import org.liudan.basic.util.SecurityUtil;
import org.liudan.cms.dao.IGroupDao;
import org.liudan.cms.dao.IRoleDao;
import org.liudan.cms.dao.IUserDao;
import org.liudan.cms.model.CmsException;
import org.liudan.cms.model.Group;
import org.liudan.cms.model.Role;
import org.liudan.cms.model.User;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService implements IUserService {

	private IUserDao userDao;
	private IRoleDao roleDao;
	private IGroupDao groupDao;
	
	/**
	 * @return the userDao
	 */
	public IUserDao getUserDao() {
		return userDao;
	}

	/**
	 * @param userDao the userDao to set
	 */
	@Inject
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * @return the roleDao
	 */
	public IRoleDao getRoleDao() {
		return roleDao;
	}

	/**
	 * @param roleDao the roleDao to set
	 */
	@Inject
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	/**
	 * @return the groupDao
	 */
	public IGroupDao getGroupDao() {
		return groupDao;
	}

	/**
	 * @param groupDao the groupDao to set
	 */
	@Inject
	public void setGroupDao(IGroupDao groupDao) {
		this.groupDao = groupDao;
	}

	private void addUserRole(User user,int rid){
		Role role = roleDao.load(rid);
		if(role==null) throw new CmsException("要添加的角色不存在。");
		userDao.addUserRole(user, role);
	}
	private void addUserGroup(User user,int gid){
		Group group = groupDao.load(gid);
		if(group==null){throw new CmsException("要添加的组不存在。");}
		userDao.addUserGroup(user, group);
	}
	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IUserService#add(org.liudan.cms.model.User, java.lang.Integer[], java.lang.Integer[])
	 */
	@Override
	public void add(User user, Integer[] rids, Integer[] gids) throws NoSuchAlgorithmException {
		User u = userDao.loadByUsername(user.getUsername());
		if(u!=null) throw new CmsException("添加的用户对象已经存在，不能添加。");
		user.setPassword(SecurityUtil.md5(user.getUsername(),user.getPassword()));
		userDao.add(user);
		//添加角色对象
		for (Integer rid:rids) {
			this.addUserRole(user, rid);
		}
		//添加组对象
		for (Integer gid:gids) {
			this.addUserGroup(user, gid);
		}
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IUserService#delete(int)
	 */
	@Override
	public void delete(int id) {
		//TODO 需要进行用户是否有文章的判断
		//删除关联角色
		userDao.deleteUserRole(id);
		//删除关联组
		userDao.deleteUserGroup(id);
		//删除用户
		userDao.delete(id);
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IUserService#update(org.liudan.cms.model.User, java.lang.Integer[], java.lang.Integer[])
	 */
	@Override
	public void update(User user, Integer[] rids, Integer[] gids) {
		List<Integer> erids = userDao.listUserRoleIds(user.getId());
		List<Integer> egids = userDao.listUserGroupIds(user.getId());
		//添加
		for (Integer rid : rids) {
			if(!erids.contains(rid)){
				this.addUserRole(user, rid);
			}
		}
		for (Integer gid : gids) {
			if(!egids.contains(gid)){
				this.addUserGroup(user, gid);
			}
		}
		//删除
		for (Integer erid : erids) {
			if(!ArrayUtils.contains(rids, erid)){
				userDao.deleteUserRoles(user.getId(), erid);
			};
		}
		for (Integer egid : egids) {
			if(!ArrayUtils.contains(gids, egid)){
				userDao.deleteUserGroups(user.getId(), egid);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IUserService#updateStatus(int)
	 */
	@Override
	public void updateStatus(int id) {
		User user = userDao.load(id);
		if(user==null) throw new CmsException("要修改的用户不存在");
		if(user.getStatus()==0){user.setStatus(1);}else{
			user.setStatus(0);
		}
		userDao.update(user);
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IUserService#findUser()
	 */
	@Override
	public Pager<User> findUser() {
		return userDao.findUser();
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IUserService#load(int)
	 */
	@Override
	public User load(int id) {
		return userDao.load(id);
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IUserService#listUserRoles(int)
	 */
	@Override
	public List<Role> listUserRoles(int id) {
		return userDao.listUserRoles(id);
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IUserService#listUserGroups(int)
	 */
	@Override
	public List<Group> listUserGroups(int id) {
		return userDao.listUserGroups(id);
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IUserService#listUserRoleIds(int)
	 */
	@Override
	public List<Integer> listUserRoleIds(int id) {
		return userDao.listUserGroupIds(id);
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IUserService#listUserGroupIds(int)
	 */
	@Override
	public List<Integer> listUserGroupIds(int id) {
		return userDao.listUserGroupIds(id);
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IUserService#listGroupUsers(int)
	 */
	@Override
	public List<User> listGroupUsers(int gid) {
		return userDao.listGroupUsers(gid);
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IUserService#listRoleUsers(int)
	 */
	@Override
	public List<User> listRoleUsers(int rid) {
		return userDao.listRoleUsers(rid);
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IUserService#login(java.lang.String, java.lang.String)
	 */
	@Override
	public User login(String username, String password) {
		User u  = userDao.loadByUsername(username);
		if(u==null) throw new CmsException("用户名或密码不正确");
		try {
			if(!SecurityUtil.md5(username,password).equals(u.getPassword())){
				throw new CmsException("用户名或密码不正确");
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if(u.getStatus()==0) throw new CmsException("用户已经停用，请与管理员联系");
		return u;
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IUserService#update(org.liudan.cms.model.User)
	 */
	@Override
	public void update(User user) {
		userDao.update(user);
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IUserService#updatePwd(int, java.lang.String, java.lang.String)
	 */
	@Override
	public void updatePwd(int uid, String oldPwd, String newPwd) throws NoSuchAlgorithmException {
		User u = userDao.load(uid);
		if(!SecurityUtil.md5(u.getUsername(), oldPwd).equals(u.getPassword())){
			throw new CmsException("原始密码输入不正确");
		}
		u.setPassword(SecurityUtil.md5(u.getUsername(), newPwd));
		userDao.update(u);
	}



}
