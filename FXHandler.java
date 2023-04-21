package nordeaFX;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 * A simple console-based user interface for Nordea FX.
 * Created as part of a hiring assignment/test.
 * @author Milton Ritzing
 *
 */
public class FXHandler {
	private String buy;	
	private String sell;
	private Map<String, ArrayList<String>> currencyMap = new HashMap<>();
	
	/**
	 * Constructor initializes FXHandler and creates HashMap from user inputed array of currency pairs.
	 */
	public FXHandler(String buy, String sell, String[] currencyArr) {
		
		this.buy = buy;
		this.sell = sell;
		
        String[][] currencyPairs = new String[currencyArr.length][2];

        //Making it so currencyPairs is a matrix where each column consists of a pair (two rows).
    		for (int i = 0; i < currencyArr.length; i++) {
    			String[] temp = currencyArr[i].split("/");
    			if (temp.length != 2) {
    				System.out.print("Please use forward slashes to indicate pairs. Ending program.");
    				System.exit(0);
    			}
    			currencyPairs[i][0] = temp[0];
    			currencyPairs[i][1] = temp[1];
    		}
		
		//Creates a HashMap where each currency is its own key, and every key's values
		//are all currencies the key can convert to.
		//(These somewhat strange values of k are used to avoid duplicate code.)
		for (int k = 1; k < 3; k++) {
			for (int i = 0; i < currencyPairs.length; i++) {
				String key = currencyPairs[i][k-1];
				
				if (!currencyMap.containsKey(key)) {
					ArrayList<String> values = new ArrayList<>();
					values.add(currencyPairs[i][k%2]); //If k-1 is 0, k%2 should be 1. If k-1 is 1, k%2 should be 0.
					currencyMap.put(key, values);
					
				} else {
					ArrayList<String> values = currencyMap.get(key);
					values.add(currencyPairs[i][k%2]); //If k-1 is 0, k%2 should be 1. If k-1 is 1, k%2 should be 0.
					currencyMap.replace(key, values);
				}
			}
		}

	}
	
	/**
	 * Calculates the number of exchanges required in order to convert from "sell"  currency to "buy" currency, 
	 * using user-given currency pairs.
	 * @return number of exchanges required.
	 */
	public int nbrOfExchanges() {	
		//If sell and buy are the same no conversions are needed.
		if (sell.equals(buy)) {
			return 0;
		}
		//If either buy or sell aren't in the list, then there is no need to examine further.
		if (!currencyMap.containsKey(sell) || !currencyMap.containsKey(buy)) {
			return -1;
		}
		
		//Creating array list with passed currencies, including 'buy' currency
		ArrayList<String> passedCurrencies = new ArrayList<>();
		passedCurrencies.add(buy);
		
		//Potential currencies are all currencies connected to the current currency (buy).
		ArrayList<String> potentialCurrencies = currencyMap.get(buy);
		
		//Possibly redundant, but useful in case someone entered e.g. DKK/DKK as a pair.
		potentialCurrencies.removeAll(passedCurrencies);
		
		//If the sell and buy currencies are directly exchangeable.
		if (potentialCurrencies.contains(sell)) {
			return 1;
		}
		
		int count = 1;
		//Beginning to recursively search for least amount of exchanges.
		return function(passedCurrencies, potentialCurrencies, count);
	}
		
	/**
	 * Private help function which uses recursion in order to find the least amount of 
	 * necessary exchanges.
	 * @param passedCurrencies - the currencies which have already been examined
	 * @param potentialCurrencies - the currencies which the current currency can convert to
	 * @param count - the current amount of exchanges needed
	 * @return least amount of necessary exchanges between "buy" and "sell" currencies.
	 */
	private int function(ArrayList<String> passedCurrencies, ArrayList<String> potentialCurrencies, int count) {				
		//Increasing count for each call of this private help function
		count++;
		//Checking firstly if any potential currencies lead to the goal currency.
		for (String currentCurrency : potentialCurrencies) {
			
			//Updating passedCurrencies and creating newCurrencies, which is
			//a new "potentialCurrencies", consisting of the potential currencies
			//to convert to from currentCurrency.
			ArrayList<String> newCurrencies = currencyMap.get(currentCurrency);
			newCurrencies.removeAll(passedCurrencies);
			
			if (newCurrencies.contains(sell)) {
				return count;
			}
		}
		//Secondly, checking next currencies recursively.
		for (String currentCurrency : potentialCurrencies) {
					
			//Updating passedCurrencies and creating newCurrencies, which is
			//a new "potentialCurrencies", consisting of the potential currencies
			//to convert to from currentCurrency.
			ArrayList<String> newCurrencies = currencyMap.get(currentCurrency);
			newCurrencies.removeAll(passedCurrencies);
			
			passedCurrencies.add(currentCurrency);
			
			int temp = function(passedCurrencies, newCurrencies, count);
			if (temp != -1) { //A 'path' between sell and buy is found and we're done.
				return temp;
			} //else, continue to next currency in potentialCurrencies
		}
		//If no possible conversions, return -1.
		//This return will be reached if the list of potential currencies is empty.
		return -1;
	}
}