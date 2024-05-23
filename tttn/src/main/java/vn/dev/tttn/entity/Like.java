package vn.dev.tttn.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_like")
public class Like extends BaseEntity{
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="spost_id")
	private Spost spost_like;
	
	@Column(name = "user_id", nullable = false)
	private Integer userId;



	public Spost getSpost_like() {
		return spost_like;
	}

	public void setSpost_like(Spost spost_like) {
		this.spost_like = spost_like;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Like() {
		super();
	}

	public Like(Integer id, Date createDate, Spost spost, Integer userId) {
		super(id, createDate);
		this.spost_like = spost;
		this.userId = userId;
	}
	
}
