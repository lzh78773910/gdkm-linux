package com.gdkm.Repository;

import com.gdkm.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QuestionRepository extends JpaRepository<Question,Integer>, JpaSpecificationExecutor<Question> {

    Page<Question> findByTitleLike(Pageable pageable, String title);

}
