package model;

public class Model 
{
    private static final String INITIAL_TEXT = "<Messages appear below>";
    
    private String[] yourName;
    private String previousText;  
    
    Model() 
    {
       yourName = new String[2];
       yourName[0] = "cmpt275account";
       yourName[1] = "someone?";
       reset();
    }
    
    public void reset() 
    {
       previousText = INITIAL_TEXT;
    }
    
    public void setYourName(String name)
    {
       yourName[0] = name;
    }
    
    public String getYourName()
    {
       return yourName[0];
    }

    public void setTheirName(String name)
    {
       yourName[1] = name;
    }
    
    public String getTheirName()
    {
       return yourName[1];
    }

    
    public void setPreviousText(String text) 
    {
       previousText = text;
    }
    
    public String getPreviousText() 
    {
       return previousText; 
    }

    public void addMessage(int who, String text)
    {
       previousText += "<U>" + yourName[who] + ":</U> " + text + "<br><br>";
    }
}
