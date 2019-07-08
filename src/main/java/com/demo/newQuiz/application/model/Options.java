package com.demo.newQuiz.application.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="options")
public class Options {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int optionID;
		
	private String option1;
	
	private String option2;
	
	private String option3;
	
	private String option4;
	
	private String answer;
	
	
	
	public Options() {
		
	}
	
	public Options(String choice1, String choice2, String choice3, String choice4, String answer) {
		this.setOption1(choice1);
		this.setOption2(choice2);
		this.setOption3(choice3);
		this.setOption4(choice4);
		this.setAnswer(answer);
	}
	@OneToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Question question;

	
	public String getQuest() {
		return question.getQuestion();
	}
	
	public int getOptionsID() {
		return optionID;
	}

	public void setOptionsID(int optionsID) {
		this.optionID = optionsID;
	}

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	public String getOption4() {
		return option4;
	}

	public void setOption4(String option4) {
		this.option4 = option4;
	}
	
	

	@JsonIgnore
	public Question getQuestion() {
		return question;
	}
	
	@JsonIgnore
	public void setQuestion(Question question) {
		this.question = question;
	}
	
	
	@JsonIgnore
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	
}
