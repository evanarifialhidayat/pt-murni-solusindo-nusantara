package com.app.interview.ptaz.services.token;

import com.app.interview.ptaz.model.UserLogin;
import com.app.interview.ptaz.repo.UserLoginRepo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    final UserLoginRepo userLoginRepo;

    public JwtUserDetailsService(UserLoginRepo userLoginRepo) {
        this.userLoginRepo = userLoginRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserLogin user = userLoginRepo.findByEmail(email);
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("USER_ROLE"));
        
        return new User(user.getEmail(), user.getKatasandi(), authorityList);
    }

    public UserDetails createUserDetails(String email, String katasandi) {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("USER_ROLE"));
        return new User(email, katasandi, authorityList);
    }
}