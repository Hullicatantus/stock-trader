package stockTrader;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Provides a command line interface for stock trading.
 **/
public class TradingApp {


	public static void main(String[] args) {
		FileLogger fileLogger = new FileLogger();
		RemoteURLReader remoteURLReader = new RemoteURLReader();
		StockAPIService stockAPIService = new StockAPIService(remoteURLReader);
		Trader trader = new Trader(fileLogger, stockAPIService);

		TradingApp app = new TradingApp();
		app.start(fileLogger, trader);
	}

	void start(FileLogger fileLogger, Trader trader) {
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Enter a stock symbol (for example aapl):");
		String symbol = keyboard.nextLine();
		System.out.println("Enter the maximum price you are willing to pay: ");
		double price;
		try {
			price = keyboard.nextDouble();
		} catch (InputMismatchException e) {
			System.out.println("Invalid input. Enter a number");
			return;
		}

		try {
			boolean purchased = trader.buy(symbol, price);
			if (purchased) {
				fileLogger.log("Purchased stock!");
			}
			else {
				fileLogger.log("Couldn't buy the stock at that price.");
			}
		} catch (Exception e) {
			fileLogger.log("There was an error while attempting to buy the stock: " + e.getMessage());
		}
	}
}
