/**   
* @Title: ChannelController.java 
* @Package org.liudan.cms.controller 
* @Description:  
* @author liudan 
* @date 2015年11月2日 下午5:45:54 
* @version V1.0   
*/
package org.liudan.cms.controller;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.jboss.logging.Param;
import org.liudan.basic.util.EnumUtils;
import org.liudan.cms.auth.AuthClass;
import org.liudan.cms.dto.AjaxObj;
import org.liudan.cms.model.Channel;
import org.liudan.cms.model.ChannelTree;
import org.liudan.cms.model.ChannelType;
import org.liudan.cms.service.IChannelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@RequestMapping("/admin/channel")
@Controller
@AuthClass
public class ChannelController {
	@Inject
	private IChannelService channelService;
	
	
	@RequestMapping("/channels")
	public String list(Model model){
		return "channel/list";
	}
	
	@RequestMapping("/treeAll")
	public @ResponseBody List<ChannelTree> treeAll(Model model){
		return channelService.generateTree();
	}
	
	@RequestMapping("/channels/{pid}")
	public String listChild(@PathVariable Integer pid,@Param Integer refresh,Model model){
		Channel channel = null;
		if(refresh==null) {
			model.addAttribute("refresh",0);
		} else {
			model.addAttribute("refresh",1);
		}
		if(pid==null || pid==0){
			channel = new Channel();
			channel.setName(Channel.ROOT_NAME);
			channel.setId(Channel.ROOT_ID);
		}else{
			channel = channelService.load(pid);
		}
		model.addAttribute("pc", channel);
		model.addAttribute("channels", channelService.listByParent(pid));
		return "/channel/list_child";
	}
	public void initAdd(Model model,Integer pid){
		Channel channel = null;
		if(pid==null || pid==0){
			channel = new Channel();
			channel.setName(Channel.ROOT_NAME);
			channel.setId(Channel.ROOT_ID);
		}else{
			channel = channelService.load(pid);
		}
		model.addAttribute("pc", channel);
		model.addAttribute("types", EnumUtils.enumProp2NameMap(ChannelType.class, "name"));
	}
	@RequestMapping(value = "/add/{pid}",method = RequestMethod.GET)
	public String add(@PathVariable Integer pid,Model model){
		initAdd(model,pid);
		model.addAttribute(new Channel());
		return "/channel/add";
	}
	
	@RequestMapping(value = "/add/{pid}",method = RequestMethod.POST)
	public String add(@PathVariable Integer pid,Channel channel,BindingResult br,Model model){
		if(br.hasErrors()){
			initAdd(model, pid);
			return "channel/add";
		}
		channelService.add(channel, pid);
		return "redirect:/admin/channel/channels/"+pid+"?refresh=1";
	}
	
	@RequestMapping("/delete/{pid}/{id}")
	public String delete(@PathVariable Integer pid,@PathVariable Integer id,Model model){
		channelService.delete(id);
		System.out.println("删除的："+id);
		return "redirect:/admin/channel/channels/"+pid+"?refresh=1";
	}
	
	@RequestMapping(value = "/update/{id}",method = RequestMethod.GET)
	public String update(@PathVariable Integer id,Model model){
		Channel c = channelService.load(id);
		System.out.println(c.getName());
		model.addAttribute("channel", c);
		Channel pc = null;
		if(c.getParent()==null) {
			pc = new Channel();
			pc.setId(Channel.ROOT_ID);
			pc.setName(Channel.ROOT_NAME);
		} else {
			pc = c.getParent();
		}
		model.addAttribute("pc",pc);
		model.addAttribute("types", EnumUtils.enumProp2NameMap(ChannelType.class, "name"));
		return "/channel/update";
	}
	
	@RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
	public String update(@PathVariable Integer id,@Valid Channel channel,BindingResult br,Model model){
		if(br.hasErrors()){
			model.addAttribute("types", EnumUtils.enumProp2NameMap(ChannelType.class, "name"));
			return "channel/update";
		}
		Channel tc = channelService.load(id);
		int pid = 0;
		if(tc.getParent()!=null) pid = tc.getParent().getId();
		tc.setCustomLink(channel.getCustomLink());
		tc.setCustomLinkUrl(channel.getCustomLinkUrl());
		tc.setIsIndex(channel.getIsIndex());
		tc.setIsTopNav(channel.getIsTopNav());
		tc.setName(channel.getName());
		tc.setRecommend(channel.getRecommend());
		tc.setStatus(channel.getStatus());
		tc.setType(channel.getType());
		channelService.update(tc);
		return "redirect:/admin/channel/channels/"+pid+"?refresh=1";
	}
	
	@RequestMapping("/channels/updateSort")
	public @ResponseBody AjaxObj updateSort(@Param Integer[] ids) {
		try {
			channelService.updateSort(ids);
		} catch (Exception e) {
			return new AjaxObj(0,e.getMessage());
		}
		return new AjaxObj(1);
	}
	
	
	
	
	
	

}
