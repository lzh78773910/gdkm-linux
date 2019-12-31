package com.gdkm.dao;

import com.gdkm.model.Chatlog;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChatlogMapper {
    List<Chatlog> findAllByUserAndTouser (@Param("user") Integer user,@Param(value = "touser") Integer touser);
    List<Chatlog> findAllByUserAndTouser2 (@Param("user") Integer user,@Param(value = "touser") Integer touser);
    void updateClstate(@Param("clstate") String clstate,@Param("cid") Integer cId);
    List<Chatlog>  queryChatlogsByUserAndTouser(
            @Param("user")Integer user
            , @Param(value = "touser")Integer touser
            , @Param(value = "currentPage")Integer currentPage
            , @Param(value = "pageSize")Integer pageSize);

    Integer chatlogCount(@Param("user") Integer user,@Param(value = "touser") Integer touser);
}
