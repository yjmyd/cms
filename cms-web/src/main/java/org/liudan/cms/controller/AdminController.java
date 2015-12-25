/**   
* @Title: AdminController.java 
* @Package org.liudan.cms.controller 
* @Description:  
* @author liudan 
* @date 2015年10月27日 下午12:04:33 
* @version V1.0   
*/
package org.liudan.cms.controller;

import javax.servlet.http.HttpSession;

import org.liudan.cms.auth.AuthClass;
import org.liudan.cms.auth.AuthMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AuthClass("login")
public class AdminController {
	
	
	@RequestMapping("/admin")
	public String index(){
		return "admin/index";
	}

	@AuthMethod
	@RequestMapping("/admin/logout")
	public String logout(HttpSession session){
		session.invalidate();
		return "redirect:/login";
	}

	
}
