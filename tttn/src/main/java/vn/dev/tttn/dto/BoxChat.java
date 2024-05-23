package vn.dev.tttn.dto;


public class BoxChat {
	private Integer sendId;
	private Integer receiveId;

	public BoxChat() {
		super();
	}

	public Integer getSendId() {
		return sendId;
	}

	public void setSendId(Integer sendUserId) {
		this.sendId = sendUserId;
	}

	public Integer getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(Integer receiveObjectId) {
		this.receiveId = receiveObjectId;
	}

	public BoxChat(Integer sendUserId, Integer receiveObjectId) {
		super();
		this.sendId = sendUserId;
		this.receiveId = receiveObjectId;
	}
}
