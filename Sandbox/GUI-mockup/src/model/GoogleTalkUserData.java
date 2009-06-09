package model;

public class GoogleTalkUserData extends UserData {
    private boolean online;
	
	public GoogleTalkUserData(String accountName, String nickname, 
			                  String status) {
		super(accountName, nickname, status);
	}
	
	public GoogleTalkUserData(String accountName) {
	    super(accountName);
	}

	public void setOnline(boolean online) {
		this.online = online;
		return;
	}

	public boolean isOnline() {
		return online;
	}
}
