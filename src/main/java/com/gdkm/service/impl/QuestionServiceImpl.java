package com.gdkm.service.impl;

import com.gdkm.Repository.QuestionRepository;
import com.gdkm.Repository.UserRepository;
import com.gdkm.converter.QuestionToQuestionDtoConverter;
import com.gdkm.dto.QuestionDto;
import com.gdkm.model.Question;
import com.gdkm.model.User;
import com.gdkm.service.QuestionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    //问题添加
    @Override
    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    @Transactional(readOnly = true)
    public PageImpl<QuestionDto> getPageSort(Integer page, Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "createtime");
        Pageable pageable = new PageRequest(page - 1, size, sort);
        Subject subject = SecurityUtils.getSubject();
        User userSession=(User)subject.getPrincipal();
        Page<Question> questionDtoPage = questionRepository.findAll(pageable);
        Question question = new Question();
        List<QuestionDto> covert = QuestionToQuestionDtoConverter.convert(questionDtoPage.getContent());
        for (QuestionDto questionDto:covert){
            User user = userRepository.findOne(questionDto.getCreator());
            questionDto.setUser(user);
        }
        return new PageImpl<QuestionDto>(covert,pageable,questionDtoPage.getTotalElements());
//        return questionRepository.findAll(pageable);
    }

    @Override
    public List<Question> getAll() {
        return questionRepository.findAll();
    }

    //根据ID删除问题
    @Override
    public void deleteQuestionById(Integer qId) {
        questionRepository.delete(qId);
    }

    @Override
    public Page<QuestionDto> list(PageRequest pageable, String title) {
        Page<Question> questionPage;
        if (!(title == null || title.equals(""))) {
            title = '%' + title + '%';
            questionPage = questionRepository.findByTitleLike(pageable, title);
        } else {
            questionPage = questionRepository.findAll(pageable);
        }
        List<QuestionDto> questionDtoList = QuestionToQuestionDtoConverter.convert(questionPage.getContent());
        return new PageImpl<QuestionDto>(questionDtoList, pageable, questionPage.getTotalElements());
    }

    @Override
    public Question findQuestionById(Integer qId) {
        return questionRepository.findOne(qId);
    }

    @Override
    public Question addUserQuestion(String title, String description) {
        Subject subject = SecurityUtils.getSubject();
        User user= (User)subject.getPrincipal();
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setCreator(user.getUserId());
        System.out.println("问题创建成功");
        Question saveQuestion = questionRepository.save(question);
        return saveQuestion;
    }


}
