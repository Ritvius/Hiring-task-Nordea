package nordeaFX;
import java.util.Scanner;

/**
 * A simple console-based user interface for Nordea FX.
 * Created as part of a hiring assignment/test.
 * @author Milton Ritzing
 *
 */

public class FXGraphics {
	
	/**
	 * Constructor which asks the user for supported currency pairs, what currency the user wishes
	 * to buy and what currency the user wishes to sell.
	 */
	public FXGraphics() {
		String[] currencyArr;
		String buy;
		String sell;
		
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Kindly enter a string of supported currency pairs (e.g. EUR/USD, AUD/USD, NZD/USD, DKK/EUR), "
        		+ "where \nall pairs are separated by commas and currencies within a pair are separated by a forward slash.");
        //When accepting this input, 
        currencyArr = scan.nextLine().replaceAll("[\\[\\](){} ]","").toUpperCase().split(",");

        System.out.println("What currency would you like to buy (e.g AUD)?");
        buy = scan.nextLine().toUpperCase();
        
        System.out.println("What currency would you like to sell (e.g USD)?");
        sell = scan.nextLine().toUpperCase();		
        
        scan.close();
        
        FXHandler handler = new FXHandler(buy, sell, currencyArr);
        
        int nbrExchanges = handler.nbrOfExchanges();
        switch (nbrExchanges) {
        case -1:
	        System.out.println("Cannot convert between sell and buy currencies according to the list of supported currency pairs.");
	        System.out.println(nbrExchanges);
	        break;
        case 0:
        	System.out.println("Buy and sell currencies are the same, no conversions needed.");
	        System.out.println(nbrExchanges);       
	        break;
	    default:
        	System.out.println("Least amount of exchanges needed:");
	        System.out.println(nbrExchanges);  
        }
        

      		
	}
}