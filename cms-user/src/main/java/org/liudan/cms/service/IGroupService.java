/**   
* @Title: IGroupService.java 
* @Package org.liudan.cms.service 
* @Description:  
* @author liudan 
* @date 2015年10月27日 下午8:18:06 
* @version V1.0   
*/
package org.liudan.cms.service;

import java.util.List;

import org.liudan.basic.model.Pager;
import org.liudan.cms.model.ChannelTree;
import org.liudan.cms.model.Group;
import org.liudan.cms.model.GroupChannel;

public interface IGroupService {
	/**
	 * 添加组
	 * @param group
	 */
	public void add(Group group);
	/**
	 * 删除组
	 * @param id
	 */
	public void delete(int id);
	/**
	 * 更新组
	 * @param group
	 */
	public void update(Group group);
	/**
	 * 查询组
	 * @param id
	 * @return
	 */
	public Group load(int id);
	/**
	 * list 组
	 * @return
	 */
	public List<Group> listGroup();
	/**
	 * 分页组
	 * @return
	 */
	public Pager<Group> findGroup();
	
	public void deleteGroupUsers(int gid);
	
	public void addGroupChannel(int gid,int cid);
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
