/**   
* @Title: UserController.java 
* @Package org.liudan.cms.controller 
* @Description:  
* @author liudan 
* @date 2015年10月27日 下午4:02:23 
* @version V1.0   
*/
package org.liudan.cms.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.jboss.logging.Param;
import org.liudan.cms.auth.AuthMethod;
import org.liudan.cms.dto.UserDto;
import org.liudan.cms.model.ChannelTree;
import org.liudan.cms.model.Role;
import org.liudan.cms.model.RoleType;
import org.liudan.cms.model.User;
import org.liudan.cms.service.IChannelService;
import org.liudan.cms.service.IGroupService;
import org.liudan.cms.service.IRoleService;
import org.liudan.cms.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/user")
public class UserController {

	@Inject
	private IUserService userService;
	@Inject
	private IRoleService roleService;
	@Inject
	private IGroupService groupService;
	@Inject
	private IChannelService channelService;
	
	@RequestMapping("users")
	public String list(Model model){
		model.addAttribute("datas", userService.findUser());
		return "user/list";
	}
	public void initAddUser(Model model){
		model.addAttribute("groups",groupService.listGroup());
		model.addAttribute("roles",roleService.listRole());
	}
	@RequestMapping(value="add",method=RequestMethod.GET)
	public String add(Model model){
		model.addAttribute("userDto",new UserDto());
		initAddUser(model);
		return "user/add";
	}
	@RequestMapping(value="add",method=RequestMethod.POST)
	public String add(@Valid UserDto userDto,BindingResult br,Model model) throws NoSuchAlgorithmException{
		if(br.hasErrors()){
			initAddUser(model);
		}
		userService.add(userDto.getUser(), userDto.getRoleIds(), userDto.getGroupIds());
		return "redirect:/admin/user/users";
	}
	@RequestMapping("delete/{id}")
	public String delete(@PathVariable Integer id){
		userService.delete(id);
		return "redirect:/admin/user/users";
	}
	@RequestMapping("update/{id}")
	public String update(@PathVariable Integer id,Model model){
		User u = userService.load(id);
		List<Integer> rlist = userService.listUserRoleIds(id);
		List<Integer> glist = userService.listUserGroupIds(id);
		model.addAttribute("userDto",new UserDto(u,rlist,glist));
		initAddUser(model);
		return "user/update";
	}
	
	@RequestMapping(value = "update/{id}",method = RequestMethod.POST)
	public String update(@PathVariable Integer id,@Valid UserDto userDto,BindingResult br,Model model){
		if(br.hasErrors()){
			initAddUser(model);
			return "user/update";
		}
		User u = userService.load(id);
		u.setNickname(userDto.getNickname());
		u.setPhone(userDto.getPhone());
		u.setEmail(userDto.getEmail());
		u.setStatus(userDto.getStatus());
		userService.update(u, userDto.getRoleIds(), userDto.getGroupIds());
		return "redirect:/admin/user/users";
	}
	@RequestMapping("updateStatus/{id}")
	public String updateStatus(@PathVariable Integer id){
		userService.updateStatus(id);
		return "redirect:/admin/user/users";
	}
	@RequestMapping("{id}")
	public String show(@PathVariable Integer id,Model model){
		model.addAttribute(userService.load(id));
		model.addAttribute("gs", userService.listUserGroups(id));
		model.addAttribute("rs", userService.listUserRoles(id));
		return "/user/show";
	}
	
	@RequestMapping("/listChannels/{uid}")
	public String listChannels(@PathVariable int uid,Model model) {
		model.addAttribute(userService.load(uid));
		List<Role> rs = userService.listUserRoles(uid);
		for(Role r:rs) {
			if(r.getRoleType()==RoleType.ROLE_ADMIN) {
				model.addAttribute("isAdmin",1);
			}
		}
		return "/user/listChannel";
	}
	
	@RequestMapping("/userTree/{uid}")
	public @ResponseBody List<ChannelTree> groupTree(@PathVariable Integer uid,@Param Integer isAdmin) {
		if(isAdmin!=null&&isAdmin==1) {
			return channelService.generateTree();
		}else{
			return groupService.generateUserChannelTree(uid);
		}
	}
	
	@RequestMapping("/showSelf")
	@AuthMethod
	public String showSelf(Model model,HttpSession session){
		User user = (User)session.getAttribute("loginUser");
		model.addAttribute(user);
		model.addAttribute("gs", userService.listUserGroups(user.getId()));
		model.addAttribute("rs", userService.listUserRoles(user.getId()));
		return "user/show";
	}
	
	
	@RequestMapping("/updateSelf")
	public String updateSelf(Model model,HttpSession session){
		User user = (User)session.getAttribute("loginUser");
		model.addAttribute(new UserDto(user));
		return "/user/updateSelf";
	}
	
	@RequestMapping(value = "/updateSelf",method = RequestMethod.POST)
	@AuthMethod
	public String updateSelf(@Valid UserDto userDto,BindingResult br,Model model,HttpSession session){
		if(br.hasErrors()){
			return "user/updateSelf";
		}
		User u = userService.load(userDto.getId());
		u.setNickname(userDto.getNickname());
		u.setPhone(userDto.getPhone());
		u.setEmail(userDto.getEmail());
		userService.update(u);
		session.setAttribute("loginUser", u);
		return "redirect:/admin/user/showSelf";
	}
	
	@RequestMapping("/updatePwd")
	@AuthMethod
	public String updatePwd(Model model,HttpSession session){
		User user = (User)session.getAttribute("loginUser");
		model.addAttribute(user);
		return "/user/updatePwd";
	}
	
	@RequestMapping(value = "/updatePwd",method = RequestMethod.POST)
	@AuthMethod
	public String updatePwd(int id,String oldPwd,String password) throws NoSuchAlgorithmException{
		userService.updatePwd(id, oldPwd, password);
		return "redirect:/admin/user/showSelf";
	}
	
	
	
	
	
	
	
	
}
