/**   
* @Title: RoleService.java 
* @Package org.liudan.cms.service 
* @Description:  
* @author liudan 
* @date 2015年10月27日 下午8:18:51 
* @version V1.0   
*/
package org.liudan.cms.service;

import java.util.List;

import javax.inject.Inject;

import org.liudan.basic.model.Pager;
import org.liudan.cms.dao.IRoleDao;
import org.liudan.cms.dao.IUserDao;
import org.liudan.cms.model.CmsException;
import org.liudan.cms.model.Role;
import org.liudan.cms.model.User;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleService implements IRoleService {
	
	private IRoleDao roleDao;
	
	@Inject
	private IUserDao userDao;
	
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



	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IRoleService#listRole()
	 */
	@Override
	public List<Role> listRole() {
		return roleDao.listRole();
	}
	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IRoleService#add(org.liudan.cms.model.Role)
	 */
	@Override
	public void add(Role role) {
		roleDao.add(role);
	}
	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IRoleService#delete(int)
	 */
	@Override
	public void delete(int id) {
		List<User> user = userDao.listRoleUsers(id);
		if(user!=null && user.size()>0) throw new CmsException("要删除的角色下有用户，不能删除。");
		roleDao.delete(id);
	}
	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IRoleService#update(org.liudan.cms.model.Role)
	 */
	@Override
	public void update(Role role) {
		roleDao.update(role);
	}
	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IRoleService#load(int)
	 */
	@Override
	public Role load(int id) {
		return roleDao.load(id);
	}
	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IRoleService#deleteRoleUsers(int)
	 */
	@Override
	public void deleteRoleUsers(int rid) {
		roleDao.deleteRoleUsers(rid);
	}
	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IRoleService#findRole()
	 */
	@Override
	public Pager<Role> findRole() {
		return roleDao.findRole();
	}

}
