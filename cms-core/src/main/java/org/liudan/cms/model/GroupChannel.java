/**   
* @Title: GroupChannel.java 
* @Package org.liudan.cms.model 
* @Description:  
* @author liudan 
* @date 2015年11月4日 下午2:40:45 
* @version V1.0   
*/
package org.liudan.cms.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.directwebremoting.annotations.DataTransferObject;
@Entity
@Table(name="t_group_channel")
public class GroupChannel {
	private int id;
	private Group group;
	private Channel channel;
	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the group
	 */
	@ManyToOne
	@JoinColumn(name="g_id")
	public Group getGroup() {
		return group;
	}
	/**
	 * @param group the group to set
	 */
	public void setGroup(Group group) {
		this.group = group;
	}
	/**
	 * @return the channel
	 */
	@ManyToOne
	@JoinColumn(name="c_id")
	public Channel getChannel() {
		return channel;
	}
	/**
	 * @param channel the channel to set
	 */
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
}
