package model.dataType.tempData;

public class ChatLogMessageTempData {
    private String time;
    private String from;
    private String to;
    private String text;
    
    public ChatLogMessageTempData(String time, String from, String to,
            String text) {
        super();
        this.time = time;
        this.from = from;
        this.to = to;
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;      
        return;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
        return;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
        return;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        return;
    }
}
