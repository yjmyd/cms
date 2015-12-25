/**   
* @Title: LoginController.java 
* @Package org.liudan.cms.controller 
* @Description:  
* @author liudan 
* @date 2015年11月11日 下午5:54:58 
* @version V1.0   
*/
package org.liudan.cms.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.liudan.basic.util.Captcha;
import org.liudan.cms.model.Role;
import org.liudan.cms.model.RoleType;
import org.liudan.cms.model.User;
import org.liudan.cms.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	@Inject
	private IUserService userService;
	
	@RequestMapping(value="/login",method = RequestMethod.GET)
	public String login(Model model){
		model.addAttribute("username", "");
		model.addAttribute("password", "");
		return "/admin/login";
	}
	@RequestMapping(value="/login",method = RequestMethod.POST)
	public String login(String username,String password,String checkcode,HttpSession session,Model model){
		String cc = (String)session.getAttribute("cc");
		if(!checkcode.equals(cc)){
			model.addAttribute("error", "验证码错误");
			return "/admin/login";
		}
		User u = userService.login(username, password);
		List<Role> rs = userService.listUserRoles(u.getId());
		boolean isAdmin = isAdmin(rs);
		session.setAttribute("isAdmin",isAdmin);
		System.out.println(u.toString());
		session.setAttribute("loginUser", u);
		if(!isAdmin){
			session.setAttribute("allActions", getAllActions(rs, session));
		}
		session.removeAttribute("cc");
		return "redirect:/admin";
	}
	
	public boolean isAdmin(List<Role> rs){
		for (Role role : rs) {
			if(role.getRoleType()==RoleType.ROLE_ADMIN) return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	private Set<String> getAllActions(List<Role> rs,HttpSession session){
		Set<String> actions = new HashSet<String>();
		Map<String,Set<String>> allAuths = (Map<String,Set<String>>)session.getServletContext().getAttribute("allAuths");
		actions.addAll(allAuths.get("base"));
		for (Role r : rs) {
			if(r.getRoleType()==RoleType.ROLE_ADMIN) continue;
			actions.addAll(allAuths.get(r.getRoleType().name()));
		}
		return actions;
	}
	
	
	@RequestMapping("/drawCheckCode")
	public void drawCheckCode(HttpServletResponse reps,Integer width,Integer height,HttpSession session) throws IOException{
		reps.setContentType("image/jpg");
		width=200;
		height =30;
		Captcha chap = Captcha.getInstance();
		chap.set(width, height);
		String checkCode = chap.generateCheckcode();
		session.setAttribute("cc", checkCode);
		OutputStream os = reps.getOutputStream();
		ImageIO.write(chap.generateCheckImg(checkCode), "jpg", os);
	}
	
}
