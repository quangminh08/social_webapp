
package vn.dev.tttn.service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vn.dev.tttn.entity.User;



@Service
public class UserDetailsServiceImpl extends BaseService<User> implements UserDetailsService{
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//Conect to DB lay user theo user name
		String sql = "SELECT * FROM flow_chat.tbl_user WHERE username = '" + username + "' AND status = 1";
		
		User user = this.getEntityByNativeSql(sql);
		
		try {
			System.out.println("UserDetailsServiceImpl == "+user);
			System.out.println("User password: " + user.getPassword());
			System.out.println("Username: " + user.getUsername());
			return user;
		} catch (UsernameNotFoundException e) {
			e.printStackTrace();
		}
		return new User(); //UserDetails không được null.
		
//		if(user != null) {
//			System.out.println("tim thay != "+user);
//			System.out.println("User password: " + user.getPassword());
//			System.out.println("Username: " + user.getUsername());
//			return user;
//		}else {
//			System.out.println("khong tim thay == " + user);
//			User usertest = new User();
//			usertest.setUsername("bossflowchat");
//			usertest.setPassword("$2a$04$oJPJxbtHsVF4DMFC82edhemronwmVIVbn.gXKpEqL52.Jrg5B.J7a");
//			
//			return usertest;
//		}
		
	}

	@Override
	public Class<User> clazz() {
		// TODO Auto-generated method stub
		return User.class;
	}
}
