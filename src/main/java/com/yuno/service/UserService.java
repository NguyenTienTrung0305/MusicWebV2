package com.yuno.service;

import java.awt.print.Pageable;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yuno.model.Roles;
import com.yuno.model.Users;
import com.yuno.reponsitory.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	public Users save(Users user) {
		Users sUser = new Users(user.getFullName(), user.getEmail(), user.getPassword(),
				Arrays.asList(new Roles("ROLE_USER"), new Roles("ROLE_ADMIN")));
		return userRepository.save(sUser);
	}

	// get roles and map roles to authority
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Roles> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNameRole())).collect(Collectors.toList());
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userRepository.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password");
		}
		return new User(username, user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}

	public Users findUserByEmail(String username) {
		Users users = userRepository.findByEmail(username);
		if (users == null) {
			throw new UsernameNotFoundException("Invalid username or password");
		}
		return users;
	}

//	public Page<Users> findAll(String keyword, Pageable pageable) {
//		return userRepository.findAll(keyword, pageable);
//	}

}
