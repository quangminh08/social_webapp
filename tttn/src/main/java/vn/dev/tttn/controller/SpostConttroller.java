package vn.dev.tttn.controller;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import vn.dev.tttn.entity.Comment;
import vn.dev.tttn.entity.Group;
import vn.dev.tttn.entity.Spost;
import vn.dev.tttn.entity.User;
import vn.dev.tttn.service.CommentService;
import vn.dev.tttn.service.GroupService;
import vn.dev.tttn.service.RememberService;
import vn.dev.tttn.service.SpostService;

@Controller
public class SpostConttroller extends BaseController{
	
	@Autowired
	private SpostService spostService;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private RememberService rememberService;
	
	@Autowired
	private CommentService commentService;
	
	@RequestMapping(value = "/user/post", method = RequestMethod.POST)
	public String saveAddProduct (@ModelAttribute("newSpost") Spost newSpost, // được khởi tạo từ newSpost bên homeController
									BindingResult bindingResult,
									@RequestParam("protect") String protect,
									@RequestParam("picture") MultipartFile picture
								) throws IOException{
		
		if(newSpost.getId() == null) {
			User userLogined = userService.getByUsername(getUsernameLogined());
			newSpost.setProtect(protect);
			spostService.saveSpost(newSpost, picture, userLogined);
		}
		return "redirect:/user/profile";
	}
	
	@RequestMapping(value = "/user/group/post", method = RequestMethod.POST)
	public String saveAddProductInGroup (@ModelAttribute("newSpost") Spost newSpost,
									BindingResult bindingResult,
									@RequestParam("protect") String protect,
									@RequestParam("picture") MultipartFile picture,
									@RequestParam(required = false, value="groupId") Integer groupId
								) throws IOException{
		
		if(groupId != null) {
			Group _group = groupService.getById(groupId);
			newSpost.setGroup(_group);
		}
		
		if(newSpost.getId() == null) {
			User userLogined = userService.getByUsername(getUsernameLogined());
			newSpost.setProtect(protect);
			spostService.saveSpost(newSpost, picture, userLogined);
		}
		return "redirect:/user/group/" + groupId;
	}
	
	
	// like hoac bo bo like
	@RequestMapping(value = "/user/home/like/{spostId}", method = RequestMethod.GET)
	public String likeInHomeStatus(@PathVariable("spostId") Integer spostId) {
		
		Spost spost = spostService.getById(spostId);
		User userLogined = userService.getByUsername(getUsernameLogined());
		spostService.changeLikeStatus(spost, userLogined.getId());
		return "redirect:/user/home";
	}
	
	@RequestMapping(value = "/user/profile/like/{spostId}", method = RequestMethod.GET)
	public String likeInProfileStatus(@PathVariable("spostId") Integer spostId) {
		Integer userId = (Integer) session.getAttribute("userFocusId");
		Spost spost = spostService.getById(spostId);
		User userLogined = userService.getByUsername(getUsernameLogined());
		spostService.changeLikeStatus(spost, userLogined.getId());
		System.out.println("userFocusId == " + userId);
		return "redirect:/user/profile/"+userId;
	}
	
	@RequestMapping(value = "/user/group/like/{spostId}", method = RequestMethod.GET)
	public String likeInGroupStatus(@PathVariable("spostId") Integer spostId) {
		Integer groupFocusId = (Integer) session.getAttribute("groupFocusId");
		Spost spost = spostService.getById(spostId);
		User userLogined = userService.getByUsername(getUsernameLogined());
		spostService.changeLikeStatus(spost, userLogined.getId());
		return "redirect:/user/group/" + groupFocusId;
	}
	
	
	// Delete
	@RequestMapping(value = {"/user/spost/home/delete/{spostId}"}, method = RequestMethod.GET)
	public String deleteHomeSpost(@PathVariable("spostId") Integer spostId) throws IOException {
		spostService.delete(spostService.getById(spostId));
		return "redirect:/user/home";
	}
	
