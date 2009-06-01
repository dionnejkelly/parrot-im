import java.awt.event.ActionListener;


public class core {
	private model m;
	
	public core (model newm, GUI g){
		g.addMultiplyListener(new ActionListener());
		m = newm;
		setString("and we changed this");
	}
	
	public void setString(String s){
		m.str = s;
	}

}
