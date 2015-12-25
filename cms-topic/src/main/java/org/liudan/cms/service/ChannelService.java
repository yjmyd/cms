/**   
* @Title: ChannelService.java 
* @Package org.liudan.basic.service 
* @Description:  
* @author liudan 
* @date 2015年11月2日 下午5:25:11 
* @version V1.0   
*/
package org.liudan.cms.service;

import java.util.List;

import javax.inject.Inject;

import org.liudan.cms.dao.IChannelDao;
import org.liudan.cms.model.Channel;
import org.liudan.cms.model.ChannelTree;
import org.liudan.cms.model.CmsException;
import org.springframework.stereotype.Service;
@Service("channelService")
public class ChannelService implements IChannelService {
	@Inject
	private IChannelDao channelDao;
	/* (non-Javadoc)
	 * @see org.liudan.basic.service.IChannelService#add(java.nio.channels.Channel, java.lang.Integer)
	 */
	@Override
	public void add(Channel channel, Integer pid) {
		Integer orders = channelDao.getMaxOrderByParent(pid);
		if(pid!=null && pid>0){
			Channel cp = channelDao.load(pid);
			if(cp==null) throw new CmsException("要添加的父栏目不存在。");
			channel.setParent(cp);
		}
		channel.setOrders(orders+1);
		channelDao.add(channel);
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.service.IChannelService#update(java.nio.channels.Channel)
	 */
	@Override
	public void update(Channel channel) {
		channelDao.update(channel);
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.service.IChannelService#delete(int)
	 */
	@Override
	public void delete(int id) {
		List<Channel> list =  channelDao.listByParent(id);
		System.out.println("listsize" + list.size());
		if(list !=null && list.size()>0) throw new CmsException("要删除的栏目还有子栏目，不能删除");
		System.out.println("service:" + id);
		channelDao.delete(id);
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.service.IChannelService#clearTopic(int)
	 */
	@Override
	public void clearTopic(int id) {
		
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.service.IChannelService#listByParent(java.lang.Integer)
	 */
	@Override
	public List<Channel> listByParent(Integer pid) {
		return channelDao.listByParent(pid);
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.service.IChannelService#load(int)
	 */
	@Override
	public Channel load(int id) {
		return channelDao.load(id);
	}
	
	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IChannelService#generateTree()
	 */
	@Override
	public List<ChannelTree> generateTree() {
		return channelDao.generateTree();
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IChannelService#generateTreeByParentId(java.lang.Integer)
	 */
	@Override
	public List<ChannelTree> generateTreeByParentId(Integer pid) {
		return channelDao.generateTreeByParentId(pid);
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IChannelService#updateSort(java.lang.Integer[])
	 */
	@Override
	public void updateSort(Integer[] ids) {
		channelDao.updateSort(ids);
	}

}
