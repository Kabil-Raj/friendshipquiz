package com.demo.newQuiz.application.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.newQuiz.application.model.Options;
import com.demo.newQuiz.application.model.Question;
import com.demo.newQuiz.application.repository.OptionsRepo;
import com.demo.newQuiz.application.repository.QuestionRepo;

@RestController
public class QuizController {

	@Autowired
	QuestionRepo questionRepo;

	@Autowired
	OptionsRepo optionsRepo;

//	List created to store the questions answered
	List<Integer> questionAnswered = new ArrayList<Integer>();
	private int score = 0;

// 	Saving the question with the respective choices and answer

	@PostMapping("/createquestionandoptions")
	public void setQues(@RequestParam String ques, String choice1, String choice2, String choice3, String choice4,
			String answer) {
		Question setQuestion = new Question(ques);
		Options setOptions = new Options(choice1, choice2, choice3, choice4, answer);
		setQuestion.setOptions(setOptions);
		setOptions.setQuestion(setQuestion);
		questionRepo.save(setQuestion);
	}
	
	
	
//	below mapping are for the player who plays the quiz
	@GetMapping("/friendshipgame")
	public String friendshipgame() {
		String messageToThePlayer = "Let us play a game and find out how much you know about your friend. Total questions: "
				+ questionRepo.count();
		String url = " Please visit 'localhost:8080/playgame?id=0' to start the game";
		return messageToThePlayer + url;
	}

// below is to play the game and gives the result to the user

	@GetMapping("/playgame")
	public String playGame(@RequestParam int id) {

//		checks the id entererd by user is within the num of questions

		if (id < questionRepo.count()) {

//			checks whether the id is already answered or not

			if (questionAnswered.contains(id)) {

				return "You've already answered this question, please skip this and try next";

			} else {
//				it is to get the single row from the database, custom find methods has ben created on question and options repository
				List<String> question = questionRepo.findQuestion();
				List<String> choice1 = optionsRepo.findOption1();
				List<String> choice2 = optionsRepo.findOption2();
				List<String> choice3 = optionsRepo.findOption3();
				List<String> choice4 = optionsRepo.findOption4();

				return "Question: " + question.get(id) + "\n Here are your options: \n " + choice1.get(id) + "\n"
						+ choice2.get(id) + "\n" + choice3.get(id) + "\n" + choice4.get(id)
						+ "\n If you're ready to answer, please visit 'localhost:8080/answertothequestion?id=" + id
						+ "&answer=?'" + "\n Note: replace ? with your answer";
			}
		} else {
			return checkQuestionsAnswered();
		}

	}

	
	
	public String checkQuestionsAnswered() {
		if (questionAnswered.size() == questionRepo.count()) {
			if (score > questionRepo.count() / 1.3) {
				return "You're the bestie to your friend";
			} else if (score > questionRepo.count() / 2 && score < questionRepo.count() / 1.3) {
				return "You fairly know about your friend";
			} else if (score > questionRepo.count() / 4 && score < questionRepo.count() / 2) {
				return "Get into more relationship with your friend";
			} else {
				return "Please find another friend";
			}
		} else {
			long questionsRemaining = questionRepo.count() - questionAnswered.size();
			String questionRemainingMessage = "Still these many questions are remaining " + questionsRemaining
					+ ", please answer the question to know your total score";
			return questionRemainingMessage;

		}
	}

	
	
	
	@GetMapping("/answertothequestion")
	public String checkAnswer(@RequestParam int id, String answer) {
		List<String> getAnswer = optionsRepo.findAnswer();
		if (questionAnswered.contains(id)) {
			return "you've already answered to this question, please skip and try next";
		} else {
			if (answer.equalsIgnoreCase(getAnswer.get(id))) {
				int nextId = storeQuestionsAnswered(id);
				score++;
				String answerCorrectMessage = "Hurray! you got it. Your Current Score: " + score;
				String url = " Please visit 'localhost:8080/playgame/" + nextId + "' for the next question";
				if (questionAnswered.size() == questionRepo.count()) {
					return checkQuestionsAnswered();
				}
				return answerCorrectMessage + url;

			} else {
				int nextId = storeQuestionsAnswered(id);
				String answerWrongMessage = "Sorry, you didn't get it, please visit'localhost:8080/playgame/" + nextId+ "' for the next question";
				if (questionAnswered.size() == questionRepo.count()) {
					return checkQuestionsAnswered();
				}
				return answerWrongMessage;
			}
		}
	
	}

	
	
	
	public int storeQuestionsAnswered(int id) {
		questionAnswered.add(id);
		int nextId = id + 1;
		return nextId;
	}

}
