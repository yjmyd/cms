/**   
* @Title: AuthClass.java 
* @Package org.liudan.cms.auth 
* @Description:  
* @author liudan 
* @date 2015年11月11日 下午5:50:41 
* @version V1.0   
*/
package org.liudan.cms.auth;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface AuthClass {
	/**
	 * 如果value为admin就标示只有超级管理员可以访问。
	 * 如果value为login标示这个类中的方法，某些可能为相应角色可以访问。
	 * @return
	 */
	public String value() default "admin";
}
