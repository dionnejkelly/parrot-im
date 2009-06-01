
public class main {
	public static void main (String [] args){
		model m = new model("this is test", 1);
		GUI g = new GUI (m);
		core c = new core (m, g);
	}
}
