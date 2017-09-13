package training.rxjava.service;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import training.rxjava.domain.StockInfo;

public class StockService {
    private static final Logger LOG = LoggerFactory.getLogger(StockService.class);

    public static Observable<StockInfo> getFeed(List<String> symbols) {
        LOG.debug("Create observable...");
        return Observable.create(emitter -> emitPrice(emitter, symbols));
    }

    private static void emitPrice(ObservableEmitter<StockInfo> emitter, List<String> symbols) {
        LOG.debug("Ready to emit...");
        int count = 0;
        while (count < 5) {
            symbols.stream().map(StockInfo::fetch).forEach(emitter::onNext);
            sleep();
            count++;
            if (Math.random() > 0.8) {
                LOG.warn("Some error happened...");
                emitter.onError(new RuntimeException("Ooops"));
            }
        }

        emitter.onComplete();
    }

    private static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            LOG.warn(e.getMessage());
        }
    }
}
