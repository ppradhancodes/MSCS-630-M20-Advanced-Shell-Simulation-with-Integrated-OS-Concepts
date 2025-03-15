package commands.commandsList;

import commands.AbstractCommand;
import manager.JobManager;
import shell.ShellState;

import java.io.File;
import java.io.IOException;

public class CdCommand extends AbstractCommand {

    public CdCommand(ShellState state, JobManager jobManager) {
        super(state, jobManager);
    }

    @Override
    public void execute(String args) throws IOException {
        if (args.isEmpty()) {
            // Change to home directory if no arguments
            state.setCurrentDirectory(new File(System.getProperty("user.home")));
        } else {
            File newDir;
            if (args.startsWith("/")) {
                // Absolute path
                newDir = new File(args);
            } else {
                // Relative path
                newDir = new File(state.getCurrentDirectory(), args);
            }

            state.setCurrentDirectory(newDir);
        }
    }
}
