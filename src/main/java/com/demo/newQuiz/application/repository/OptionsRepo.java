package com.demo.newQuiz.application.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.newQuiz.application.model.Options;

public interface OptionsRepo extends JpaRepository<Options, Integer>{
	
	
	
	@Query("select option1 from Options")
	List<String> findOption1();
	
	@Query("select option2 from Options")
	List<String> findOption2();
	
	@Query("select option3 from Options")
	List<String> findOption3();
	
	@Query("select option4 from Options")
	List<String> findOption4();
	
	@Query("select answer from Options")
	List<String> findAnswer();
	
	
}
