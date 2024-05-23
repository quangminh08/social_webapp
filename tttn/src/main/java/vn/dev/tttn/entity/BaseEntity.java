package vn.dev.tttn.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.format.annotation.DateTimeFormat;

@MappedSuperclass
public abstract class BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY
	@Column(name = "id")
	private Integer id;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "create_date", nullable = false)
	private Date createDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public BaseEntity(Integer id, Date createDate) {
		super();
		this.id = id;
		this.createDate = createDate;
	}
	
	public BaseEntity() {}
	
}
