/**   
* @Title: ChannelTree.java 
* @Package org.liudan.cms.model 
* @Description:  
* @author liudan 
* @date 2015年11月3日 上午10:31:22 
* @version V1.0   
*/
package org.liudan.cms.model;

public class ChannelTree {
	private Integer id;
	private String name;
	private Integer pid;
	
	/**
	 * 
	 */
	public ChannelTree() {
		super();
	}
	
	/**
	 * @param id
	 * @param name
	 * @param pid
	 */
	public ChannelTree(Integer id, String name, Integer pid) {
		super();
		this.id = id;
		this.name = name;
		this.pid = pid;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
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
	 * @return the pid
	 */
	public Integer getPid() {
		return pid;
	}
	/**
	 * @param pid the pid to set
	 */
	public void setPid(Integer pid) {
		this.pid = pid;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ChannelTree [id=" + id + ", name=" + name + ", pid=" + pid
				+ "]";
	}
	
	
}
