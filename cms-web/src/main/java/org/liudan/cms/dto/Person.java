/**   
* @Title: Person.java 
* @Package org.liudan.shiro.realm 
* @Description:  
* @author liudan 
* @date 2015年11月24日 下午5:11:41 
* @version V1.0   
*/
package org.liudan.cms.dto;

public class Person {
	private int id;
	private String name;
	/**
	 * @return the id
	 */
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
	public void sayHello(String name){
		System.out.println("hello:"+name);
	}
}
