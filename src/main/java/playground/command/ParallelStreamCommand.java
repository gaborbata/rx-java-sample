package playground.command;

import com.google.common.base.Stopwatch;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ParallelStreamCommand {

    private static final Logger LOG = LoggerFactory.getLogger(ParallelStreamCommand.class);

    protected static final int THREAD_SLEEP_MS = 500;
    protected static final int NUMBERS_COUNT = 6;
    protected static final int THREAD_NUMBER = 3;

    public void execute() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            executeInternal((value) -> LOG.info("{}: value: [{}], Elapsed: {}, ", getName(), value, stopwatch.toString()));
        } catch (Exception e) {
            LOG.error("Could not execute command: {}", e.getMessage(), e);
        }
    }

    protected String slowMappingFunction(Integer number) {
        LOG.debug("{}: Doing time consuming task on thread [{}]...", getName(), Thread.currentThread().getName());
        try {
            Thread.sleep(THREAD_SLEEP_MS);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        }
        return String.valueOf(number);
    }

    protected List<Integer> getNumbers() {
        List<Integer> numbers = IntStream.range(0, NUMBERS_COUNT).boxed().collect(Collectors.toList());
        return numbers;
    }

    protected abstract String getName();

    protected abstract void executeInternal(Consumer consumer) throws Exception;
}
