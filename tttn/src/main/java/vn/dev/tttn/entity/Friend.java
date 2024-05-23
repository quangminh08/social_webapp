package vn.dev.tttn.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_friend")
public class Friend extends BaseEntity {
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user_friend;

	@Column(name = "friend_id")
	private Integer friendId;

	public Friend(Integer id, Date createDate, User userFriend, Integer friendId) {
		super(id, createDate);
		this.user_friend = userFriend;
		this.friendId = friendId;
	}

	public Friend() {
	}

	public User getUser_friend() {
		return user_friend;
	}

	public void setUser_friend(User user_friend) {
		this.user_friend = user_friend;
	}

	public Integer getFriendId() {
		return friendId;
	}

	public void setFriendId(Integer friendId) {
		this.friendId = friendId;
	}
	
	

}
