/**   
* @Title: GroupController.java 
* @Package org.liudan.cms.controller 
* @Description:  
* @author liudan 
* @date 2015年10月29日 下午5:21:50 
* @version V1.0   
*/
package org.liudan.cms.controller;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.liudan.cms.auth.AuthClass;
import org.liudan.cms.model.ChannelTree;
import org.liudan.cms.model.Group;
import org.liudan.cms.service.IGroupService;
import org.liudan.cms.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/group")
@AuthClass
public class GroupController {
	
	@Inject
	private IGroupService groupService;
	@Inject
	private IUserService userService;
	
	
	@RequestMapping("groups")
	public String list(Model model ){
		model.addAttribute("datas",groupService.findGroup());
		return "group/list";
	}
	
	@RequestMapping(value="add",method=RequestMethod.GET)
	public String add(Model model){
		model.addAttribute(new Group());
		return "group/add";
	}
	
	@RequestMapping(value="add",method=RequestMethod.POST)
	public String add(@Valid Group group,BindingResult br){
		if(br.hasErrors()){
			return "group/add";
		}
		groupService.add(group);
		return "redirect:/admin/group/groups";
	}
	
	@RequestMapping(value="update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable Integer id,Model model){
		model.addAttribute(groupService.load(id));
		return "group/add";
	}
	
	@RequestMapping(value="update/{id}",method=RequestMethod.POST)
	public String update(@PathVariable Integer id,@Valid Group group,BindingResult br){
		if(br.hasErrors()){
			return "group/update";
		}
		Group gr = groupService.load(id);
		gr.setDescr(group.getDescr());
		gr.setName(group.getName());
		groupService.update(gr);
		return "redirect:/admin/group/groups";
	}
	@RequestMapping("delete/{id}")
	public String delete(@PathVariable Integer id){
		groupService.delete(id);
		return "redirect:/admin/group/groups";
	}
	@RequestMapping("{id}")
	public String show(@PathVariable Integer id,Model model){
		model.addAttribute(groupService.load(id));
		model.addAttribute("us", userService.listGroupUsers(id));
		return "group/show";
	}
	
	@RequestMapping("clearUsers/{id}")
	public String cleanUsers(@PathVariable Integer id){
		groupService.deleteGroupUsers(id);
		return "redirect:/admin/group/groups";
	}
	
	@RequestMapping("listChannels/{gid}")
	public String listChannels(@PathVariable Integer gid,Model model){
		model.addAttribute(groupService.load(gid));
		return "/group/listChannel";
	}
	@RequestMapping("/groupTree/{gid}")
	public @ResponseBody List<ChannelTree> groupTree(@PathVariable Integer gid) {
		return groupService.generateGroupChannelTree(gid);
	}
	@RequestMapping("/setChannels/{gid}")
	public String setChannels(@PathVariable int gid,Model model) {
		model.addAttribute(groupService.load(gid));
		model.addAttribute("cids",groupService.listGroupChannelIds(gid));
		return "/group/setChannel";
	}
}
