package com.app.interview.murni.model;

import java.beans.Transient;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import lombok.Builder.Default;


@Entity
@Table(name = "userlogin" )
public class UserLogin {

    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id_seq")
    public Long id_seq;
    
    @Column(name="age")
    @Range(min = 18,message = "Age must be at least 18 years")
	public Integer age = 0;
    
    @Column(name="email")
    @NotBlank(message = "Email cannot blank")
    @Size(max = 100, message = "Email should not be greater than 100 caracters")
    @Email(message = "Email format not correct")
	public String email ;
	
	@Column(name="password")
	@NotBlank(message = "Password cannot blank")
    @Size(min = 6, max = 80, message = "Password must have at least 6 caracters and maximum 40")
	public String password;	
	
	@Column(name="token")
	public String token;
	
    
	

	public Long getId_seq() {
		return id_seq;
	}




	public void setId_seq(Long id_seq) {
		this.id_seq = id_seq;
	}




	public Integer getAge() {
		return age;
	}




	public void setAge(Integer age) {
		this.age = age;
	}




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}




	public String getPassword() {
		return password;
	}




	public void setPassword(String password) {
		this.password = password;
	}




	public String getToken() {
		return token;
	}




	public void setToken(String token) {
		this.token = token;
	}




	@Override
	public String toString() {

				return "{\"recid\":"+       id_seq 
				+ ", \"age\":\"" +         age 
				+ "\", \"email\":\"" +      email 
				+ "\", \"password\":\"" +  password
				+ "\", \"token\":\"" +  token
				+ "\", \"id_seq\":\"" +     id_seq
				+"\"}";
	}
	
	
	


	

    
}

