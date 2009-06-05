import ChatClient.ChatClient;
import buddylist.buddylist;
import chatwindow.chatwindow;
import mainwindow.mainwindow;
import chatwindow.chatwindow;
import model.Model;

public class main {

	public static void main(String[] args) {
		Model model = new Model();
		ChatClient chatClient = new ChatClient(model);
		mainwindow mainWindow = new mainwindow(chatClient, model);
		//buddylist b = new buddylist();
		//chatwindow chat = new chatwindow();
	}
}
