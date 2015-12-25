/**   
* @Title: Channel.java 
* @Package org.liudan.cms.model 
* @Description:  
* @author liudan 
* @date 2015年10月30日 下午4:34:32 
* @version V1.0   
*/
package org.liudan.cms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_channel")
public class Channel {
	public static final String ROOT_NAME = "网站系统栏目";
	public static final int ROOT_ID = 0;
	
	private int id;
	//名称
	private String name;
	//0否1是
	private int customLink;
	//url
	private String customLinkUrl;
	//枚举类型
	private ChannelType type;
	//是否首页显示0否1是
	private int isIndex;
	//是否顶部导航栏0否1是
	private int isTopNav;
	//是否推荐栏目0否1是
	private int recommend;
	//状态
	private int status;
	//排序
	private int orders;
	
	private Channel parent;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the customLink
	 */
	@Column(name="custom_link")
	public int getCustomLink() {
		return customLink;
	}
	/**
	 * @param customLink the customLink to set
	 */
	public void setCustomLink(int customLink) {
		this.customLink = customLink;
	}
	/**
	 * @return the customLinkUrl
	 */
	@Column(name="custom_link_url")
	public String getCustomLinkUrl() {
		return customLinkUrl;
	}
	/**
	 * @param customLinkUrl the customLinkUrl to set
	 */
	public void setCustomLinkUrl(String customLinkUrl) {
		this.customLinkUrl = customLinkUrl;
	}
	/**
	 * @return the type
	 */
	public ChannelType getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(ChannelType type) {
		this.type = type;
	}
	/**
	 * @return the isIndex
	 */
	@Column(name = "is_index")
	public int getIsIndex() {
		return isIndex;
	}
	/**
	 * @param isIndex the isIndex to set
	 */
	public void setIsIndex(int isIndex) {
		this.isIndex = isIndex;
	}
	/**
	 * @return the isTopNav
	 */
	@Column(name="is_top_nav")
	public int getIsTopNav() {
		return isTopNav;
	}
	/**
	 * @param isTopNav the isTopNav to set
	 */
	public void setIsTopNav(int isTopNav) {
		this.isTopNav = isTopNav;
	}
	/**
	 * @return the recommend
	 */
	public int getRecommend() {
		return recommend;
	}
	/**
	 * @param recommend the recommend to set
	 */
	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return the orders
	 */
	public int getOrders() {
		return orders;
	}
	/**
	 * @param orders the orders to set
	 */
	public void setOrders(int orders) {
		this.orders = orders;
	}
	/**
	 * @return the parent
	 */
	@ManyToOne
	@JoinColumn(name="pid")
	public Channel getParent() {
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(Channel parent) {
		this.parent = parent;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Channel [id=" + id + ", name=" + name + ", customLink="
				+ customLink + ", customLinkUrl=" + customLinkUrl + ", type="
				+ type + ", isIndex=" + isIndex + ", isTopNav=" + isTopNav
				+ ", recommend=" + recommend + ", status=" + status
				+ ", orders=" + orders + ", parent=" + parent + "]";
	}
	
	
	
}
