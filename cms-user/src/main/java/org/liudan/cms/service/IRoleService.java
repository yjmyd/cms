/**   
* @Title: IRoleService.java 
* @Package org.liudan.cms.service 
* @Description:  
* @author liudan 
* @date 2015年10月27日 下午8:17:58 
* @version V1.0   
*/
package org.liudan.cms.service;

import java.util.List;

import org.liudan.basic.model.Pager;
import org.liudan.cms.model.Role;

public interface IRoleService {
	/**
	 * 添加角色
	 * @param role
	 */
	public void add(Role role);
	/**
	 * 删除角色
	 * @param id
	 */
	public void delete(int id);
	/**
	 * 更新角色
	 * @param role
	 */
	public void update(Role role);
	/**
	 * 载入角色
	 * @param id
	 * @return
	 */
	public Role load(int id);
	/**
	 * list 角色
	 * @return
	 */
	public List<Role> listRole();
	
	public Pager<Role> findRole();
	
	public void deleteRoleUsers(int rid);
	
}
