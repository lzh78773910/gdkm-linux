package com.gdkm.service;

import com.gdkm.model.Chatlog;
import com.gdkm.vo.AppUser;
import com.gdkm.vo.ChatVo;
import com.gdkm.vo.PageVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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
}
