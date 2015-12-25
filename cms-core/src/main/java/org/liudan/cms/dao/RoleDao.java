/**   
* @Title: RoleDao.java 
* @Package org.liudan.cms.dao 
* @Description:  
* @author liudan 
* @date 2015年10月25日 上午11:04:04 
* @version V1.0   
*/
package org.liudan.cms.dao;

import java.util.List;

import org.liudan.basic.dao.BaseDao;
import org.liudan.basic.model.Pager;
import org.liudan.cms.model.Role;
import org.springframework.stereotype.Repository;

@Repository("roleDao")
public class RoleDao extends BaseDao<Role> implements IRoleDao {

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IRoleDao#listRole()
	 */
	@Override
	public List<Role> listRole() {
		return this.list("from Role");
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IRoleDao#findRole()
	 */
	@Override
	public Pager<Role> findRole() {
		return this.findRole();
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IRoleDao#deleteRoleUsers(int)
	 */
	@Override
	public void deleteRoleUsers(int rid) {
		this.updateByHql("delete from UserRole ur where ur.role.id=?", rid);
	}


}
