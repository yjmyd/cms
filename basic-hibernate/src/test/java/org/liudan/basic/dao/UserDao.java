/**   
* @Title: UserDao.java 
* @Package org.liudan.basic.dao 
* @Description:  
* @author liudan 
* @date 2015年10月22日 下午2:17:50 
* @version V1.0   
*/
package org.liudan.basic.dao;

import java.util.List;
import java.util.Map;

import org.liudan.basic.model.Pager;
import org.liudan.basic.model.Users;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDao extends BaseDao<Users> implements IUserDao {

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IUserDao#listBySql(java.lang.String, java.lang.Object[], java.lang.Class, boolean)
	 */
	@Override
	public List<Users> listUserBySql(String string, Object[] objects,
			Class<Users> class1, boolean b) {
		return super.listBySql(string, objects,class1, b);
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IUserDao#listBySql(java.lang.String, java.lang.Object[], java.util.Map, java.lang.Class, boolean)
	 */
	@Override
	public List<Users> listUserBySql(String string, Object[] objects,
			Map<String, Object> args, Class<Users> class1, boolean b) {
		return super.listBySql(string, objects, args, class1, b);
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IUserDao#findBySql(java.lang.String, java.lang.Object[], java.lang.Class, boolean)
	 */
	@Override
	public Pager<Users> findUserBySql(String string, Object[] objects,
			Class<Users> class1, boolean b) {
		return super.findBySql(string, objects,class1, b);
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IUserDao#findBySql(java.lang.String, java.lang.Object[], java.util.Map, java.lang.Class, boolean)
	 */
	@Override
	public Pager<Users> findUserBySql(String string, Object[] objects,
			Map<String, Object> args, Class<Users> class1, boolean b) {
		return super.findBySql(string, objects, args, class1, b);
	}

	
}
