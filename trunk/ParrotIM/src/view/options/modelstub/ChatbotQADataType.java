package view.options.modelstub;

import java.sql.SQLException;
import java.util.Vector;

import model.DatabaseFunctions;

public class ChatbotQADataType {
	private Vector<String> questions = new Vector<String>();
	private Vector<String> answers = new Vector<String>();
	
	public ChatbotQADataType(){}
	
	public ChatbotQADataType(String question) throws SQLException, ClassNotFoundException{
		DatabaseFunctions db = new DatabaseFunctions();
		db.addQuestion("kevin", question);
		questions.add(question);
	}
	
	public ChatbotQADataType(String question, String answer) throws SQLException, ClassNotFoundException{
		questions.add(question);
		answers.add(answer);
		
		//DatabaseFunctions db = new DatabaseFunctions();
		//db.addQuestion("kevin", question);
		//db = new DatabaseFunctions();
		//db.addAnswer("kevin", question, answer);
	}
	
	public ChatbotQADataType(Vector<String> questions, Vector<String> answers){
		this.questions = questions;
		this.answers = answers;
	}
	
	public void addQuestion(String question) throws SQLException, ClassNotFoundException{
		DatabaseFunctions db = new DatabaseFunctions();
		for(int i=0; i<answers.size(); i++)
		{
			db = new DatabaseFunctions();
			db.addAnswer("kevin", question, answers.get(i));
		}
		db = new DatabaseFunctions();
		db.addQuestion("kevin", question);
		db = new DatabaseFunctions();
		if (questions.size() != 0)
		{
		db.addAfter(questions.lastElement(), question);
		}
		questions.add(question);
		
	}
	
	public void removeQuestion (int index){
		questions.remove(index);
	}
	
	public void addAnswer(String answer) throws ClassNotFoundException, SQLException{
		DatabaseFunctions db = new DatabaseFunctions();
		for(int i=0; i<questions.size(); i++)
		{
			db = new DatabaseFunctions();
			db.addAnswer("kevin", questions.get(i), answer);
		}
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
