package vn.dev.tttn.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "tbl_group")
public class Group extends BaseEntity {
	
	@Column(name = "group_name", length = 500, nullable = false)
	private String groupName;
	
	@Column(name = "avatar", length = 255, nullable = true)
	private String avatar;
	
	@Column(name = "user_id") // sau này làm thêm phần trưởng nhóm mới cần
	private Integer userId;
	
	@Column(name = "description", length = 5000, nullable = true) // sau này làm thêm phần trưởng nhóm mới cần
	private String description;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "tbl_group_user", joinColumns = @JoinColumn(name = "group_id"),
	inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> users_groups = new ArrayList<User>();

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<User> getUsers_groups() {
		return users_groups;
	}

	public void setUsers_groups(List<User> users_groups) {
		this.users_groups = users_groups;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Group(Integer id, Date createDate, String groupName, String avatar, Integer userId, String description,
			List<User> users_groups) {
		super(id, createDate);
		this.groupName = groupName;
		this.avatar = avatar;
		this.userId = userId;
		this.description = description;
		this.users_groups = users_groups;
	}

	public Group() {
		super();
	}

}
