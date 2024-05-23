package vn.dev.tttn.controller;

import java.io.IOException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bouncycastle.openpgp.PGPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.dev.tttn.dto.BoxChat;
import vn.dev.tttn.entity.User;
import vn.dev.tttn.entity.Message;
import vn.dev.tttn.service.KeyBasedFileProcessor;
import vn.dev.tttn.service.MessageService;
import vn.dev.tttn.service.UserService;

@Controller
public class MessageController extends BaseController{

	@Autowired
	private MessageService messageService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession session;
	
	//flowchat\target\classes\output\sample.pgp
	@Value("classpath:receive-process-module/receive_message.pgp")
	private Resource encryptOutputResource;

	//flowchat\target\classes\input\sample.txt
	@Value("classpath:send-process-module/send_message.txt")
	private Resource encryptInputResource;
	
	@Value("classpath:key/quangminh_pub_key.pgp")
	private Resource publicKeyResource;
	
	@Value("classpath:key/quangminh_priv_key.pgp")
	private Resource privateKeyResource;
	
	@Value("classpath:receive-process-module/receive_message.pgp")
	private Resource decryptInputResource;
	
	
	@RequestMapping(value = "/chat-app", method=RequestMethod.GET)
	public String getForm(final Model model,
							final HttpServletRequest request) throws IOException, NoSuchProviderException{

		BoxChat boxChat = null;
		if(session.getAttribute("boxChat") != null) {
			boxChat = (BoxChat) session.getAttribute("boxChat");
		}
		else{
			System.out.println("=	=	=	= chat-app BOXCHAT IS NULL");
			User _userLogined = userService.getByUsername(getUsernameLogined());
			boxChat = new BoxChat();
			boxChat.setReceiveId(2);
//			boxChat.setSendUserId(_userLogined.getId());
			boxChat.setSendId(_userLogined.getId());
			session.setAttribute("boxChat", boxChat);
			session.setAttribute("userLoginId", _userLogined.getId());
		}
		
//		Integer userLoginId = (Integer) session.getAttribute("userLoginId");
//		Integer receiveId = (Integer) session.getAttribute("receiveId");

		User partnerUser = userService.getByIdSql(boxChat.getReceiveId());
		User subjectUser = userService.getById(boxChat.getSendId());
		model.addAttribute("subjectUser", subjectUser);
		model.addAttribute("partnerUser", partnerUser);
		
		System.out.println("subjectUser name" + boxChat.getSendId());
		System.out.println("subjectUser name" + boxChat.getReceiveId());
		System.out.println("subjectUser name" + boxChat.getSendId());
		System.out.println("partnerUser name" + partnerUser.getNickname());
//		System.out.println("partnerUser name" + partnerUser.getNickname());
		
		// DECRYPTION
			String decryptInputFileName = decryptInputResource.getFile().getPath().toString(); // lấy đường dẫn đầy đủ
			String privateKeyFileName = privateKeyResource.getFile().getPath().toString();
		// lấy mã(content) từ database lưu vào receive_message => giải mã, sau đó lấy từ 
		// D:\\AA_Learn\\NguyenQuangMinh_TTTN\\NguyenQuangMinh_TTTN_API\\flowchat\\send_message.txt
		// lấy mã tin nhan từ db LIST<Message>
		List<Message> getMessages = messageService.getMessagesByUserId2( boxChat.getSendId(), boxChat.getReceiveId());
		// lưu vào 
		ArrayList<String> componentMessage = new ArrayList<String>();
		for(Message s : getMessages) {
			componentMessage.add(s.getContent());  //chuyển sang list để phù hợp với hàm saveData().
			messageService.saveData(componentMessage, decryptInputFileName);  // lưu vào receive_msg.pgp trong receive-process-module.
			KeyBasedFileProcessor.decryptFile(decryptInputFileName, privateKeyFileName, "openpgp".toCharArray(), null);//DECRYPT-receive_msg.pgp
			//Danh tin nhắn sau giải mã 
			List<String> decryptMessages = 
						messageService.loadData("D:\\AA_Learn\\NguyenQuangMinh_TTTN\\NguyenQuangMinh_TTTN_API\\tttn\\send_message.txt");
			String _fileContent = "";
			for(int i=0; i<decryptMessages.size(); i++) {
				if(i == decryptMessages.size()-1) {					// đẩy decrypted data vào String Obj để setContent cho Message Obj
					_fileContent += decryptMessages.get(i);// dòng cuối
				}
				else {
					_fileContent += decryptMessages.get(i) + "\n";		// muốn lấy từng dòng một
				}
			}
			s.setContent(_fileContent);
			System.out.println("\n_fileContent SET: " + s.getContent());
			componentMessage.remove(0);
		}
		
		if(getMessages.size() == 0) {
			getMessages.add(new vn.dev.tttn.entity.Message());
		}
		model.addAttribute("getMessages", getMessages);

		int totalMessages = getMessages.size();
		model.addAttribute("totalMessages", totalMessages);
		session.setAttribute("boxChat", boxChat);
		// laays tam
//		List<User> users = userService.findAll();
		List<User> users = userService.getListSqlById(boxChat.getSendId());
		model.addAttribute("users", users);
		return "chat-app";
	}
	 
