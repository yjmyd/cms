/**   
* @Title: GroupService.java 
* @Package org.liudan.cms.service 
* @Description:  
* @author liudan 
* @date 2015年10月27日 下午8:19:10 
* @version V1.0   
*/
package org.liudan.cms.service;

import java.util.List;

import javax.inject.Inject;

import org.liudan.basic.model.Pager;
import org.liudan.cms.dao.IChannelDao;
import org.liudan.cms.dao.IGroupDao;
import org.liudan.cms.dao.IUserDao;
import org.liudan.cms.model.Channel;
import org.liudan.cms.model.ChannelTree;
import org.liudan.cms.model.CmsException;
import org.liudan.cms.model.Group;
import org.liudan.cms.model.GroupChannel;
import org.liudan.cms.model.User;
import org.springframework.stereotype.Service;
@Service("groupService")
public class GroupService implements IGroupService {
	@Inject
	private IGroupDao groupDao;
	@Inject
	private IUserDao userDao;
	@Inject 
	IChannelDao channelDao;



	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IGroupService#listGroup()
	 */
	@Override
	public List<Group> listGroup() {
		return groupDao.listGroup();
	}


	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IGroupService#add(org.liudan.cms.model.Group)
	 */
	@Override
	public void add(Group group) {
		groupDao.add(group);
	}


	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IGroupService#delete(int)
	 */
	@Override
	public void delete(int id) {
		List<User> user = userDao.listGroupUsers(id);
		if(user!=null && user.size()>0) throw new CmsException("要删除的组中还有用户，不能删除。");
		groupDao.delete(id);
	}


	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IGroupService#update(org.liudan.cms.model.Group)
	 */
	@Override
	public void update(Group group) {
		groupDao.update(group);
	}


	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IGroupService#load(int)
	 */
	@Override
	public Group load(int id) {
		return groupDao.load(id);
	}


	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IGroupService#findGroup()
	 */
	@Override
	public Pager<Group> findGroup() {
		return groupDao.findGroup();
	}


	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IGroupService#deleteGroupUsers(int)
	 */
	@Override
	public void deleteGroupUsers(int gid) {
		groupDao.deleteGroupUsers(gid);
	}


	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IGroupService#addGroupChannel(org.liudan.cms.model.Group, org.liudan.cms.model.Channel)
	 */
	@Override
	public void addGroupChannel(int gid, int cid) {
		Group group = groupDao.load(gid);
		Channel channel = channelDao.load(cid);
		if(group==null || channel==null) throw new CmsException("要添加的组频道关联不存在。");
		groupDao.addGroupChannel(group, channel);
	}


	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IGroupService#loadGroupChannel(int, int)
	 */
	@Override
	public GroupChannel loadGroupChannel(int gid, int cid) {
		return groupDao.loadGroupChannel(gid, cid);
	}


	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IGroupService#listGroupChannelIds(int)
	 */
	@Override
	public List<Integer> listGroupChannelIds(int gid) {
		return groupDao.listGroupChannelIds(gid);
	}


	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IGroupService#deleteGroupChannel(int, int)
	 */
	@Override
	public void deleteGroupChannel(int gid, int cid) {
		groupDao.deleteGroupChannel(gid, cid);
	}


	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IGroupService#clearGroupChannel(int)
	 */
	@Override
	public void clearGroupChannel(int gid) {
		groupDao.clearGroupChannel(gid);
	}


	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IGroupService#generateGroupChannelTree(int)
	 */
	@Override
	public List<ChannelTree> generateGroupChannelTree(int gid) {
		return groupDao.generateGroupChannelTree(gid);
	}


	/* (non-Javadoc)
	 * @see org.liudan.cms.service.IGroupService#generateUserChannelTree(int)
	 */
	@Override
	public List<ChannelTree> generateUserChannelTree(int uid) {
		return groupDao.generateUserChannelTree(uid);
	}

}
