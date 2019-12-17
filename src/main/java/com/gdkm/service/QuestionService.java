package com.gdkm.service;

import com.gdkm.model.Question;
import org.springframework.data.domain.Page;

public interface QuestionService {

    Question addQuestion(Question question);

    Page<Question> getPageSort(Integer page, Integer size);

}
