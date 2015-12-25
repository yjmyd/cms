/**   
* @Title: GroupController.java 
* @Package org.liudan.cms.controller 
* @Description:  
* @author liudan 
* @date 2015年10月29日 下午5:21:50 
* @version V1.0   
*/
package org.liudan.cms.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import org.liudan.basic.util.EnumUtils;
import org.liudan.cms.auth.AuthClass;
import org.liudan.cms.model.Role;
import org.liudan.cms.model.RoleType;
import org.liudan.cms.service.IRoleService;
import org.liudan.cms.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/role")
@AuthClass
public class RoleController {
	
	@Inject
	private IRoleService roleService;
	@Inject
	private IUserService userService;
	
	
	@RequestMapping("roles")
	public String list(Model model ){
		model.addAttribute("roles",roleService.listRole());
		return "role/list";
	}
	
	@RequestMapping(value="add",method=RequestMethod.GET)
	public String add(Model model){
		model.addAttribute(new Role());
		model.addAttribute("types", EnumUtils.enum2Name(RoleType.class));
		return "role/add";
	}
	
	@RequestMapping(value="add",method=RequestMethod.POST)
	public String add(@Valid Role role,BindingResult br){
		if(br.hasErrors()){
			return "role/add";
		}
		roleService.add(role);
		return "redirect:/admin/role/roles";
	}
	
	@RequestMapping(value="update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable Integer id,Model model){
		model.addAttribute(roleService.load(id));
		model.addAttribute("types", EnumUtils.enum2Name(RoleType.class));
		return "role/add";
	}
	
	@RequestMapping(value="update/{id}",method=RequestMethod.POST)
	public String update(@PathVariable Integer id,@Valid Role role,BindingResult br){
		if(br.hasErrors()){
			return "group/update";
		}
		Role rl = roleService.load(id);
		rl.setName(role.getName());
		rl.setRoleType(role.getRoleType());
		roleService.update(rl);
		return "redirect:/admin/role/roles";
	}
	@RequestMapping("delete/{id}")
	public String delete(@PathVariable Integer id){
		roleService.delete(id);
		return "redirect:/admin/role/roles";
	}
	@RequestMapping("{id}")
	public String show(@PathVariable Integer id,Model model){
		model.addAttribute(roleService.load(id));
		model.addAttribute("us", userService.listRoleUsers(id));
		return "role/show";
	}
	
	@RequestMapping("clearUsers/{id}")
	public String cleanUsers(@PathVariable Integer id){
		roleService.deleteRoleUsers(id);
		return "redirect:/admin/role/roles";
	}
}
