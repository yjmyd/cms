/**   
* @Title: IGroupDao.java 
* @Package org.liudan.cms.dao 
* @Description:  
* @author liudan 
* @date 2015年10月25日 上午11:01:26 
* @version V1.0   
*/
package org.liudan.cms.dao;

import java.util.List;

import org.liudan.basic.dao.IBasicDao;
import org.liudan.basic.model.Pager;
import org.liudan.cms.model.Channel;
import org.liudan.cms.model.ChannelTree;
import org.liudan.cms.model.Group;
import org.liudan.cms.model.GroupChannel;

public interface IGroupDao extends IBasicDao<Group> {
	public List<Group> listGroup();
	public Pager<Group> findGroup();
	public void deleteGroupUsers(int gid);
	public void addGroupChannel(Group group,Channel channel);
	public GroupChannel loadGroupChannel(int gid,int cid);
	public List<Integer> listGroupChannelIds(int gid);
	public void  deleteGroupChannel(int gid,int cid);
	public void clearGroupChannel(int gid);
	/**
	 * 获取某个组的栏目
	 * @param gid
	 * @return
	 */
	public List<ChannelTree> generateGroupChannelTree(int gid);
	/**
	 * 获取某个用户的栏目
	 * @param uid
	 * @return
	 */
	public List<ChannelTree> generateUserChannelTree(int uid);
}
