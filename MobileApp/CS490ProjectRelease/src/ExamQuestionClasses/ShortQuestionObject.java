package ExamQuestionClasses;

public class ShortQuestionObject extends QuestionObject{

	QuestionObject question;
	String CORRECT_ANS;
	
	public ShortQuestionObject(){
		question.setId(null);
		question.setPoints(0);
		question.setType("ShortAnswer");
		question.setQuestion(null);
		CORRECT_ANS = null;
	}
	
	public ShortQuestionObject(
			String id,
			String inQuestion,
			String correctAns,
			int points){
		question.setId(id);
		question.setPoints(points);
		question.setType("ShortAnswer");
		question.setQuestion(inQuestion);
		CORRECT_ANS = correctAns;		
	}
	
	public String getCorrectAns(){
		return CORRECT_ANS;
	}
}
