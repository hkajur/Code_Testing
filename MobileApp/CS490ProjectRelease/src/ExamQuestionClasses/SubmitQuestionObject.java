package ExamQuestionClasses;

public class SubmitQuestionObject {
	private String questionID;
	private String userAnswer;
	
	public String getQuestionId(){
		return questionID;
	}
	public String getUserAnswer(){
		return userAnswer;
	}
	public void setQuestionId(String id){
		questionID = id; 
	}
	public void setUserAnswer(String answer){
		userAnswer = answer; 
	}
}
