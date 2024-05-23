package vn.dev.tttn.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vn.dev.tttn.entity.Spost;
import vn.dev.tttn.entity.User;
import vn.dev.tttn.service.FriendService;
import vn.dev.tttn.service.SpostService;

@Controller
public class ProfileController extends BaseController{
	
	@Autowired
	private SpostService spostService;
	
	@Autowired
	private FriendService friendService;
	
//	@RequestMapping(value="/user/profile", method=RequestMethod.GET)
//	public String profile(final Model model, final HttpServletRequest request) throws IOException{
//		
//		User userFocus = userService.getByUsername(getUsernameLogined());
//		model.addAttribute("userFocus", userFocus);
//		List<Spost> mainSposts = spostService.findInProfile(userFocus.getId());
//
//		model.addAttribute("mainSposts", mainSposts);
//		
//		return "/user/profile";
//	}
//	
//	@RequestMapping(value="/user/profile/{userId}", method=RequestMethod.GET)
//	public String userProfile(final Model model, final HttpServletRequest request,
//								@PathVariable("userId") Integer userId) throws IOException{
//		
//		User userFocus = userService.getById(userId);
//		model.addAttribute("userFocus", userFocus);
//		List<Spost> mainSposts = spostService.findInProfile(userId);
//
//		model.addAttribute("mainSposts", mainSposts);
//		
//		return "/user/profile";
//	}
	
	@GetMapping(value = {"/user/profile", "/user/profile/{userId}"})
	public String linkProfile(final Model model, final HttpServletRequest request,
								@PathVariable(required = false) Integer userId) throws IOException{
		if(userId ==null) {
			// người dùng đc hiển thị trong trang profile
			User userFocus = userService.getByUsername(getUsernameLogined());
			model.addAttribute("userFocus", userFocus);
			List<Spost> mainSposts = spostService.findInProfile(userFocus.getId());
			session.setAttribute("userFocusId", userFocus.getId());
			System.out.println("userFocusId == " + userFocus.getId());
			model.addAttribute("mainSposts", mainSposts);
			model.addAttribute("userLogined", userFocus);
		}else {
			User userFocus = userService.getById(userId);
			User userLogined = userService.getByUsername(getUsernameLogined());
			model.addAttribute("userFocus", userFocus);
			List<Spost> mainSposts = spostService.findInOtherProfile(userId); // không xem được private
			session.setAttribute("userFocusId", userFocus.getId());
			System.out.println("userFocusId == " + userFocus.getId());
			model.addAttribute("mainSposts", mainSposts);
			model.addAttribute("userLogined", userLogined);
			model.addAttribute("isFriend", friendService.checkIsFriend(userLogined, userId));
		}
		return "/user/profile";
	}
	
}
