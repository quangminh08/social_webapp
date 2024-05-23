package vn.dev.tttn.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_remember")
public class Remember extends BaseEntity{
	
	@Column(name = "spost_id")
	private Integer spostId;
	
	@Column(name = "user_id")
	private Integer userId;

	public Integer getSpostId() {
		return spostId;
	}

	public void setSpostId(Integer spostId) {
		this.spostId = spostId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Remember(Integer id, Date createDate, Integer spostId, Integer userId) {
		super(id, createDate);
		this.spostId = spostId;
		this.userId = userId;
	}
	
	public Remember() {	}
}
