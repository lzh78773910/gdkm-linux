package com.gdkm.service.impl;

import com.gdkm.Repository.UserRepository;
import com.gdkm.dao.UserMapper;
import com.gdkm.model.User;
import com.gdkm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import javax.annotation.Resource;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private UserRepository repository;

    @Override
    public User findone(Integer userId) {
        return userMapper.findone(userId);
    }

    public void status(Integer userId){
        User findone = repository.findOne(userId);
        if (findone.getStatus()==true){
            findone.setStatus(false);
        }else{
            findone.setStatus(true);
        }
        repository.save(findone);
    }

    @Override
    public User add(User user) {
        User save = repository.save(user);
        return save;
    }

    public Page<User> userPage(Pageable pageable){
        Page<User> userPage = repository.findAll(pageable);
        List<User> userList = userPage.getContent();
        return new PageImpl<>(userList,pageable,userPage.getTotalPages());
    }

}
