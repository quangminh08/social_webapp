package vn.dev.tttn.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import vn.dev.tttn.entity.Like;
import vn.dev.tttn.entity.Remember;
import vn.dev.tttn.entity.Spost;
import vn.dev.tttn.entity.User;
import vn.dev.tttn.illconst.Constants;

@Service
public class SpostService extends BaseService<Spost> implements Constants{
	
	@Autowired
	private LikeService likeService;
	
	@Autowired
	private RememberService rememberService;
	
	public List<Spost> findAll(){
		return super.findAll();
	}
	
	public List<Spost> findAllSql(){
		String sql = "SELECT * FROM tttnsql.tbl_spost;";
		return super.executeNativeSql(sql);
	}
	
	public List<Spost> findInProfile(Integer id){
		String sql = "SELECT * FROM tttnsql.tbl_spost where user_id=" + id + ";";
		return super.executeNativeSql(sql);
	}

	public List<Spost> findInOtherProfile(Integer id){
		String sql = "SELECT * FROM tttnsql.tbl_spost where user_id=" + id + " and protect='Public' ;";
		return super.executeNativeSql(sql);
	}
	
	public List<Spost> findInGroup(Integer id){
		String sql = "SELECT * FROM tttnsql.tbl_spost where group_id=" + id + ";";
		return super.executeNativeSql(sql);
	}
	
	// kiểm tra người dùng có upload picture không
	public boolean isEmptyUploadFile(MultipartFile pictureFile) {
		if(pictureFile == null || pictureFile.getOriginalFilename().isEmpty()) {
			return true;
		}
		return false;
	}
	
	@Transactional
	public Spost saveSpost(Spost post, MultipartFile pictureFile, User user) {
		String path;
		
		// Save
		if(!isEmptyUploadFile(pictureFile)) {
			//save 
			path = STORAGE_FOLDER + "/spost-pictures/" + pictureFile.getOriginalFilename().toString();
			try {
				pictureFile.transferTo(new File(path));
			}catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			
			// save path into database
			post.setPicture("spost-pictures/" + pictureFile.getOriginalFilename().toString());	
		}	
		post.setCreateDate(new Date());
		post.setUser_spost(user);
		return super.saveOrUpdate(post);
	}
	
	
	@Transactional
	public void changeLikeStatus(Spost spost, Integer userId) {
		if(likeService.getLikeBySql(spost.getId(), userId) != null) {
			spost.deleteRelationalPostLike(likeService.getLikeBySql(spost.getId(), userId));
			likeService.deleteLike(likeService.getLikeBySql(spost.getId(), userId));
			return;
		}
		Like _like = new Like();
		_like.setCreateDate(new Date());
		_like.setSpost_like(spost);
		_like.setUserId(userId);
		spost.addRelationalLike(_like);
		super.saveOrUpdate(spost);
	}

	@Override
	public Class<Spost> clazz() {
		// TODO Auto-generated method stub
		return Spost.class;
	}

	public List<Spost> newfeeds() {
		String sql = "SELECT * FROM tttnsql.tbl_spost where protect='Public' ";
		return super.executeNativeSql(sql);
	}
	
	@Transactional
	public void delete(Spost spost) {
		super.delete(spost);
	}

	public List<Spost> getRememberSposts(Integer loginedId){
		List<Remember> remembers = rememberService.getRemembers(loginedId);
		List<Spost> sposts = new ArrayList<Spost>();
		for(Remember r : remembers) {
			try {
				Spost spost = super.getById(r.getSpostId());
				sposts.add(spost);
			}catch(NullPointerException e){
				e.printStackTrace();
				System.out.println("spost in getRememberSposts is null");
			}
		}
		return sposts;
	}
}
