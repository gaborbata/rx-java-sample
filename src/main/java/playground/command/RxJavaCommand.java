package playground.command;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import java.util.StringJoiner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class RxJavaCommand extends StreamCommand {

    @Override
    protected void executeInternal(Consumer consumer) throws Exception {
        ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(THREAD_NUMBER);
        Scheduler scheduler = Schedulers.from(threadPoolExecutor);

        Observable<Integer> numbers = Observable.fromIterable(getNumbers());
        String values = numbers.flatMap(number -> Observable.just(number).subscribeOn(scheduler).map(this::slowMappingFunction))
                .serialize()
                .collect(() -> new StringJoiner(","), (joiner, text) -> joiner.add(text))
                .blockingGet().toString();
        threadPoolExecutor.shutdown();
        consumer.accept(values);
    }

    @Override
    protected String getName() {
        return "RxJava";
    }
}
