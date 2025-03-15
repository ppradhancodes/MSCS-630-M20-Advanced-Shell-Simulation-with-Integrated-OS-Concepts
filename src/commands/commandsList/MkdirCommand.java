package commands.commandsList;

import commands.AbstractCommand;
import manager.JobManager;
import shell.ShellState;

import java.io.File;
import java.io.IOException;

public class MkdirCommand extends AbstractCommand {
    public MkdirCommand(ShellState state, JobManager jobManager) {
        super(state, jobManager);
    }

    @Override
    public void execute(String args) throws IOException {
        if (args.isEmpty()) {
            System.out.println("Usage: mkdir <directory>");
            return;
        }

        File newDir = new File(state.getCurrentDirectory(), args);
        if (!newDir.mkdir()) {
            throw new IOException("Failed to create directory: " + args);
        }
    }
}
