/**   
* @Title: IChannelService.java 
* @Package org.liudan.basic.service 
* @Description:  
* @author liudan 
* @date 2015年11月2日 下午5:06:05 
* @version V1.0   
*/
package org.liudan.cms.service;

import java.util.List;

import org.liudan.cms.model.Channel;
import org.liudan.cms.model.ChannelTree;

public interface IChannelService {
	
	public Channel load(int id);
	
 	public void add(Channel channel,Integer pid);
	
	public void update(Channel channel);
	
	public void delete(int id);
	
	public void clearTopic(int id);
	
	public List<Channel> listByParent(Integer pid);
	
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
	
	public void updateSort(Integer[] ids);
	

}
