package commands.commandsList;

import commands.AbstractCommand;
import manager.JobManager;
import shell.ShellState;

public class PwdCommand extends AbstractCommand {
    public PwdCommand(ShellState state, JobManager jobManager) {
        super(state, jobManager);
    }

    @Override
    public void execute(String args) {
        System.out.println(state.getCurrentDirectoryPath());
    }
}
