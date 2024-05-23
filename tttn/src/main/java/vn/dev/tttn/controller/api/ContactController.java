package vn.dev.tttn.controller.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.dev.tttn.controller.BaseController;
import vn.dev.tttn.dto.UserModel;


@CrossOrigin(value = "http://localhost:5555")
@RestController
@RequestMapping(value = "/api/v1/")
public class ContactController extends BaseController{
	
	

	@GetMapping("userlogined")
	public UserModel getUserLogin() {
		UserModel model =  userService.getTheOwnerModel();
		return model;
	}
	
//	@GetMapping("userlogined")
//	public ResponseEntity<UserModel> getUserLogin() {
//		UserModel model =  userService.getTheOwnerModel();
//		return ResponseEntity.ok(model);;
//	}
	
}
