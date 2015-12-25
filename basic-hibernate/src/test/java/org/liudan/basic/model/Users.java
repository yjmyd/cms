/**   
* @Title: Users.java 
* @Package org.liudan.basic.model 
* @Description:  
* @author liudan 
* @date 2015年10月22日 上午11:59:36 
* @version V1.0   
*/
package org.liudan.basic.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_user")
public class Users {
	private int id;
	private String username;
	/**
	 * 
	 */
	public Users() {
	}
	/**
	 * @param id
	 * @param username
	 */
	public Users(int id, String username) {
		super();
		this.id = id;
		this.username = username;
	}
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
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
}
