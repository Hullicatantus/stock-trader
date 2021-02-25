package stockTrader;

import stockTrader.FileLogger;
import stockTrader.StockAPIService;

import java.io.IOException;

/**
 * Business logic for stock trading
 **/
public class Trader {
	private StockAPIService stockAPIService;
	private FileLogger fileLogger;

	Trader(FileLogger fileLogger, StockAPIService stockAPIService) {
		this.stockAPIService = stockAPIService;
		this.fileLogger = fileLogger;
	}

	public void setStockAPIServiceAndLogger(FileLogger fileLogger, StockAPIService stockAPIService) {
		this.stockAPIService = stockAPIService;
		this.fileLogger = fileLogger;
	}


	/** Checks the price of a stock, and buys it if the price is not greater than the bid amount.
	 * 	@return whether any stock was bought */
	boolean buy(String symbol, double bid) throws IOException {
		double price = stockAPIService.getPrice(symbol);

		boolean result;
		if (price <= bid) {
			result = true;
			stockAPIService.buy(symbol);
			fileLogger.log("Purchased " + symbol + " stock at $" + bid + ", since its higher that the current price ($" + price + ")");
		}
		else {
			fileLogger.log("Bid for " + symbol + " was $" + bid + " but the stock price is $" + price + ", no purchase was made.");
			result = false;
		}
		return result;
	}
}