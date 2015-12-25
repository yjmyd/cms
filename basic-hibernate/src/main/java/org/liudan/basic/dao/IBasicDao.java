/**   
* @Title: IBasicDao.java 
* @Package org.liudan.basic.dao 
* @Description:  公告DAO处理对象，包括了Hibernate对数据的基本的SQL操作
* @author liudan 
* @date 2015年10月21日 下午2:26:41 
* @version V1.0   
*/
package org.liudan.basic.dao;


public interface IBasicDao<T> {
	/**
	 * 添加对象
	 * @param t
	 * @return
	 */
	public T add(T t);
	/**
	 * 更新对象
	 * @param t
	 */
	public void update(T t);
	/**
	 * 删除对象
	 * @param id
	 */
	public void delete(int id);
	/**
	 * 查询对象
	 * @param id
	 * @return
	 */
	public T load(int id);
}
