package vn.dev.tttn.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.dev.tttn.dto.BoxChat;
import vn.dev.tttn.entity.Message;


@Service
public class MessageService extends BaseService<Message>{
	@Override
	public Class<Message> clazz() {
		return Message.class;
	}
	
	@Autowired
	private UserService userService;
	
	public Message getById(Integer id) {
		return super.getById(id);
	}
	
//	public List<Message> getMessagesByUserId(Integer subjectId, Integer partnerId){
//		String sql = "SELECT * FROM tttnsql.tbl_message p WHERE status=1 and create_by=" + subjectId + " and receive_object_id=" + partnerId;
//		return super.executeNativeSql(sql);
//	}

	
	public List<Message> getMessagesByUserId2(Integer subjectId, Integer partnerId){
		String sql = "SELECT a.id, a.create_date, a.send_id, a.receive_id, a.content, a.update_date "
				+ "FROM tttnsql.tbl_message as a join tttnsql.tbl_user as b "
				+ "ON a.send_id=b.id WHERE (a.send_id in ( " + subjectId + ", " + partnerId + ")" 
											  + " and a.receive_id in (" + subjectId + ", " + partnerId + ")) "
				+ "order by a.id";
		return super.executeNativeSql(sql);
	}
	
	@Transactional
	public Message saveToTblMessage(String message, BoxChat currentBox) {
		Message newMessage = new Message();
		newMessage.setContent(message);
		Date currentDate = new Date();
		newMessage.setCreateDate(currentDate);
		newMessage.setUser_message(userService.getByIdSql(currentBox.getSendId()));
		newMessage.setReceiveId(currentBox.getReceiveId());
		return super.saveOrUpdate(newMessage);
	}
	
	public ArrayList<String> loadData(String path) {
		File file = new File(path);
		FileReader fread = null;
		BufferedReader bufR = null;

		ArrayList<String> list = new ArrayList<String>();

		try {
			fread = new FileReader(file);
			bufR = new BufferedReader(fread);
			String line;
			while ((line = bufR.readLine()) != null) {
				// String b = new String(line);
				list.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufR != null)
					bufR.close();
			} catch (Exception e) {
			}
		}
		return list;
	}

	public void saveData(ArrayList<String> list, String path) {
		File file = new File(path);
		FileWriter fwriter = null;
		BufferedWriter bufW = null;

		try {
			fwriter = new FileWriter(file);
			bufW = new BufferedWriter(fwriter);
			for (String line : list) {
				bufW.write(line);
				bufW.newLine();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufW != null)
					bufW.close();
			} catch (Exception e) {
			}
		}
	}
	
	@Transactional
	public void deleteMessage(Message message) {
		super.delete(message);
	}
}
