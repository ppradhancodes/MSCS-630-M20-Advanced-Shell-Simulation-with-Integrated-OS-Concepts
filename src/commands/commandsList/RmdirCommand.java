package commands.commandsList;

import commands.AbstractCommand;
import manager.JobManager;
import shell.ShellState;

import java.io.File;
import java.io.IOException;

public class RmdirCommand extends AbstractCommand {
    public RmdirCommand(ShellState state, JobManager jobManager) {
        super(state, jobManager);
    }

    @Override
    public void execute(String args) throws IOException {
        if (args.isEmpty()) {
            System.out.println("Usage: rmdir <directory>");
            return;
        }

        File dir = new File(state.getCurrentDirectory(), args);
        if (!dir.exists() || !dir.isDirectory()) {
            throw new IOException("Directory not found: " + args);
        }

        if (dir.list().length > 0) {
            throw new IOException("Directory not empty: " + args);
        }

        if (!dir.delete()) {
            throw new IOException("Failed to remove directory: " + args);
        }
    }
}
