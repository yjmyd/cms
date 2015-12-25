/**   
* @Title: GroupDao.java 
* @Package org.liudan.cms.dao 
* @Description:  
* @author liudan 
* @date 2015年10月25日 上午11:03:26 
* @version V1.0   
*/
package org.liudan.cms.dao;

import java.util.List;

import org.liudan.basic.dao.BaseDao;
import org.liudan.basic.model.Pager;
import org.liudan.cms.model.Channel;
import org.liudan.cms.model.ChannelTree;
import org.liudan.cms.model.Group;
import org.liudan.cms.model.GroupChannel;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository("groupDao")
public class GroupDao extends BaseDao<Group> implements IGroupDao {

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IGroupDao#listGroup()
	 */
	@Override
	public List<Group> listGroup() {
		return this.list("from Group");
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IGroupDao#findGroup()
	 */
	@Override
	public Pager<Group> findGroup() {
		return this.find("from Group");
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IGroupDao#deleteGroupUsers(int)
	 */
	@Override
	public void deleteGroupUsers(int gid) {
		this.updateByHql("delete from UserGroup ug where ug.group.id=?", gid);
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IGroupDao#addGroupChannel(org.liudan.cms.model.Group, org.liudan.cms.model.Channel)
	 */
	@Override
	public void addGroupChannel(Group group, Channel channel) {
		GroupChannel gc = this.loadGroupChannel(group.getId(), channel.getId());
		if(gc!=null) return;
		gc = new GroupChannel();
		gc.setGroup(group);
		gc.setChannel(channel);
		this.getSession().save(gc);
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IGroupDao#loadGroupChannel(int, int)
	 */
	@Override
	public GroupChannel loadGroupChannel(int gid, int cid) {
		return (GroupChannel)this.queryObject("from GroupChannel gc where gc.group.id=? and gc.channel.id=?", new Object[]{gid,cid});
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IGroupDao#listGroupChannelIds(int)
	 */

	@Override
	public List<Integer> listGroupChannelIds(int gid) {
		String hql = "select gc.channel.id from GroupChannel gc where gc.group.id=?";
		return this.getSession().createQuery(hql).setParameter(0, gid).list();
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IGroupDao#deleteGroupChannel(int, int)
	 */
	@Override
	public void deleteGroupChannel(int gid, int cid) {
		this.updateByHql("delete GroupChannel where group.id=? and channel.id=?", new Object[]{gid,cid});
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IGroupDao#clearGroupChannel(int)
	 */
	@Override
	public void clearGroupChannel(int gid) {
		this.updateByHql("delete GroupChannel where group.id=?", new Object[]{gid});
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IGroupDao#generateGroupChannelTree(int)
	 */
	@Override
	public List<ChannelTree> generateGroupChannelTree(int gid) {
		String sql = "select c.id,c.name,c.pid from t_group_channel gc "
				+ " left join t_channel c on gc.c_id=c.id  where gc.g_id=?";
		List<ChannelTree> cts = this.listBySql(sql,gid,ChannelTree.class, false);
		ChannelDao.initTreeNode(cts);
		return cts;
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IGroupDao#generateUserChannelTree(int)
	 */
	@Override
	public List<ChannelTree> generateUserChannelTree(int uid) {
		String sql = "select distinct c.id as id,c.name as name,c.pid as pid from " +
				"t_group_channel gc left join t_channel c on(gc.c_id=c.id) left join t_user_group ug on(ug.g_id=gc.g_id)" +
				" where ug.u_id=?";
		List<ChannelTree> cts = this.listBySql(sql,uid,ChannelTree.class, false);
		ChannelDao.initTreeNode(cts);
		return cts;
	}

	

}
