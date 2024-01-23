package com.yuno.model;

import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "fullName")
	private String fullName;
	
	@Column(name = "email", unique = true)
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
	@JoinTable(
				name = "user_roles",
				joinColumns = @JoinColumn(name = "userId" , referencedColumnName = "id"),
				inverseJoinColumns = @JoinColumn(name = "roleId" , referencedColumnName = "id"))
	private Collection<Roles> roles;

	public Users(String fullName, String email, String password, Collection<Roles> roles) {
		super();
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}
	 public Users() {
		 
	 }
	
}
