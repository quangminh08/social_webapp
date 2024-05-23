package vn.dev.tttn.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import vn.dev.tttn.entity.Group;
import vn.dev.tttn.entity.User;
import vn.dev.tttn.illconst.Constants;

@Service
public class GroupService extends BaseService<Group> implements Constants{

	@Override
	public Class<Group> clazz() {
		// TODO Auto-generated method stub
		return Group.class;
	}
	
	@Autowired
	private UserService userService;
	
	public List<Group> getMyGroup(User user){
		return user.getGroups();
	}
	
	// kiểm tra người dùng có upload picture không
	public boolean isEmptyUploadFile(MultipartFile pictureFile) {
		if(pictureFile == null || pictureFile.getOriginalFilename().isEmpty()) {
			return true;
		}
		return false;
	}
	
	@Transactional
	public void saveGroup(Group group, MultipartFile pictureFile, User user) {
		String path;
		
		// Save
		if(!isEmptyUploadFile(pictureFile)) {
			//save 
			path = STORAGE_FOLDER + "/group-cover-images/" + pictureFile.getOriginalFilename().toString();
			try {
				pictureFile.transferTo(new File(path));
			}catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}	
			// save path into database
			group.setAvatar("group-cover-images/" + pictureFile.getOriginalFilename().toString());	
		}	
		else {
			group.setAvatar("group-cover-images/cover.png");
		}
		group.setCreateDate(new Date());
		group.setUserId(user.getId());
		super.saveOrUpdate(group);
		user.addGroup(group);
		userService.saveOrUpdate(user);
	}
	
	public boolean checkInGroup(User userLogined, Integer groupId) {
		Group _group = getById(groupId);
		List<Group> groupsOfUser = userLogined.getGroups();
		if(groupsOfUser.contains(_group)) {
			return true;
		}
		return false;
	}

}
