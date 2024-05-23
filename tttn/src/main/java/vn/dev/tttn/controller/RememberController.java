package vn.dev.tttn.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.dev.tttn.entity.Spost;
import vn.dev.tttn.entity.User;
import vn.dev.tttn.service.RememberService;
import vn.dev.tttn.service.SpostService;

@Controller
public class RememberController extends BaseController{

	@Autowired
	private SpostService spostService; 
	
	@Autowired
	private RememberService rememberService;
	
	@RequestMapping(value = "/user/remembers", method = RequestMethod.GET)
	public String getRemembers(final Model model, 
			final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {
		
		User userLogined = userService.getByUsername(getUsernameLogined());
		List<User> rightContentUsers = userService.getListSqlByUsername(getUsernameLogined());
		List<Spost> mainSposts = spostService.getRememberSposts(userLogined.getId());
		

		model.addAttribute("mainSposts", mainSposts);
		model.addAttribute("userLogined", userLogined);
		model.addAttribute("rightContentUsers", rightContentUsers);
		model.addAttribute("userService", userService);
		return "/user/remembers";
	}
	
	@RequestMapping(value = "/user/unremember/{spostId}", method = RequestMethod.GET)
	public String removeRemembers(final Model model, 
			@PathVariable("spostId") Integer spostId) throws IOException {
			rememberService.getRemember(getUserLogined().getId(), spostId);
			rememberService.delete(rememberService.getRemember(getUserLogined().getId(), spostId));
		return "redirect:/user/remembers";
	}
}
