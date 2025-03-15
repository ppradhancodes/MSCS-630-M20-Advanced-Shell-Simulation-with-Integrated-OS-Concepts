package commands.commandsList;

import commands.AbstractCommand;
import manager.JobManager;
import shell.ShellState;

import java.io.File;
import java.io.IOException;

public class RmCommand extends AbstractCommand {
    public RmCommand(ShellState state, JobManager jobManager) {
        super(state, jobManager);
    }

    @Override
    public void execute(String args) throws IOException {
        if (args.isEmpty()) {
            System.out.println("Usage: rm <filename>");
            return;
        }

        File file = new File(state.getCurrentDirectory(), args);
        if (!file.exists()) {
            throw new IOException("File not found: " + args);
        }

        if (file.isDirectory()) {
            throw new IOException("Cannot remove directory with rm: " + args);
        }

        if (!file.delete()) {
            throw new IOException("Failed to remove file: " + args);
        }
    }
}
