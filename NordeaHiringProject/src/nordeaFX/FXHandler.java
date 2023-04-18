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
	public String buy;	
	public String sell;
	public Map<String, ArrayList<String>> currencyMap = new HashMap<>();
	public int count = 1; //Count == 0 is tested separately (same currency for both sell and buy).
	
	/**
	 * Constructor initializes FXHandler
	 */
	public FXHandler() {
		FXGraphics graphics = new FXGraphics();
		
		this.buy = graphics.buy;
		this.sell = graphics.sell;
		
		//Creates a HashMap where each currency is its own key, and every key's values
		//are all currencies the key can convert to.
		//(These somewhat strange values of k are used to avoid duplicate code.)
		for (int k = 1; k < 3; k++) {
			for (int i = 0; i < graphics.currencyPairs.length; i++) {
				String key = graphics.currencyPairs[i][k-1];
				
				if (!currencyMap.containsKey(key)) {
					ArrayList<String> values = new ArrayList<>();
					values.add(graphics.currencyPairs[i][k%2]); //If k-1 is 0, k%2 should be 1. If k-1 is 1, k%2 should be 0.
					currencyMap.put(key, values);
					
				} else {
					ArrayList<String> values = currencyMap.get(key);
					values.add(graphics.currencyPairs[i][k%2]); //If k-1 is 0, k%2 should be 1. If k-1 is 1, k%2 should be 0.
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
		if (sell.equals(buy)) {
			System.out.println("Buy and sell currencies are the same, no conversions needed.");
			return 0;
		}
		
		if (!currencyMap.containsKey(sell) || !currencyMap.containsKey(buy)) {
			System.out.println("Sell and/or buy currencies are not in the list of supported currency pairs.");
			return -1;
		}
		ArrayList<String> passedCurrencies = new ArrayList<>();
		passedCurrencies.add(buy);
		ArrayList<String> potentialCurrencies = currencyMap.get(buy);
		//Possibly redundant, but useful in case someone entered e.g. DKK/DKK as a pair.
		potentialCurrencies.removeAll(passedCurrencies);
		
		System.out.println("Number of exchanges required (-1 means no conversion can be made):");
		return function(passedCurrencies, potentialCurrencies);
	}
		
	/**
	 * Private help function which uses recursion in order to find the least amount of 
	 * necessary exchanges.
	 * @param passedCurrencies - the currencies which have already been examined
	 * @param potentialCurrencies - the currencies which the current currency can convert to
	 * @return least amount of necessary exchanges between "buy" and "sell" currencies.
	 */
	private int function(ArrayList<String> passedCurrencies, ArrayList<String> potentialCurrencies) {				
		//Check if we've reached the goal currency (sell).
		if (potentialCurrencies.contains(sell)) {
			return count;
		} else {
			count++;
			//Checking firstly (i == 0) if any potential currencies lead to the goal currency.
			//Secondly (i == 1), checking next currencies recursively.
			for (int i = 0; i < 2; i++) {
				for (String currentCurrency : potentialCurrencies) {
					
					//Updating passedCurrencies and creating newCurrencies, which is
					//a new "potentialCurrencies", consisting of the potential currencies
					//to convert to from currentCurrency.
					ArrayList<String> newCurrencies = currencyMap.get(currentCurrency);
					newCurrencies.removeAll(passedCurrencies);
					
					if (i == 0) {
						if (newCurrencies.contains(sell)) {
							return count;
						}
					} else if (i == 1){
						passedCurrencies.add(currentCurrency);
						int temp = function(passedCurrencies, newCurrencies);
						if (temp != -1) {
							return temp;
						} //else, continue to next currency in potentialCurrencies
					}
				}
			}
		}
		//If no possible conversions, return -1.
		return -1;
	}
}