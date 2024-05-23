package vn.dev.tttn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.dev.tttn.dto.UserModel;
import vn.dev.tttn.entity.Friend;
import vn.dev.tttn.entity.User;


@Service
public class UserService extends BaseService<User>{

	@Override
	public Class<User> clazz(){
		return User.class;
	}
	
	@Autowired
	private FriendService friendService;
	
	@Autowired
	private TransmitService transmitService;
	
	public User getById(Integer id) {
		return super.getById(id);
	}
	
	public User getByIdSql(Integer id) {
		String sql = "select * from tttnsql.tbl_user p where p.id=" + id +";";
		return super.getEntityByNativeSql(sql);
	}
	
	public List<User> getListSqlById(Integer id) {
		String sql = "select p.id, p.create_date, p.username, p.password, p.nickname, p.avatar, p.description, p.email, p.status from tttnsql.tbl_user p where p.id<>" + id +";";
		return super.executeNativeSql(sql);
	}
	
	public List<User> getListSqlByUsername(String name) {
		String sql = "select * from tttnsql.tbl_user p where p.username<>'" + name +"';";
		return super.executeNativeSql(sql);
	}
	
	
	public UserModel getUserModelByUsername(String username) {
		User user = getByUsername(username);
		UserModel model = new UserModel();
		return transmitService.userToModel(user, model);
	}
	
	public User getByUsername(String username) {
		String sql = "select * from tttnsql.tbl_user where username='"+ username +"';";
		return super.getEntityByNativeSql(sql);
	}
	
	public UserModel getTheOwnerModel() {
		String sql = "select * from tttnsql.tbl_user where username='boss';";
		User user = super.getEntityByNativeSql(sql);
		return transmitService.userToModel(user, new UserModel());
	}
	
//	public UserModel getUserById(Integer id) {
//		User entity = super.getById(id);
//		return transmitService.userToModel(entity, new UserModel());
//	}
	
	// trả về userModel
//	public List<UserModel> getFriendsList(Integer userId){
//		
//		List<User> users = new ArrayList<User>();
//		List<Friend> friends = friendService.getFriendList(userId); //lấy danh sách bạn của đôi tượng
//		for(Friend friend : friends) {
//			try {
//				User user = super.getById(friend.getFriendId());
//				users.add(user);
//			}catch(NullPointerException e){
//				e.printStackTrace();
//				System.out.println("friend is null");
//			}
//		}
//		List<UserModel> userModels = transmitService.userEntitiesToModels(users);
//		return userModels;
//	}
	
	public List<User> getMyFollows(Integer userId){
		
		List<User> users = new ArrayList<User>();
		List<Friend> friends = friendService.getFriendList(userId); //lấy danh sách bạn của đôi tượng
		for(Friend friend : friends) {
			try {
				User user = super.getById(friend.getFriendId());
				users.add(user);
			}catch(NullPointerException e){
				e.printStackTrace();
				System.out.println("friend is null");
			}
		}
		return users;
	}
	
	
	
}
