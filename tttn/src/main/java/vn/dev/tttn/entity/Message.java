package vn.dev.tttn.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "tbl_message")
public class Message extends BaseEntity{
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="send_id", nullable = false)
	private User user_message; // map to user
	
	@Column(name = "receive_id", nullable = false)
	private Integer receiveId;
	
	@Column(name = "content", length=5000, nullable = false)
	private String content;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "update_date", nullable = true)
	private Date updateDate;
	
	public User getUser_message() {
		return user_message;
	}

	public void setUser_message(User user_message) {
		this.user_message = user_message;
	}

	public Integer getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(Integer receiveId) {
		this.receiveId = receiveId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Message(Integer id, Date createDate, User user_message, Integer receivedObjectId, String content,
			Date updateDate) {
		super(id, createDate);
		this.user_message = user_message;
		this.receiveId = receivedObjectId;
		this.content = content;
		this.updateDate = updateDate;
	}

	public Message() {}
	
}
