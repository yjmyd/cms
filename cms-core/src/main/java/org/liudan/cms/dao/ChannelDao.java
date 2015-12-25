/**   
* @Title: ChannelDao.java 
* @Package org.liudan.cms.dao 
* @Description:  
* @author liudan 
* @date 2015年10月30日 下午5:34:50 
* @version V1.0   
*/
package org.liudan.cms.dao;

import java.util.List;

import org.liudan.basic.dao.BaseDao;
import org.liudan.cms.model.Channel;
import org.liudan.cms.model.ChannelTree;
import org.springframework.stereotype.Repository;

@Repository("channelDao")
public class ChannelDao extends BaseDao<Channel> implements IChannelDao {
	
	public static void initTreeNode(List<ChannelTree> cts) {
		cts.add(0,new ChannelTree(Channel.ROOT_ID,Channel.ROOT_NAME,-1));
		for(ChannelTree ct:cts) {
			if(ct.getPid()==null)ct.setPid(0);
		}
	}
	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IChannelDao#listByParent()
	 */
	@Override
	public List<Channel> listByParent(Integer pid) {
		String hql = "select c from Channel c left join fetch c.parent cp  where cp.id="+pid + " order by c.orders";
		if(pid==null || pid==0) hql = "select c from Channel c left join fetch c.parent cp where cp is null order by c.orders";
		return this.list(hql);
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IChannelDao#getMaxOrderByParent(int)
	 */
	@Override
	public int getMaxOrderByParent(Integer pid) {
		String hql = "select max(c.orders) from Channel c where c.parent.id="+pid;
		if(pid==null) hql = "select max(c.orders) from Channel c where c.parent is null";
		Object obj = this.queryObject(hql);
		if(obj==null){
			return 0;
		}
		return (Integer)obj;
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IChannelDao#generateTree()
	 */
	@Override
	public List<ChannelTree> generateTree() {
		String sql = "select id,name,pid from t_channel order by orders asc";
		List<ChannelTree> cts = this.listBySql(sql, ChannelTree.class, false);
		initTreeNode(cts);
		return cts;
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IChannelDao#generateTreeByParentId(java.lang.Integer)
	 */
	@Override
	public List<ChannelTree> generateTreeByParentId(Integer pid) {
		if(pid!=null && pid>0){
			return this.listBySql("select id,name,pid from t_channel where pid="+pid +" order by orders ",ChannelTree.class,false);
		}else{
			String sql = "select id,name,pid from t_channel where pid is null order by orders ";
			return this.listBySql(sql, ChannelTree.class, false);
		}
	}

	/* (non-Javadoc)
	 * @see org.liudan.cms.dao.IChannelDao#updateSort(java.lang.Integer[])
	 */
	@Override
	public void updateSort(Integer[] ids) {
		int index = 1;
		String hql = "update Channel c set c.orders=?  where c.id=?";
		for (Integer id : ids) {
			this.updateByHql(hql, new Object[]{index++,id});
		}
	}

}
