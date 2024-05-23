package vn.dev.tttn.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
//import javax.persistence.OneToOne;
//import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
@Entity
@Table(name = "tbl_user")
public class User extends BaseEntity implements UserDetails {
	@Column(name = "username", length = 60, nullable = false)
	private String username;

	@Column(name = "password", length = 150, nullable = false)
	private String password;

	@Column(name = "nickname", length = 60, nullable = false)
	private String nickname;

	@Column(name = "email", length = 200, nullable = true)
	private String email;

	@Column(name = "description", length = 500, nullable = true)
	private String description;

	@Column(name = "avatar", length = 255, nullable = true)
	private String avatar;

	@Column(name = "status")
	private Boolean status = Boolean.FALSE;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user_spost")
	private Set<Spost> posts = new HashSet<>();
	public void addRelationalPost(Spost post) {
		posts.add(post);
		post.setUser_spost(this);
	}
	public void deleteRelationalUser_Post(Spost post) {
		posts.remove(post);
		post.setUser_spost(null);
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user_friend")
	private Set<Friend> friends = new HashSet<>();
	public void addRelationalFriend(Friend friend) {
		friends.add(friend);
		friend.setUser_friend(this);
	}
	public void deleteRelationalFriend(Friend friend) {
		friends.remove(friend);
		friend.setUser_friend(null);
	}

	@OneToOne(mappedBy = "user_role", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Role role;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "users_groups")
	private List<Group> groups = new ArrayList<Group>();
	public void addGroup(Group group) {
		group.getUsers_groups().add(this);
		groups.add(group);
	}
	public void deleteGroups(Group group) {
		group.getUsers_groups().remove(this);
		groups.remove(group);
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user_message")
	private Set<Message> messages = new HashSet<>();
	public void addRelationalMessage(Message message) {
		messages.add(message);
		message.setUser_message(this);
	}
	public void deleteRelationalMessage(Message message) {
		messages.remove(message);
		message.setUser_message(null);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Set<Spost> getPosts() {
		return posts;
	}

	public void setPosts(Set<Spost> posts) {
		this.posts = posts;
	}

	public Set<Friend> getFriends() {
		return friends;
	}

	public void setFriends(Set<Friend> friends) {
		this.friends = friends;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	public Set<Message> getMessages() {
		return messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	public User(Integer id, Date createDate, String username, String password, String nickname, String avatar, 
			String description, String email, Boolean status, Set<Spost> posts, Set<Friend> friends,
			Role role, List<Group> groups, Set<Message> messages) {
		super(id, createDate);
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.email = email;
		this.description = description;
		this.avatar = avatar;
		this.status = status;
		this.posts = posts;
		this.friends = friends;
		this.role = role;
		this.groups = groups;
		this.messages = messages;
	}
	public User() {
	}



	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<Role> roles = new ArrayList<Role>();
		roles.add(this.role);
		return roles;
	}
}
