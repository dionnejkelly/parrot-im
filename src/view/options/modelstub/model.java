package view.options.modelstub;

import java.util.Vector;

public class model {
	private Vector<ChatbotQADataType> QAs = new Vector<ChatbotQADataType> ();
	
	public model(){
		QAs.add(new ChatbotQADataType("Am I dumb?","Yes, you are."));
		QAs.add(new ChatbotQADataType("Hello?","Yes?"));
		QAs.add(new ChatbotQADataType("Where is the world?","Somewhere~~ Out there~~~"));
	}
	
	public void addQA(ChatbotQADataType newQA){
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
