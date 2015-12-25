/**   
* @Title: IChannelDao.java 
* @Package org.liudan.cms.dao 
* @Description:  
* @author liudan 
* @date 2015年10月30日 下午5:30:22 
* @version V1.0   
*/
package org.liudan.cms.dao;

import java.util.List;

import org.liudan.basic.dao.IBasicDao;
import org.liudan.cms.model.Channel;
import org.liudan.cms.model.ChannelTree;

public interface IChannelDao extends IBasicDao<Channel> {
	public List<Channel> listByParent(Integer pid);
	public int getMaxOrderByParent(Integer pid);
	/**
	 * 获取所有栏目
	 * @return
	 */
	public List<ChannelTree> generateTree();
	/**
	 * 根据父ID查询子栏目
	 * @param pid
	 * @return
	 */
	public List<ChannelTree> generateTreeByParentId(Integer pid);
	/**
	 * 更新排序
	 * @param ids
	 */
	public void updateSort(Integer[] ids);
}
