import java.sql.SQLException;

import ChatClient.ChatClient;
import buddylist.buddylist;
import chatwindow.chatwindow;
import mainwindow.mainwindow;
import model.Model;

public class main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Model model = new Model();
		ChatClient chatClient = new ChatClient(model);
		mainwindow mainWindow = new mainwindow(chatClient, model);
		//buddylist b = new buddylist();
		//chatwindow chat = new chatwindow();
	}
}
