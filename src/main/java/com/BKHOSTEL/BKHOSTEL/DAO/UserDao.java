package com.BKHOSTEL.BKHOSTEL.DAO;

import com.BKHOSTEL.BKHOSTEL.Entity.RefreshToken;
import com.BKHOSTEL.BKHOSTEL.Entity.User;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public interface UserDao {
    public User getUserProfileById(String userId);

    public void findAndDeleteUserById(String userId);

    public User findById(String userId);

    public User findByUserName(String userName);
    public User findByEmail(String email);
    public User save(User user);
}
