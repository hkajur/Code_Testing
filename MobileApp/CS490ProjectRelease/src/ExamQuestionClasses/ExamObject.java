package ExamQuestionClasses;

public class ExamObject {

	String EXAM_ID;
	public String EXAM_NAME;
	public String EXAM_STATUS;

	public String getName() {
		return EXAM_NAME;
	}
	
	public void setName(String name) {
		this.EXAM_NAME= name;
	}

	public String getId() {
		return EXAM_ID;
	}

	public void setId(String id) {
		this.EXAM_ID= id;
	}

	public String getStatus() {
		return EXAM_STATUS;
	}
	
	public void setStatus(String status) {
		this.EXAM_STATUS = status;
	}
}
