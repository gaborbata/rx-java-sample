package playground.command;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import static playground.command.ParallelStreamCommand.THREAD_NUMBER;

public class CompletableFutureCommand extends ParallelStreamCommand {

    @Override
    protected void executeInternal(Consumer consumer) throws Exception {
        ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(THREAD_NUMBER);
        List<Integer> numbers = getNumbers();
        List<CompletableFuture<String>> futures = numbers.stream()
                .map(number -> CompletableFuture.supplyAsync(() -> slowMappingFunction(number), threadPoolExecutor))
                .collect(Collectors.toList());
        String values = futures.stream().map(CompletableFuture::join).collect(Collectors.joining(","));
        threadPoolExecutor.shutdown();
        consumer.accept(values);
    }

    @Override
    protected String getName() {
        return "CompletableFuture";
    }
}
