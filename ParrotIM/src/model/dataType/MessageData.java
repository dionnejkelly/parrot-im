package model.dataType;


public class MessageData {
    private UserData fromUser;
    private String message;
    private String font;
    private String size;
    
    public MessageData(UserData fromUser, String message, String font, String size) {
        this.fromUser = fromUser;
        this.message = message;
        this.font = font;
        this.size = size;
    }
    
    public UserData getFromUser() {
        return this.fromUser;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public String getFont() {
        return this.font;
    }
    
    public String getSize() {
        return this.size;
    }
    
    public String text() {
        String text = "<U>" + this.fromUser + ":</U> " + "<font face=\"" 
                      + this.font + "\" size=\"" + this.size + "\">" 
                      + this.message + "</font><br><br>";
        return text;
    }
    
    public String plainText() {
        String plainText = this.fromUser + ":" + this.message + "\n";
        return plainText;
    }
    
    @Override
	public String toString() {
        return text();
    }
}
