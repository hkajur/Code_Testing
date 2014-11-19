package ExamQuestionClasses;

public class ProgramQuestionObject extends QuestionObject{

	QuestionObject question;
	String CORRECT_OUTPUT;
	
	public ProgramQuestionObject(){
		question.setId(null);
		question.setPoints(0);
		question.setType("ShortAnswer");
		question.setQuestion(null);
		CORRECT_OUTPUT = null;
	}
	
	public ProgramQuestionObject(
			String id,
			String inQuestion,
			String correctAns,
			int points){
		question.setId(id);
		question.setPoints(points);
		question.setType("Programming");
		question.setQuestion(inQuestion);
		CORRECT_OUTPUT = correctAns;		
	}
	
	public String getCorrectAns(){
		return CORRECT_OUTPUT;
	}
}
