package ExamQuestionClasses;

public class QuestionObject {

	String QUESTION_ID;
	String QUESTION_TYPE;
	String QUESTION;
	int POINTS;

	public QuestionObject(){
		QUESTION_ID = null;
		QUESTION_TYPE = null;
		QUESTION = null;
		POINTS = 0;
	}
	
	public QuestionObject(String id, String type, String question){
		QUESTION_ID = id;
		QUESTION_TYPE = type;
		QUESTION = question;
		POINTS = 0;
	}
	
	public int getPoints(){
		return POINTS;
	}
	
	public void setPoints(int point){
		POINTS = point;
	}
	
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

	public String toString(){
		return "QuestionId: " + QUESTION_ID	+ "\n"
		+ "QuestionType: " + QUESTION_TYPE 	+ "\n"
		+ "Question: " + QUESTION 			+ "\n"
		+ "Points: " + POINTS 				+ "\n";
	}
}
