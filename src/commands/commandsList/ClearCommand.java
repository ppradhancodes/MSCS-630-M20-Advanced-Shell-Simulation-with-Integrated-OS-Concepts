package commands.commandsList;

import commands.AbstractCommand;
import manager.JobManager;
import shell.ShellState;

public class ClearCommand extends AbstractCommand {
    public ClearCommand(ShellState state, JobManager jobManager) {
        super(state, jobManager);
    }

    @Override
    public void execute(String args) {
        // Try to clear console based on OS
        try {
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Fallback - print several new lines
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
}