package com.gdkm.converter;

import com.gdkm.dto.QuestionDto;
import com.gdkm.model.Question;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionToQuestionDtoConverter {

    public static QuestionDto convert(Question question) {
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question, questionDto);
        return questionDto;
    }

    public static List<QuestionDto> convert(List<Question> questions) {
        return questions.stream().map(e -> convert(e)).collect(Collectors.toList());
    }

}
