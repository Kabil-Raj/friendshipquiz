package com.demo.newQuiz.application.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.newQuiz.application.model.Question;

public interface QuestionRepo extends JpaRepository<Question, Integer>{
	
	@Query("select question from Question")
	List<String> findQuestion();


	

}