	@RequestMapping(value = {"/user/spost/group/delete/{spostId}"}, method = RequestMethod.GET)
		public String deleteGroupSpost(@PathVariable("spostId") Integer spostId) throws IOException {
		spostService.delete(spostService.getById(spostId));
		Integer groupFocusId = (Integer) session.getAttribute("groupFocusId");
		return "redirect:/user/group/" + groupFocusId;
	}
	
	@RequestMapping(value = {"/user/spost/profile/delete/{spostId}"}, method = RequestMethod.GET)
		public String deleteProfileSpost(@PathVariable("spostId") Integer spostId) throws IOException {
		spostService.delete(spostService.getById(spostId));
		Integer userId = (Integer) session.getAttribute("userFocusId");
		return "redirect:/user/profile/"+userId;
	}
	
	
	// Remember
	@RequestMapping(value = {"/user/spost/home/remember/{spostId}"}, method = RequestMethod.GET)
	public String rememberHomeSpost(@PathVariable("spostId") Integer spostId) throws IOException {
		rememberService.remember(spostId, getUserLogined().getId());
		return "redirect:/user/home";
	}
	
	@RequestMapping(value = {"/user/spost/group/remember/{spostId}"}, method = RequestMethod.GET)
	public String rememberGroupSpost(@PathVariable("spostId") Integer spostId) throws IOException {
		rememberService.remember(spostId, getUserLogined().getId());
		Integer groupFocusId = (Integer) session.getAttribute("groupFocusId");
		return "redirect:/user/group/" + groupFocusId;
	}
	
	@RequestMapping(value = {"/user/spost/profile/remember/{spostId}"}, method = RequestMethod.GET)
		public String rememberProfileSpost(@PathVariable("spostId") Integer spostId) throws IOException {
		rememberService.remember(spostId, getUserLogined().getId());
		Integer userId = (Integer) session.getAttribute("userFocusId");
		return "redirect:/user/profile/"+userId;
	}
	
	
	// Comment
	@RequestMapping(value = {"/user/spost/home/comment/{spostId}"}, method = RequestMethod.POST)
	public String commentHomeSpost(@PathVariable("spostId") Integer spostId,
			@RequestParam("comment") String comment) throws IOException {
			
		
		Comment newComment = new Comment();
		newComment.setContent(comment);
		newComment.setSpost_comment(spostService.getById(spostId));
		newComment.setUserId(getUserLogined().getId());
		newComment.setCreateDate(new Date());
		commentService.saveOrUpdate(newComment);
		return "redirect:/user/home";
	}
	
	@RequestMapping(value = {"/user/spost/group/comment/{spostId}"}, method = RequestMethod.POST)
	public String commentGroupSpost(@PathVariable("spostId") Integer spostId,
			@RequestParam("comment") String comment) throws IOException {
		
		Comment newComment = new Comment();
		newComment.setContent(comment);
		newComment.setSpost_comment(spostService.getById(spostId));
		newComment.setUserId(getUserLogined().getId());
		newComment.setCreateDate(new Date());
		commentService.saveOrUpdate(newComment);
		Integer groupFocusId = (Integer) session.getAttribute("groupFocusId");
		return "redirect:/user/group/" + groupFocusId;
	}
	
	@RequestMapping(value = {"/user/spost/profile/comment/{spostId}"}, method = RequestMethod.POST)
		public String commentProfileSpost(@PathVariable("spostId") Integer spostId,
				@RequestParam("comment") String comment) throws IOException {
		
		Comment newComment = new Comment();
		newComment.setContent(comment);
		newComment.setSpost_comment(spostService.getById(spostId));
		newComment.setUserId(getUserLogined().getId());
		newComment.setCreateDate(new Date());
		commentService.saveOrUpdate(newComment);
		Integer userId = (Integer) session.getAttribute("userFocusId");
		return "redirect:/user/profile/"+userId;
	}
	
}
