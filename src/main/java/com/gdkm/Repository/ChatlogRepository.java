package com.gdkm.Repository;

import com.gdkm.model.Chatlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatlogRepository  extends JpaRepository<Chatlog,Integer> {
    List<Chatlog> findAllByUserAndTouser (Integer user ,Integer touser);
}
