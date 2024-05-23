package vn.dev.tttn.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_comment")
public class Comment extends BaseEntity{
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="spost_id")
	private Spost spost_comment;
	
	@Column(name = "user_id", nullable = false)
	private Integer userId;
	
	@Column(name = "content", length=5000, nullable = true)
	private String content;



	public Spost getSpost_comment() {
		return spost_comment;
	}

	public void setSpost_comment(Spost spost_comment) {
		this.spost_comment = spost_comment;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Comment(Integer id, Date createDate, Spost spost_comment, Integer userId, String content) {
		super(id, createDate);
		this.spost_comment = spost_comment;
		this.userId = userId;
		this.content = content;
	}
	
	public Comment() {}
}
