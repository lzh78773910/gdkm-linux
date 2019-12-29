package com.gdkm.service;

import com.gdkm.dto.QuestionDto;
import com.gdkm.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface QuestionService {

    Question addQuestion(Question question);

    PageImpl<QuestionDto> getPageSort(Integer page, Integer size);

    List<Question> getAll();

    void deleteQuestionById(Integer qId);

    Page<QuestionDto> list(PageRequest pageable, String title);

    Question findQuestionById(Integer qId);

    Question addUserQuestion(String title, String description);
}
