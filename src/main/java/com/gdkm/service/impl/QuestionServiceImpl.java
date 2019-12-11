package com.gdkm.service.impl;

import com.gdkm.Repository.QuestionRepository;
import com.gdkm.model.Question;
import com.gdkm.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    //问题添加
    @Override
    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Question> getPageSort(Integer page, Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"createtime");
        Pageable pageable = new PageRequest(page - 1,size,sort);
        return questionRepository.findAll(pageable);
    }
}
