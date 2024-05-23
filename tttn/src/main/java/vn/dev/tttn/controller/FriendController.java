package vn.dev.tttn.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.dev.tttn.entity.User;
import vn.dev.tttn.service.FriendService;

@Controller
public class FriendController extends BaseController{
	
	@Autowired
	private FriendService friendService;
	
	@RequestMapping(value ="/user/friend/{userId}", method=RequestMethod.GET)
	public String addFriend(final Model model,
			@PathVariable Integer userId) {
		
		User userLogined = userService.getByUsername(getUsernameLogined());
		
		friendService.saveAddFrient(userLogined, userId);
		
		
		return "redirect:/user/profile/" + userId;
	}
	
	@RequestMapping(value ="/user/unfriend/{userId}", method=RequestMethod.GET)
	public String unFriend(final Model model,
			@PathVariable Integer userId) {
		
		User userLogined = userService.getByUsername(getUsernameLogined());
		
		friendService.saveUnFrient(userLogined, userId);
		
		
		return "redirect:/user/profile/" + userId;
	}
	
	@RequestMapping(value = "/user/friends", method=RequestMethod.GET)
	public String friendLists(final Model model) {

			User user = getUserLogined();
			List<User> myFollows = userService.getMyFollows(user.getId()) == null ? new ArrayList<User>() : userService.getMyFollows(user.getId());
			model.addAttribute("myFollows", myFollows);
			
			List<User> rightContentUsers = userService.getListSqlByUsername(getUsernameLogined());
			model.addAttribute("rightContentUsers", rightContentUsers);
			return "/user/list-friend";
	}
		
}