	@RequestMapping(value = "send-message", method=RequestMethod.POST)
	public String Message(final HttpServletRequest request,
			final HttpServletResponse response) throws IOException, NoSuchProviderException, PGPException {
		
		//lấy nội dung trong ô nhập in nhắn
		String message = request.getParameter("message");
		// kiểm tra xem đã có hội thoại chưa 
		BoxChat boxChat = null;
		if(session.getAttribute("boxChat") != null) {
			boxChat = (BoxChat) session.getAttribute("boxChat");
		}
		else {
			System.out.println("=	=	=	= send-message BOXCHAT IS NULL");
//			User _userLogined = getUserLogined();
//			User _userLogined = userService.getByUsername(getUsernameLogined());
			int receiveId = (int) session.getAttribute("receiveId");
			int userLoginId = (int) session.getAttribute("userLoginId");
			boxChat = new BoxChat();
			boxChat.setReceiveId(receiveId);
			boxChat.setSendId(userLoginId);
			session.setAttribute("boxChat", boxChat);
		}
		
		System.out.println("boxChat rc" + boxChat.getReceiveId());
		System.out.println("boxChat sd" + boxChat.getSendId());
		
		// ENCRYPTION
				String encryptOutputFileName = encryptOutputResource.getFile().getPath().toString();
				String encryptInputFileName = encryptInputResource.getFile().getPath().toString();
				String publicKeyFileName = publicKeyResource.getFile().getPath().toString();	
		// lấy message từ form lưu vào send_mesage => mã hóa, sau đó lấy chuỗi mã hóa từ receive_mesage lưu vào database
		// lấy mesage request được chuyển sang kiểu LIST<>
		ArrayList<String> componentMessage = new ArrayList<String>();
		componentMessage.add(message);
		//in thử xem có đúng không
		for(String s : componentMessage) {
			System.out.println("Component msg: " + s);
		}
		// save into send_msg.txt
		messageService.saveData(componentMessage, encryptInputFileName); // input file đã có data
		// Encrypt
		KeyBasedFileProcessor.encryptFile(encryptOutputFileName, encryptInputFileName, publicKeyFileName, true, true); //output file đã có data
		// SAVE
		ArrayList<String> fileContents = messageService.loadData(encryptOutputFileName); // lấy data từ receive_msg.pgp
		String _fileContent = "";
		for(int i=0; i<fileContents.size(); i++) {
			if(i == fileContents.size()-1) {					// đẩy encrypted data vào String Obj để lưu vào databse
				_fileContent += fileContents.get(i);			
			}else {
				_fileContent += fileContents.get(i) + "\n";			// ghi từng dòng(ok)
			}
			
		}
		System.out.println("luu message");
		messageService.saveToTblMessage(_fileContent, boxChat); // lưu
		
		session.setAttribute("boxChat", boxChat);
		
		// put data
		return "redirect:/chat-app";
	}
	
	
	@RequestMapping(value = "chat-with/{userId}", method=RequestMethod.GET)
	String changePartner(final Model model,
			final HttpServletRequest request,
			@PathVariable("userId") Integer userId) throws IOException {

		BoxChat boxChat = (BoxChat) session.getAttribute("boxChat");
		if (boxChat == null) {
			boxChat = new BoxChat();
		}
		User _userLogined = userService.getByUsername(getUsernameLogined());
		boxChat.setReceiveId(userId);
		boxChat.setSendId(_userLogined.getId());
		session.setAttribute("boxChat", boxChat);
		session.setAttribute("receiveId", userId);
		session.setAttribute("userLoginId", _userLogined.getId());
		System.out.println("chat with is done");
		return "redirect:/chat-app";
	}
	
	
	@RequestMapping(value = "/delete-message/{messageId}", method = RequestMethod.GET)
	public String deleteMesage(final Model model, final HttpServletRequest request, final HttpServletResponse response,
			@PathVariable("messageId") Integer messageId) throws IOException {
		Message message = messageService.getById(messageId);
		BoxChat boxChat = null;
		if(session.getAttribute("boxChat") != null) {
			boxChat = (BoxChat) session.getAttribute("boxChat");
		}else {
			boxChat = new BoxChat();
			boxChat.setReceiveId(message.getReceiveId());
			boxChat.setSendId(message.getUser_message().getId());
		}
		session.setAttribute("boxChat", boxChat);
		// cung xoa theo do on delete cadcase
		messageService.deleteMessage(message);
		return "redirect:/chat-app";
	}
}
