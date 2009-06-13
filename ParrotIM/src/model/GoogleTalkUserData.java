package model;

public class GoogleTalkUserData extends UserData {
    private boolean online;
	
    public GoogleTalkUserData(String accountName, String nickname, 
                              String status, AccountData friendOf) {
       super(accountName, nickname, status, friendOf);
    }
	
    public GoogleTalkUserData(String accountName, AccountData friendOf) {
        super(accountName, friendOf);
    }

    public void setOnline(boolean online) {
        this.online = online;
        return;
    }

    public boolean isOnline() {
        return online;
    }
}
