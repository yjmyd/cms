/**   
* @Title: TopicController.java 
* @Package org.liudan.cms.controller 
* @Description:  
* @author liudan 
* @date 2015年11月11日 下午6:57:12 
* @version V1.0   
*/
package org.liudan.cms.controller;

import org.liudan.cms.auth.AuthClass;
import org.liudan.cms.auth.AuthMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AuthClass("login")
@RequestMapping("/admin/topic")
public class TopicController {

	@RequestMapping("/add")
	@AuthMethod(role = "ROLE_PUBLISH")
	public void add(){
		
	}
}
