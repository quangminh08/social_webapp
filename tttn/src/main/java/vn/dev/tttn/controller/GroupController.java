package vn.dev.tttn.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import vn.dev.tttn.entity.Group;
import vn.dev.tttn.entity.Spost;
import vn.dev.tttn.entity.User;
import vn.dev.tttn.service.GroupService;
import vn.dev.tttn.service.SpostService;

@Controller
public class GroupController extends BaseController{
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private SpostService spostService;
	
	// ok
	@RequestMapping(value = {"/user/group", "/user/group/{groupId}"}, method=RequestMethod.GET)
	public String renderGroup(final Model model,
			@PathVariable(required = false) Integer groupId) {
		if(groupId == null) {
			List<Group> myGroups = groupService.getMyGroup(getUserLogined()) == null ? new ArrayList<Group>() : groupService.getMyGroup(getUserLogined());
			model.addAttribute("myGroups", myGroups);
			
			List<Group> allGroups = groupService.findAll();
			model.addAttribute("allGroups", allGroups);
			
			Group newGroup = new Group();
			model.addAttribute("newGroup", newGroup);
			
			List<User> rightContentUsers = userService.getListSqlByUsername(getUsernameLogined());
			model.addAttribute("rightContentUsers", rightContentUsers);
			return "/user/list-group";
		}else {
			Group groupFocus = groupService.getById(groupId);
			model.addAttribute("groupFocus", groupFocus);
			session.setAttribute("groupFocusId", groupFocus.getId());
			
			List<Group> rightContentGroups = groupService.findAll(); // cần đổi thành getMyGroups
			model.addAttribute("rightContentGroups", rightContentGroups);
			
			List<Spost> mainSposts = spostService.findInGroup(groupId);
			model.addAttribute("mainSposts", mainSposts);
			
			User userLogined = userService.getByUsername(getUsernameLogined());
			model.addAttribute("isInGroup", groupService.checkInGroup(userLogined, groupId));
			System.out.println("user id addGroup = " + userLogined.getId());
			System.out.println("group id addGroup = " + groupId);
			return "/user/group";
		}
	}
	
	//ok
	@RequestMapping(value = "/user/group", method = RequestMethod.POST)
	public String addGroup(@ModelAttribute("newGroup") Group newGroup,
			@RequestParam("groupAvatar") MultipartFile groupAvatar) {
			
		if(newGroup.getId() == null){
			groupService.saveGroup(newGroup, groupAvatar, getUserLogined());
		}
		return "redirect:/user/group";
	}
	
	// ok
	@RequestMapping(value = "/user/group/join/{groupId}", method=RequestMethod.GET)
	public String userJoinGroup(@PathVariable("groupId") Integer groupId) throws IOException{
		
			Group groupFocus = groupService.getById(groupId);
			User userLogined = getUserLogined();
			userLogined.addGroup(groupFocus); // tham gia 
			userService.saveOrUpdate(userLogined);
			return "redirect:/user/group/" + groupId;
	}
	
	// ok
	@RequestMapping(value = "/user/group/leave/{groupId}", method=RequestMethod.GET)
	public String leaveGroup(@PathVariable("groupId") Integer groupId) {
		
		Group groupFocus = groupService.getById(groupId);
		User userLogined = getUserLogined();
		userLogined.deleteGroups(groupFocus); // rời group
		userService.saveOrUpdate(userLogined);
		return "redirect:/user/group";
	}
}
