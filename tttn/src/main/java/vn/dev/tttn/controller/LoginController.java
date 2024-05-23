package vn.dev.tttn.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.dev.tttn.entity.Role;
import vn.dev.tttn.entity.User;
import vn.dev.tttn.service.UserService;


@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/login")
	public String login() throws IOException{
		return "/login";
	}
	
	
	@GetMapping("/signup")
	public String signup() throws IOException{
		return "/signup";
	}
	
	@GetMapping("/signup-manager")
	public String signupManager() throws IOException{
		return "/signup-manager";
	}
	
	
	// SIGNUP
		@RequestMapping(value="/register", method=RequestMethod.POST)
		public String signup(final Model model, final HttpServletRequest request,
								final HttpServletResponse response)
				throws IOException {
			
			// kiểm tra trùng lặp username 
			String _username = request.getParameter("username");
			List<User> users = userService.findAll();
			for(User user : users) {
				if(user.getUsername().equals(_username)) {
					System.out.println("Trùng tên đăng nhập");
					return "redirect:/signup";
				}
			}
			
			String pw = request.getParameter("password");
			//biểu thức chính quy
			// Tối thiểu tám ký tự, ít nhất một chữ cái, một số và một ký tự đặc biệt
			Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
			if (pattern.matcher(pw).matches() == true) {
				pw = new BCryptPasswordEncoder(4).encode(pw);
			}else {
				return "redirect:/signup";
			}
				
			User newUser = new User();
			newUser.setUsername(_username);
			newUser.setPassword(pw);
			newUser.setEmail(request.getParameter("email"));
			newUser.setCreateDate(new Date());
			newUser.setNickname(request.getParameter("nickname"));
			newUser.setAvatar("avatars/Avatar_default.png");
			newUser.setStatus(true);
			
			Role role = new Role();
			role.setCreateDate(new Date());
			role.setRoleName("USER");
			role.setUser_role(newUser);
			newUser.setRole(role);
			
			System.out.println("\nRole's name: " + role.getRoleName());

			userService.saveOrUpdate(newUser);

//			System.out.println("TAO TAI KHOAN");
//			System.out.println("\nUsername: " + newUser.getUsername());
//			System.out.println("pw: " + pw);
//			System.out.println("\nPassword: " + newUser.getPassword());
//			System.out.println("Email: " + newUser.getEmail());
//			System.out.println("Role: " + newUser.getRole());	
			return "redirect:/login";
		}
		
		
		// SIGNUP MANAGER
		@PreAuthorize("hasAuthority('BOSS')")
		@RequestMapping(value="/register-manager", method=RequestMethod.POST)
		public String signupManager(final Model model, final HttpServletRequest request,
								final HttpServletResponse response)
				throws IOException {
			
			// kiểm tra trùng lặp username 
			String _username = request.getParameter("username");
			List<User> users = userService.findAll();
			for(User user : users) {
				if(user.getUsername().equals(_username)) {
					System.out.println("Trùng tên đăng nhập");
					return "redirect:/signup";
				}
			}
			
			String pw = request.getParameter("password");
			//biểu thức chính quy
			// Tối thiểu tám ký tự, ít nhất một chữ cái, một số và một ký tự đặc biệt
			Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
			if (pattern.matcher(pw).matches() == true) {
				pw = new BCryptPasswordEncoder(4).encode(pw);
			}else {
				return "redirect:/signup-manager";
			}
				
			User newUser = new User();
			newUser.setUsername(_username);
			newUser.setPassword(pw);
			newUser.setEmail(request.getParameter("email"));
			newUser.setCreateDate(new Date());
			newUser.setNickname(request.getParameter("nickname"));
			newUser.setAvatar("avatars/Avatar_default.png");
			newUser.setStatus(true);
			
			Role role = new Role();
			role.setCreateDate(new Date());
			role.setRoleName("MANAGER");
			role.setUser_role(newUser);
			newUser.setRole(role);
			
			System.out.println("\nRole's name: " + role.getRoleName());

			userService.saveOrUpdate(newUser);

			return "redirect:/user/profile";
		}
}
