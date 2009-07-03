package view.options.modelstub;

import java.util.Vector;

public class ChatbotQADataType {
	private Vector<String> questions = new Vector<String>();
	private Vector<String> answers = new Vector<String>();
	
	public ChatbotQADataType(){}
	
	public ChatbotQADataType(String question){
		questions.add(question);
	}
	
	public ChatbotQADataType(String question, String answer){
		questions.add(question);
		answers.add(answer);
	}
	
	public ChatbotQADataType(Vector<String> questions, Vector<String> answers){
		this.questions = questions;
		this.answers = answers;
	}
	
	public void addQuestion(String question){
		questions.add(question);
	}
	
	public void removeQuestion (int index){
		questions.remove(index);
	}
	
	public void addAnswer(String answer){
		answers.add(answer);
	}
	
	public void removeAnswer (int index){
		answers.remove(index);
	}
	
	public String toString(){
		String str="";
		int index;
		for (index=0; index<questions.size()-1; index++){
			str += questions.get(index) + ", ";
		}
		str += questions.get(index);
		return str;		
	}
	
	public Vector<String> getQuestions(){
		return questions;
	}
	
	public Vector<String> getAnswers(){
		return answers;
	}
	
	public boolean isEmpty(){
		return questions.isEmpty() || answers.isEmpty();
	}
}
