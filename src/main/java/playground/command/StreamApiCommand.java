package playground.command;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class StreamApiCommand extends ParallelStreamCommand {

    @Override
    protected void executeInternal(Consumer consumer) throws Exception {
        List<Integer> numbers = getNumbers();
        ForkJoinPool threadPool = new ForkJoinPool(THREAD_NUMBER);
        String values = threadPool.submit(() -> numbers.parallelStream().map(this::slowMappingFunction).collect(Collectors.joining(","))).get();
        consumer.accept(values);
    }

    @Override
    protected String getName() {
        return "Stream";
    }
}
