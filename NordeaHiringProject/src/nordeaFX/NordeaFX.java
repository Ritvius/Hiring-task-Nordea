package nordeaFX;


/**
 * A simple console-based user interface for Nordea FX.
 * Created as part of a hiring assignment/test.
 * @author Milton Ritzing
 *
 */
public class NordeaFX {
	
	public static void main(String[] args) {
		FXHandler handler = new FXHandler();
		System.out.println(handler.nbrOfExchanges());
	}

}
