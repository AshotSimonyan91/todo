package com.example.todo.security;


import com.example.todo.entity.User;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * Created by Ashot Simonyan on 18.05.23.
 */

public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private User user;

    public CurrentUser(User user) {
        super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().name()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
