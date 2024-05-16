package com.app.interview.murni.model.res;

import java.io.Serializable;



public class BodyJson implements Serializable{
	
    public Long id_seq;    
	public String nama ;    
	public String email ;
	public String katasandi;
	public Long getId_seq() {
		return id_seq;
	}
	public void setId_seq(Long id_seq) {
		this.id_seq = id_seq;
	}
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getKatasandi() {
		return katasandi;
	}
	public void setKatasandi(String katasandi) {
		this.katasandi = katasandi;
	}	
	
	
	
	

}
