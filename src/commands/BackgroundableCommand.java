package commands;

// Interface for commands that can run in background
public interface BackgroundableCommand extends Command {
    void executeInBackground(String args, int jobId);
}
