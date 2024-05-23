package vn.dev.tttn.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.dev.tttn.entity.Spost;
import vn.dev.tttn.entity.User;
import vn.dev.tttn.service.SpostService;


@Controller
public class HomeController extends BaseController{
	
	@Autowired
	private SpostService spostService; 
	
	@RequestMapping(value = "/user/home", method = RequestMethod.GET)
	public String getHome(final Model model, 
			final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {
		
		User userLogined = userService.getByUsername(getUsernameLogined());
		List<User> rightContentUsers = userService.getListSqlByUsername(getUsernameLogined());
		List<Spost> mainSposts = spostService.newfeeds();
		
		Spost newSpost = new Spost();// tên phải trùng vói modelAttibute trong sf:form
		model.addAttribute("newSpost", newSpost);

		model.addAttribute("mainSposts", mainSposts);
		model.addAttribute("userLogined", userLogined);
		model.addAttribute("rightContentUsers", rightContentUsers);
		model.addAttribute("userService", userService);
		return "/user/home";
	}
}
