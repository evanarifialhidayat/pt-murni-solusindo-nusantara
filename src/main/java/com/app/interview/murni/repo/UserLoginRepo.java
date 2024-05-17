package com.app.interview.murni.repo;


import java.util.Optional;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

import com.app.interview.murni.model.UserLogin;

@Repository
public interface UserLoginRepo extends DataTablesRepository<UserLogin, Long> {
	UserLogin findByEmail(String Email);
	UserLogin findByEmailAndPassword(String Email,String Password);
	long countByEmail(String Email);
	
}
