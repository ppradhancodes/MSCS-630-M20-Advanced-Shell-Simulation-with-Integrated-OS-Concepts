package commands.commandsList;

import commands.AbstractCommand;
import manager.JobManager;
import shell.ShellState;

import java.io.File;
import java.io.IOException;

public class TouchCommand extends AbstractCommand {
    public TouchCommand(ShellState state, JobManager jobManager) {
        super(state, jobManager);
    }

    @Override
    public void execute(String args) throws IOException {
        if (args.isEmpty()) {
            System.out.println("Usage: touch <filename>");
            return;
        }

        File file = new File(state.getCurrentDirectory(), args);
        if (file.exists()) {
            // Update timestamp
            if (!file.setLastModified(System.currentTimeMillis())) {
                throw new IOException("Failed to update timestamp: " + args);
            }
        } else {
            // Create new file
            if (!file.createNewFile()) {
                throw new IOException("Failed to create file: " + args);
            }
        }
    }
}
