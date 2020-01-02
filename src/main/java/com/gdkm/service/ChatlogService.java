package com.gdkm.service;

import com.gdkm.vo.AppUser;
import com.gdkm.vo.ChatVo;
import com.gdkm.vo.PageVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ChatlogService {
    List<ChatVo> findAllByUserAndTouser (Integer touser);

    Set<AppUser> userSet(Set<AppUser> userSet);

    Map<Integer, AppUser> userMap(Map<Integer, AppUser> userMap);

    ChatVo addChatlog(Integer touser, String text);

    PageVo<ChatVo> chatlogPage(Integer touser, Integer currentPage, Integer pageSize);

    void upChatlog(String clstate,Integer cId);

    void deleteChatlog(Integer cId);

    void deleteChatlogAll(Integer touser);
}
