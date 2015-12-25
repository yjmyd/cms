/**   
* @Title: ChannelType.java 
* @Package org.liudan.cms.model 
* @Description:  
* @author liudan 
* @date 2015年10月30日 下午4:35:11 
* @version V1.0   
*/
package org.liudan.cms.model;

public enum ChannelType {
	NAV_CHANNEL("导航栏目"),TOPIC_LIST("文章列表栏目"),
		TOPIC_CONTENT("文章内容栏目"),TOPIC_IMG("图片列表栏目");
	private String name;
	/**
	 * 
	 */
	private ChannelType(String name) {
		this.name = name;
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
	
}
