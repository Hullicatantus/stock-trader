package stockTrader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// TODO
class TraderTest {

    private FileLogger fileLogger;
    private RemoteURLReader remoteURLReader;
    private StockAPIService stockAPIService;
    private Trader trader;


    @BeforeEach
    void setup() throws IOException {
        fileLogger = mock(FileLogger.class);
        remoteURLReader = mock(RemoteURLReader.class);
        stockAPIService = mock(StockAPIService.class);
        stockAPIService.setRemoteURLReader(remoteURLReader);

        trader = new Trader(fileLogger, stockAPIService);

        when(stockAPIService.getPrice("aapl")).thenReturn((double) 250);
    }

    @Test // Bid was lower than price, buy should return false.
    void testBidLowerThanPrice() throws IOException {

        assertFalse(trader.buy("aapl", 200));
    }

    @Test // bid was equal or higher than price, buy() should return true.
    void testBidHigherThanPrice() throws IOException {

        assertTrue(trader.buy("aapl", 300));
    }
}