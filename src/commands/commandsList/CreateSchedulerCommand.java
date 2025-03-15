package commands.commandsList;

import commands.AbstractCommand;
import manager.JobManager;
import manager.ProcessManager;
import shell.ShellState;

import java.io.IOException;

public class CreateSchedulerCommand extends AbstractCommand {
    private final ProcessManager processManager;
    private final int DEFAULT_TIME_QUANTUM = 2; // Default time quantum for Round Robin

    public CreateSchedulerCommand(ShellState state, JobManager jobManager, ProcessManager processManager) {
        super(state, jobManager);
        this.processManager = processManager;
    }

    @Override
    public void execute(String args) throws IOException {
        System.out.println("given args " + args);
        // Handle empty args
        if (args == null || args.trim().isEmpty()) {
            System.out.println("Please specify a scheduler type: roundrobin or priorityqueue");
            return;
        }

        // Get the input as lowercase and trimmed
        String input = args.trim().toLowerCase();

        // Handle the only two possible inputs
        if (input.equals("roundrobin")) {
            processManager.createRoundRobinScheduler(DEFAULT_TIME_QUANTUM);
            processManager.generateRandomProcesses(2);
            processManager.startScheduler();
            System.out.println("Switched to Round Robin scheduler with time quantum: " + DEFAULT_TIME_QUANTUM);
        }
        else if (input.equals("priorityqueue")) {
            processManager.createPriorityScheduler();
            processManager.generateRandomProcesses(2);
            processManager.startScheduler();
            System.out.println("Switched to Priority Queue scheduler");
        }
        else {
            System.out.println("Unknown scheduler type: " + input);
            System.out.println("Available types: roundrobin, priorityqueue");
        }
    }
}