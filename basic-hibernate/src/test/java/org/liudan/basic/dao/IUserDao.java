/**   
* @Title: IUserDao.java 
* @Package org.liudan.basic.dao 
* @Description:  
* @author liudan 
* @date 2015年10月22日 下午2:13:57 
* @version V1.0   
*/
package org.liudan.basic.dao;

import java.util.List;
import java.util.Map;

import org.liudan.basic.model.Pager;
import org.liudan.basic.model.Users;

public interface IUserDao extends IBasicDao<Users> {

	/**
	 * @param string
	 * @param objects
	 * @return
	 */
	List<Users> list(String string, Object[] objects);

	/**
	 * @param string
	 * @param objects
	 * @param args
	 * @return
	 */
	List<Users> list(String string, Object[] objects, Map<String, Object> args);

	/**
	 * @param string
	 * @param objects
	 * @return
	 */
	Pager<Users> find(String string, Object[] objects);

	/**
	 * @param string
	 * @param objects
	 * @param args
	 * @return
	 */
	Pager<Users> find(String string, Object[] objects, Map<String, Object> args);

	/**
	 * @param string
	 * @param objects
	 * @param class1
	 * @param b
	 * @return
	 */
	List<Users> listUserBySql(String string, Object[] objects, Class<Users> class1,
			boolean b);

	/**
	 * @param string
	 * @param objects
	 * @param args
	 * @param class1
	 * @param b
	 * @return
	 */
	List<Users> listUserBySql(String string, Object[] objects,
			Map<String, Object> args, Class<Users> class1, boolean b);

	/**
	 * @param string
	 * @param objects
	 * @param class1
	 * @param b
	 * @return
	 */
	Pager<Users> findUserBySql(String string, Object[] objects,
			Class<Users> class1, boolean b);

	/**
	 * @param string
	 * @param objects
	 * @param args
	 * @param class1
	 * @param b
	 * @return
	 */
	Pager<Users> findUserBySql(String string, Object[] objects,
			Map<String, Object> args, Class<Users> class1, boolean b);

}
