package commands.commandsList;

import commands.AbstractCommand;
import manager.JobManager;
import shell.ShellState;

public class EchoCommand extends AbstractCommand {
    public EchoCommand(ShellState state, JobManager jobManager) {
        super(state, jobManager);
    }

    @Override
    public void execute(String args) {
        System.out.println(args);
    }
}
