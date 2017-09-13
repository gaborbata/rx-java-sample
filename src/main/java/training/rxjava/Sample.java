package training.rxjava;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import training.rxjava.domain.StockInfo;
import training.rxjava.service.StockService;

/**
 * Code sample based on Venkat Subramaniam's reactive presentation.
 *
 * @see <a href="https://www.youtube.com/watch?v=f3acAsSZPhU">Reactive Programming in Java by Venkat Subramaniam</a>
 */
public class Sample {
    private static final Logger LOG = LoggerFactory.getLogger(Sample.class);

    public static void main(String[] args) {
        List<String> stockSymbols = Arrays.asList("GOOG", "AMZN", "ITC");

        Observable<StockInfo> feed = StockService.getFeed(stockSymbols);
        LOG.debug("Got observable...");

        feed.retry()
                .filter(stockInfo -> stockInfo.getValue() > 500.0)
                .map(stockInfo -> new StockInfo(stockInfo.getTicker(), stockInfo.getValue() * 0.9))
                .subscribe(
                        stockInfo -> LOG.info(stockInfo.toString()),
                        error -> LOG.warn(error.getMessage()),
                        () -> LOG.info("DONE")
                );
    }
}
