package view.options.modelstub;

import java.sql.SQLException;
import java.util.Vector;
import model.DatabaseFunctions;
import model.Model;

public class model {
	private Vector<ChatbotQADataType> QAs = new Vector<ChatbotQADataType> ();
    private Model model;
	
	public model() throws ClassNotFoundException, SQLException{
		QAs.clear();
		Vector<String> questionList = new Vector<String>();
		Vector<String> answersList = new Vector<String>();
		Vector<String> doneQuestionsList = new Vector<String>();
		Vector<String> tempQuestionsList = new Vector<String>();
		Vector<String> tempAnswersList = new Vector<String>();
		questionList = model.getQuestionList();
		
		for(int i=0; i<questionList.size(); i++)
		{
			
			tempAnswersList = model.getAnswersList(questionList.get(i));
			tempQuestionsList = model.getAllAfterQuestions(questionList.get(i));
			if (!doneQuestionsList.containsAll(tempQuestionsList))
			{
				QAs.add(new ChatbotQADataType(tempQuestionsList, tempAnswersList));
				doneQuestionsList.addAll(tempQuestionsList);
			}
		}
	}
	
	public void addQA(ChatbotQADataType newQA) throws ClassNotFoundException, SQLException{
		QAs.add(newQA);
	}
	
	public void removeQA(int index) throws ClassNotFoundException, SQLException{
		QAs.get(index).removeAllQA();
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
