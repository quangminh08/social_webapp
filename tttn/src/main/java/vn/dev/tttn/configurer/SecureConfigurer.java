package vn.dev.tttn.configurer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import vn.dev.tttn.service.UserDetailsServiceImpl;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecureConfigurer extends WebSecurityConfigurerAdapter{
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		
		http.csrf().disable().authorizeRequests() //bat dau cau hinh: tat ca cac requests tu
												  //trinh duyet deu duoc bat trong phuong thuc nay
		
		//Cho phep cac request, static resources khong bi rang buoc login
		.antMatchers("/StorageFolder/**", "/admin/**","/user/**", "/login", "/logout").permitAll()
		
		//Cac requests co action bat dau voi "/manager/..." phai xac thuc (login) moi duoc vao (authenticated())
//		.antMatchers("/manager/**").authenticated()
		.antMatchers("/boss/**").hasAnyAuthority("BOSS") //CHI ROLE ADMINISTRATOR MOI VAO DUOC CAC TRANG ADMINISTRATOR
		.antMatchers("/manager/**").hasAnyAuthority("BOSS", "MANAGER") //hasAnyAuthority cho phép duyệt nhiều role cùng một lệnh
//		.antMatchers("/staff/**").hasAnyAuthority("STAFF")
		.and()
		
		 
		
		//neu request den trang (admin) ma chua xac thuc thi redirect ve trang login
		.formLogin().loginPage("/login").loginProcessingUrl("/login_processing_url")
		
//		.defaultSuccessUrl("/manager/home-admin", true) //login thanh cong thi tro ve trang home.jsp
		
		// chuyển đến request phù hợp với Role
		.successHandler(new UrlAuthenticationSuccessHandler())
		
		.failureUrl("/login?login_error=true") //login khong thanh cong (o lai trang login voi co bao loi)
		
		.and()
		
		//Cau hinh phan logout: logout tro ve trang login
		.logout().logoutUrl("/logout").logoutSuccessUrl("/login").invalidateHttpSession(true)
		.deleteCookies("JSESSIONID");
		
//		.and().rememberMe().key("uniqueAndSecret").tokenValiditySeconds(86400);
	}
	
	
	
	//Cau hinh phan dang nhap bang tai khoan trong DB
		@Autowired
		private UserDetailsServiceImpl userDetailsService; //bean
		
		@Autowired //Tham so cua phuong thuc (auth) duoc inject
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
			//auth tim cac bean kieu AuthenticationManagerBuilder
			auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder(4));
		}
		
}
