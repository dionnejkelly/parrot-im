package view.options.modelstub;

import java.sql.SQLException;
import java.util.Vector;

import model.Model;


public class ChatbotQADataType {
	private Vector<String> questions = new Vector<String>();
	private Vector<String> answers = new Vector<String>();
    private Model model;
	
	public ChatbotQADataType(Model model){this.model = model;}
	
	public ChatbotQADataType(Model model, String question) throws SQLException, ClassNotFoundException{
		this.model = model;
		model.addQuestion(question);
		questions.add(question);
	}
	
	public ChatbotQADataType(Model model, String question, String answer) throws SQLException, ClassNotFoundException{
		this.model = model;
		questions.add(question);
		answers.add(answer);
		
		//DatabaseFunctions db = new DatabaseFunctions();
		//db.addQuestion("kevin", question);
		//db = new DatabaseFunctions();
		//db.addAnswer("kevin", question, answer);
	}
	
	public ChatbotQADataType(Model model, Vector<String> questions, Vector<String> answers){
		this.model = model;
		this.questions = questions;
		this.answers = answers;
	}
	
	public void addQuestion(String question) throws SQLException, ClassNotFoundException{
		for(int i=0; i<answers.size(); i++)
		{
			model.addAnswer(question, answers.get(i));
		}
		model.addQuestion(question);
		if (questions.size() != 0)
		{
		model.addAfter(questions.lastElement(), question);
		}
		questions.add(question);
		
	}
	
	public void removeQuestion (int index) throws SQLException, ClassNotFoundException{
		model.removeChatQuestion(questions.get(index));
		
		questions.remove(index);
	}
	
	public void addAnswer(String answer) throws ClassNotFoundException, SQLException{
		for(int i=0; i<questions.size(); i++)
		{
			model.addAnswer(questions.get(i), answer);
		}
		answers.add(answer);
		
		
	}
	public void removeAllQA() throws ClassNotFoundException, SQLException
	{
		for (int i=0; i<questions.size(); i++) {
			model.removeChatQuestion(questions.get(i));
		}
		
	}
	public void removeAnswer (int index) throws ClassNotFoundException, SQLException{
		for(int i=0; i<questions.size(); i++) {
			model.removeAnswer(questions.get(i), answers.get(index));
		}
		answers.remove(index);
	}
	
	public String toString(){
		String str="";
		int index;
		for (index=0; index<questions.size()-1; index++){
			str += questions.get(index) + ", ";
		}
		if (questions.size() != 0) {
			str += questions.get(index);
		}
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
