package view.options.modelstub;

import java.sql.SQLException;
import java.util.Vector;
import model.DatabaseFunctions;

public class model {
	private Vector<ChatbotQADataType> QAs = new Vector<ChatbotQADataType> ();
	
	
	public model() throws ClassNotFoundException, SQLException{
		Vector<String> questionList = new Vector<String>();
		DatabaseFunctions db = new DatabaseFunctions();
		questionList = db.getQuestionList();
		for(int i=0; i<questionList.size(); i++)
		{
			QAs.add(new ChatbotQADataType(questionList.get(i), "bleh"));
			
		}
		QAs.add(new ChatbotQADataType("Am I dumb?","Yes, you are."));
		QAs.add(new ChatbotQADataType("Hello?","Yes?"));
		QAs.add(new ChatbotQADataType("Where is the world?","Somewhere~~ Out there~~~"));
	}
	
	public void addQA(ChatbotQADataType newQA) throws ClassNotFoundException, SQLException{
		DatabaseFunctions db = new DatabaseFunctions();
		db.addQuestion("kevin", newQA.getQuestions().get(0));
		QAs.add(newQA);
	}
	
	public void removeQA(int index){
		QAs.remove(index);
	}
	
	public Vector<String> getQAList(){
		Vector<String> QAList = new Vector<String>();
		for (int i=0; i<QAs.size(); i++){
			QAList.add(QAs.get(i).toString());
		}
		return QAList;
	}
	
	public ChatbotQADataType getQAObject(int index){
		return QAs.get(index);
	}
	public int getQASize(){
		return QAs.size();
	}
}
