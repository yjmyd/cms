/**   
* @Title: TestSpring.java 
* @Package org.liudan.cms.test 
* @Description:  
* @author liudan 
* @date 2015年11月24日 下午5:14:44 
* @version V1.0   
*/
package org.liudan.cms.test;

import org.junit.Test;
import org.liudan.cms.dto.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring {
	
	
	@Test
	public void testSpring(){
		ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
		Person p = context.getBean("person",Person.class);
		p.sayHello("liudan");
	}
}
