package ExamQuestionClasses;

public class TrueFalseQuestionObject extends QuestionObject{

	QuestionObject question;
	String trueAnswer;
	String falseAnswer;
	
	String trueReason;
	String falseReason;
	
	public TrueFalseQuestionObject(){
		question.setId(null);
		question.setQuestion(null);
		question.setType("TrueFalse");
		
		trueAnswer = null;
		falseAnswer = null;
		
		trueReason = null;
		falseReason = null;		
		
		question.setPoints(0);
	}
	public TrueFalseQuestionObject(
			String id,
			String inQuestion,
			String trueAns,
			String trueRes,
			String falseAns,
			String falseRes,
			int points){
		
		question.setId(id);
		question.setQuestion(inQuestion);
		question.setType("TrueFalse");
		
		trueAnswer = trueAns;
		falseAnswer = trueRes;
		
		trueReason = falseAns;
		falseReason = falseRes;
		
		question.setPoints(points);
	}
	
	public String getTrueAns(){
		return trueAnswer;
	}
	public String getFalseAns(){
		return falseAnswer;
	}
	public String getTrueRes(){
		return trueReason;
	}
	public String getFalseRes(){
		return falseReason;
	}
	
}
