package com.gdkm.dao;

import com.gdkm.model.User;

public interface UserMapper {
    User findone(Integer userId);
    int save(User user);
}
