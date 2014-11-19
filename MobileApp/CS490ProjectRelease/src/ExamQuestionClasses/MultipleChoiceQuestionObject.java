package ExamQuestionClasses;

public class MultipleChoiceQuestionObject extends QuestionObject{

	QuestionObject question;
	String correctAnswer;
	String correctReasoning;
	
	String wrong1;
	String wrong2;
	String wrong3;
	
	String wrongReason1;
	String wrongReason2;
	String wrongReason3;
	
	public MultipleChoiceQuestionObject(){
		question.setId(null);
		question.setQuestion(null);
		question.setType("MultipleChoice");
		
		correctAnswer = null;
		correctReasoning = null;
		
		wrong1 = null;
		wrong2 = null;
		wrong3 = null;
		
		wrongReason1 = null;
		wrongReason2 = null;
		wrongReason3 = null;
		
		question.setPoints(0);
	}
	public MultipleChoiceQuestionObject(
			String id,
			String in_question,
			String correctAns,
			String correctRes,
			String in_wrong1,
			String wrong1res,
			String in_wrong2,
			String wrong2res,
			String in_wrong3,
			String wrong3res,
			int points){
		
		question.setId(id);
		question.setQuestion(in_question);
		question.setType("MultipleChoice");
		
		correctAnswer = correctAns;
		correctReasoning = correctRes;
		
		wrong1 = in_wrong1;
		wrong2 = in_wrong2;
		wrong3 = in_wrong3;
		
		wrongReason1 = wrong1res;
		wrongReason2 = wrong2res;
		wrongReason3 = wrong3res;
		question.setPoints(points);
	}
		
	public String getCorrectAns(){
		return correctAnswer;
	}
	public String getCorrectRes(){
		return correctReasoning;
	}
	public String[] getWrongAnswers(){
		String[] temp = {wrong1,wrong2,wrong3};
		return temp;
	}
	public String[] getWrongReasons(){
		String[] temp = {wrongReason1,wrongReason2,wrongReason3};
		return temp;
	}

}
