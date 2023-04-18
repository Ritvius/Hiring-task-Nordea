package nordeaFX;
import java.util.Scanner;

/**
 * A simple console-based user interface for Nordea FX.
 * Created as part of a hiring assignment/test.
 * @author Milton Ritzing
 *
 */

public class FXGraphics {
	public String[] currencyArr;
	public String[][] currencyPairs;
	public String buy;
	public String sell;
	
	/**
	 * Constructor which asks the user for supported currency pairs, what currency the user wishes
	 * to buy and what currency the user wishes to sell.
	 */
	public FXGraphics() {
		
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Kindly enter a string of supported currency pairs (e.g. EUR/USD, AUD/USD, NZD/USD, DKK/EUR), "
        		+ "where \nall pairs are separated by commas and currencies within a pair are separated by a forward slash.");
        //When accepting this input, 
        currencyArr = scan.nextLine().replaceAll("[\\[\\](){} ]","").toUpperCase().split(",");
        currencyPairs = new String[currencyArr.length][2];

        System.out.println("What currency would you like to buy (e.g AUD)?");
        buy = scan.nextLine().toUpperCase();
        
        System.out.println("What currency would you like to sell (e.g USD)?");
        sell = scan.nextLine().toUpperCase();		
        
        scan.close();
        
      //Making it so currency_pairs is now a matrix where each column consists of a pair (two rows).
  		for (int i = 0; i < currencyArr.length; i++) {
  			String[] temp = currencyArr[i].split("/");
  			currencyPairs[i][0] = temp[0];
  			currencyPairs[i][1] = temp[1];
  		}
      		
	}
}