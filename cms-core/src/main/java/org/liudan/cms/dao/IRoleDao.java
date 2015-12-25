/**   
* @Title: IRoleDao.java 
* @Package org.liudan.cms.dao 
* @Description:  
* @author liudan 
* @date 2015年10月25日 上午11:01:54 
* @version V1.0   
*/
package org.liudan.cms.dao;

import java.util.List;

import org.liudan.basic.dao.IBasicDao;
import org.liudan.basic.model.Pager;
import org.liudan.cms.model.Role;

public interface IRoleDao extends IBasicDao<Role> {
	public List<Role> listRole();
	public Pager<Role> findRole();
	public void deleteRoleUsers(int rid);
}
