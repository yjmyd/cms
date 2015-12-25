/**   
* @Title: AuthMethod.java 
* @Package org.liudan.cms.auth 
* @Description:  
* @author liudan 
* @date 2015年11月11日 下午6:48:15 
* @version V1.0   
*/
package org.liudan.cms.auth;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * 来确定方法有哪些角色可以访问。
 * 属性有一个role：如果role的值为base标示这个方法可以被所有登陆用户所访问。
 * 如果为ROLE_PUBLISH  标示只能被文章发布人员访问。
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthMethod {
	public String role() default "base";
}
