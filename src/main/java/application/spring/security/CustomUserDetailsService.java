package application.spring.security;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import application.spring.repository.DBUserRepository;
import application.spring.model.DBUser;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private DBUserRepository dbUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username)  {//throws UsernameNotFoundException
		try{
			DBUser user = dbUserRepository.findByUsername(username);
			return new User(user.getUsername(), user.getPassword(), getGrantedAuthorities(user.getRole()));
		}catch(Exception e){
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));		
		}
	}

	private List<GrantedAuthority> getGrantedAuthorities(String role) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		return authorities;
	}
}