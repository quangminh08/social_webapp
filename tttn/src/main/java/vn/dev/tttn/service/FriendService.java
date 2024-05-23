package vn.dev.tttn.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import vn.dev.tttn.entity.Friend;
import vn.dev.tttn.entity.User;


@Service
public class FriendService extends BaseService<Friend>{
	
	@Override
	public Class<Friend> clazz(){
		return Friend.class;
	}
	
	// trả về Friend( phục vụ việc lấy user trong userService )
	public List<Friend> getFriendList(Integer userId){
		String sql = "select * from tttnsql.tbl_friend where user_id=" + userId + ";";
		return super.executeNativeSql(sql);
	}

	@Transactional
	public Friend saveAddFrient(User userLogined, Integer userId) {
		Friend _friend = new Friend();
		_friend.setCreateDate(new Date());
		_friend.setFriendId(userId);
		_friend.setUser_friend(userLogined);
		return super.saveOrUpdate(_friend);
	}
	
	@Transactional
	public void saveUnFrient(User userLogined, Integer userId) {
		String sql = "select * from tttnsql.tbl_friend where user_id="
						+ userLogined.getId() + " and friend_id=" + userId;
		Friend _friend = super.getEntityByNativeSql(sql);
		
		super.delete(_friend);
	}
	
	
//	public boolean checkIsFriend(Integer loginedId, Integer objectId) {
//		String sql = "select * from tttnsql.tbl_friend where user_id=" + loginedId 
//					+ " and friend_id=" + objectId;
//		if(super.getEntityByNativeSql(sql) == null) {
//			System.out.println("Checkin Friend FALSE ");
//			return false;
//		}
//		System.out.println("Checkin Friend TRUE ");
//		return true;
//	}
	public boolean checkIsFriend(User logined, Integer objectId) {
		Set<Friend> friendsOfUser = logined.getFriends();
		for(Friend f : friendsOfUser) {
			if(f.getFriendId() == objectId) {
			System.out.println("Checkin Friend FALSE ");
			return true;
			}
		}
		
		System.out.println("Checkin Friend TRUE ");
		return false;
	}
	/*lấy ra danh sach không chùng lặp tuwcslaf userId và objectId có tầm quan trọng như nhau
	 *  làm thế nào để xác định objectId là bạn của friend
	 *  lấy alllist sau đó order by với UserId */


}
