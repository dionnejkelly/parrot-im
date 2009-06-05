import ChatClient.ChatClient;
import buddylist.buddylist;
import chatwindow.chatwindow;
import mainwindow.mainwindow;
import chatwindow.chatwindow;

public class main {

	public static void main(String[] args) {
		ChatClient c = new ChatClient();
		mainwindow m = new mainwindow(c);
		//buddylist b = new buddylist();
		//chatwindow chat = new chatwindow();
	}
}
