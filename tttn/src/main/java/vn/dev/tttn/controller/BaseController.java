package vn.dev.tttn.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;

import vn.dev.tttn.entity.User;
import vn.dev.tttn.service.UserService;

public class BaseController {
	
	@Autowired
	protected UserService userService;
	
	@Autowired
	protected HttpSession session;
	
	
	// Lay thong tin cua user dang nhap
	@ModelAttribute("userLogined")
	public User getUserLogined() {
		// SecurityContextHolder class quan ly thong tin user
		User user = userService.getByUsername(getUsernameLogined());
		return user;
	}

	@ModelAttribute("usernameLogined")
	public String getUsernameLogined() {
		// SecurityContextHolder class quan ly thong tin user
		Object userLogined = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (userLogined instanceof UserDetails) {

			String string = ((UserDetails)userLogined).getUsername();
//			System.out.println("in BaseController getUsernameLogined function tttn: " +string);
			return string;
		}
		System.out.println("in BaseController getUsernameLogined function tttn: NULL");
		return new String();
	}
	
	@ModelAttribute("isLogined")
	public boolean isLogined() {
		// SecurityContextHolder class quan ly thong tin user
		Object userLogined = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userLogined != null && userLogined instanceof UserDetails) {
			return true;
		}
		return false;
	}
}
