package vn.dev.tttn.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
@Entity
@Table(name = "tbl_role")
public class Role extends BaseEntity implements GrantedAuthority{
	
	@Id
    @Column(name = "id")
    private Integer id;
	
	@OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user_role;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "create_date", nullable = false)
	private Date createDate;
	
	@Column(name = "role_name", length = 45, nullable = false)
	private String roleName;
	
	@Column(name = "description", length = 500, nullable = true)
	private String description;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

	public User getUser_role() {
		return user_role;
	}

	public void setUser_role(User user_role) {
		this.user_role = user_role;
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer integer) {
		this.id = integer;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	

	public Role(Integer id, User user_role, Date createDate, String roleName, String description) {
		super();
		this.id = id;
		this.user_role = user_role;
		this.createDate = createDate;
		this.roleName = roleName;
		this.description = description;
	}


	public Role() {}



	@Override
	public String getAuthority() {
		return this.roleName;
	}
	
}
