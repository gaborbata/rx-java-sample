package playground.command;

import java.util.function.Consumer;
import java.util.stream.Collectors;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class ReactorCommand extends ParallelStreamCommand {

    @Override
    protected void executeInternal(Consumer consumer) throws Exception {
        Flux<Integer> numbers = Flux.fromIterable(getNumbers());

        String values = numbers.parallel(THREAD_NUMBER)
                .runOn(Schedulers.parallel())
                .map(this::slowMappingFunction)
                .sequential()
                .collect(Collectors.joining(","))
                .block();
        consumer.accept(values);
    }

    @Override
    protected String getName() {
        return "Reactor";
    }
}
