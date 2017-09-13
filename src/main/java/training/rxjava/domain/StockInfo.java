package training.rxjava.domain;

import java.io.IOException;

import yahoofinance.YahooFinance;

public class StockInfo {
    private final String ticker;
    private final double value;

    public StockInfo(String ticker, double value) {
        this.ticker = ticker;
        this.value = value;
    }

    public static StockInfo fetch(String ticker) {
        try {
            return new StockInfo(ticker, YahooFinance.get(ticker).getQuote().getPrice().doubleValue());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getTicker() {
        return ticker;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%s : %f", ticker, value);
    }
}
