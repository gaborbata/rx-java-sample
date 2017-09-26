package playground;

import java.util.Arrays;
import java.util.List;
import playground.command.CompletableFutureCommand;
import playground.command.ReactorCommand;
import playground.command.StreamApiCommand;
import playground.command.ParallelStreamCommand;
import playground.command.RxJavaCommand;

/**
 * Class to test different approaches for parallel stream processing.
 */
public class ParallelStreamsMain {

    private static final List<ParallelStreamCommand> COMMANDS = Arrays.asList(
            new StreamApiCommand(),
            new CompletableFutureCommand(),
            new ReactorCommand(),
            new RxJavaCommand());

    public static void main(String[] args) throws Exception {
        COMMANDS.forEach(ParallelStreamCommand::execute);
    }
}
