package vn.dev.tttn.entity;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "tbl_spost")
public class Spost extends BaseEntity implements vn.dev.tttn.illconst.Constants{
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id", nullable = false)
	private User user_spost; // map to user
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="group_id", nullable = true)
	private Group group;

	@Column(name = "description", length = 10000, nullable = true)
	private String description;
	
	@Column(name = "picture", length = 255, nullable = true)
	private String picture;
	
	@Column(name = "protect", length = 20, nullable = true)
	private String protect;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "spost_like")
	private Set<Like> likes = new HashSet<>();

	public void addRelationalLike(Like like) {
		likes.add(like);
		like.setSpost_like(this);
	}

	public void deleteRelationalPostLike(Like like) {
		likes.remove(like);
//		like.setSpost_like(null);
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "spost_comment")
	private Set<Comment> comments = new HashSet<>();

	public void addRelationalComment(Comment comment) {
		comments.add(comment);
		comment.setSpost_comment(this);
	}

	public void deleteRelationalPostComment(Comment comment) {
		comments.remove(comment);
//		comment.setSpost_comment(null);
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getProtect() {
		return protect;
	}

	public void setProtect(String protect) {
		this.protect = protect;
	}
	

	public User getUser_spost() {
		return user_spost;
	}

	public void setUser_spost(User user_post) {
		this.user_spost = user_post;
	}

	public Set<Like> getLikes() {
		return likes;
	}

	public void setLikes(Set<Like> likes) {
		this.likes = likes;
	}
	
	public int getLikeQuantity() {
		return likes.size();
	}
	
	public boolean checkLiked(Integer userLoginedId) {
		for(Like l : this.likes) {
			if(l.getUserId() == userLoginedId) {
				return true;
			}
		}
		return false;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	
	public int getCommentQuantity() {
		return comments.size();
	}

	
	public Spost(Integer id, Date createDate, User user_spost, Group group, String description, String picture,
			String protect, Set<Like> likes, Set<Comment> comments) {
		super(id, createDate);
		this.user_spost = user_spost;
		this.group = group;
		this.description = description;
		this.picture = picture;
		this.protect = protect;
		this.likes = likes;
		this.comments = comments;
	}

	public Spost() {}
}
