package com.gdkm.service.impl;

import com.gdkm.Repository.ChatlogRepository;
import com.gdkm.Repository.UserRepository;
import com.gdkm.dao.ChatlogMapper;
import com.gdkm.model.Chatlog;
import com.gdkm.model.User;
import com.gdkm.service.ChatlogService;
import com.gdkm.vo.AppUser;
import com.gdkm.vo.ChatVo;
import com.gdkm.vo.PageVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ChatlogServiceImpl implements ChatlogService {
    @Autowired
    private ChatlogRepository chatlogRepository;

    @Resource
    private ChatlogMapper chatlogMapper;

    @Autowired
    private UserRepository userRepository;



    @Override
    public List<ChatVo> findAllByUserAndTouser(Integer touser) {
        Subject subject = SecurityUtils.getSubject();
        User user= (User)subject.getPrincipal();
        List<Chatlog> allByUserAndTouser = chatlogMapper.findAllByUserAndTouser(user.getUserId(), touser);
        List<ChatVo> chatVoList=new ArrayList<>();
        for (Chatlog chatlog:allByUserAndTouser){
            User one = userRepository.findOne(chatlog.getUser());
            ChatVo chatVo=new ChatVo();
            chatVo.setCId(chatlog.getCId());
            chatVo.setUserId(chatlog.getUser());
            chatVo.setUserIocn(one.getUserIcon());
            chatVo.setCltext(chatlog.getCltext());
            chatVo.setClstate(chatlog.getClstate());
            /**
             * 0是自己  1是对方
             */
            if(chatlog.getUser().equals(user.getUserId())){
                chatVo.setTpye("0");
            }else {
                chatVo.setTpye("1");

            }
            chatVoList.add(chatVo);

            //修改为已读
            chatlogMapper.updateClstate("1",chatlog.getCId());
        }


        return chatVoList;
    }

    @Override
    public Set<AppUser> userSet(Set<AppUser> userSet) {
        Subject subject = SecurityUtils.getSubject();
        User user= (User)subject.getPrincipal();
        if (userSet!=null) {
            if (userSet.size()>0) {
                for (AppUser appUser : userSet) {
                    List<Chatlog> allByUserAndTouser = chatlogMapper.findAllByUserAndTouser2(appUser.getUserId(), user.getUserId());
                    for (Chatlog chatlog : allByUserAndTouser) {
                        if (chatlog.getClstate().equals("0")) {
                            appUser.setXinxi(true);
                        } else {
                            appUser.setXinxi(false);
                        }
                    }
                }
            }
        }
        return userSet;
    }

    @Override
    public Map<Integer, AppUser> userMap(Map<Integer, AppUser> userMap) {
        Subject subject = SecurityUtils.getSubject();
        User user= (User)subject.getPrincipal();
        if (userMap!=null) {
            if (userMap.size()>0) {
                for (AppUser appUser : userMap.values()) {
                    List<Chatlog> allByUserAndTouser = chatlogMapper.findAllByUserAndTouser2(appUser.getUserId(), user.getUserId());
                    for (Chatlog chatlog : allByUserAndTouser) {
                        if (chatlog.getClstate().equals("0")) {
                            appUser.setXinxi(true);
                        } else {
                            appUser.setXinxi(false);
                        }
                    }
                }
            }
        }
        return userMap;
    }

    @Override
    public ChatVo addChatlog(Integer touser, String text) {
        Subject subject = SecurityUtils.getSubject();
        User user= (User)subject.getPrincipal();
        Chatlog chatlog=new Chatlog();
        chatlog.setCltext(text);
        chatlog.setUser(user.getUserId());
        chatlog.setTouser(touser);
        chatlog.setClstate("0");
        Chatlog save = chatlogRepository.save(chatlog);
        ChatVo chatVo=new ChatVo();
        chatVo.setCId(save.getCId());
        chatVo.setUserId(user.getUserId());
        chatVo.setUserIocn(user.getUserIcon());
        chatVo.setCltext(chatlog.getCltext());
        chatVo.setClstate(chatlog.getClstate());
        /**
         * 0是自己  1是对方
         */
        chatVo.setTpye("1");

        return chatVo;
    }

    @Override
    public PageVo<ChatVo> chatlogPage(Integer touser, Integer currentPage, Integer pageSize) {
        Subject subject = SecurityUtils.getSubject();
        User userSession = (User) subject.getPrincipal();
        List<Chatlog> chatlogs = chatlogMapper.queryChatlogsByUserAndTouser(userSession.getUserId(), touser, currentPage - 1, pageSize);
        Integer count = chatlogMapper.chatlogCount(userSession.getUserId(), touser);
        PageVo<ChatVo> pageVo = new PageVo<>();
        pageVo.setTotalPage(count);
        pageVo.setCurrentPage(currentPage);
        List<ChatVo> chatVoList = new ArrayList<>();
        for (Chatlog chatlog : chatlogs) {
            User one = userRepository.findOne(chatlog.getUser());
            ChatVo chatVo = new ChatVo();
            chatVo.setUserId(chatlog.getUser());
            chatVo.setCId(chatlog.getCId());
            chatVo.setUserIocn(one.getUserIcon());
            chatVo.setCltext(chatlog.getCltext());
            chatVo.setClstate(chatlog.getClstate());
            /**
             * 0是自己  1是对方
             */
            if (chatlog.getUser().equals(userSession.getUserId())) {
                chatVo.setTpye("0");
            } else {
                chatVo.setTpye("1");

            }
            chatVoList.add(chatVo);
        }
        pageVo.setList(chatVoList);
        return pageVo;
    }

    @Override
    public void upChatlog(String clstate,Integer cId) {
        chatlogMapper.updateClstate(clstate,cId);
    }

    @Override
    public void deleteChatlog(Integer cId) {
        chatlogMapper.delete(cId);
    }

    @Override
    public void deleteChatlogAll(Integer touser) {
        Subject subject = SecurityUtils.getSubject();
        User userSession = (User) subject.getPrincipal();
        chatlogMapper.deleteAll(userSession.getUserId(),touser);
    }
}
