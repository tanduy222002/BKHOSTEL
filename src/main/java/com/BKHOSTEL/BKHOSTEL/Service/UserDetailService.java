package com.BKHOSTEL.BKHOSTEL.Service;

import com.BKHOSTEL.BKHOSTEL.Entity.User;
import com.BKHOSTEL.BKHOSTEL.Entity.UserDetail;
import com.BKHOSTEL.BKHOSTEL.Exception.UserNotFoundException;
import com.BKHOSTEL.BKHOSTEL.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailService implements UserDetailsService {
    private UserRepository userRepository;
    @Autowired
    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetail loadUserByUsername(String userName) {
        // Kiểm tra xem user có tồn tại trong database không?
        User user = userRepository.findByUserName(userName);
        if (user == null) {
           throw new UserNotFoundException();
        }
        return new UserDetail(user);
    }
}
