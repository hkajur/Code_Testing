package com.example.cs490project;

public class QuestionObject {

	String QUESTION_ID;
	String QUESTION_TYPE;
	String QUESTION;

	public String getQuestion() {
		return QUESTION;
	}
	
	public void setQuestion(String question) {
		this.QUESTION= question;
	}

	public String getId() {
		return QUESTION_ID;
	}

	public void setId(String id) {
		this.QUESTION_ID = id;
	}

	public String getType() {
		return QUESTION_TYPE;
	}
	
	public void setType(String type) {
		this.QUESTION_TYPE = type;
	}

}
