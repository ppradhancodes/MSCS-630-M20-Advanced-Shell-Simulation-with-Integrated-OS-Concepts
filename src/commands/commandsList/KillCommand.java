package commands.commandsList;

import commands.AbstractCommand;
import manager.JobManager;
import shell.ShellState;

import java.io.IOException;

public class KillCommand extends AbstractCommand {
    public KillCommand(ShellState state, JobManager jobManager) {
        super(state, jobManager);
    }

    @Override
    public void execute(String args) {
        try {
            int pid = Integer.parseInt(args.trim());
            Process process = new ProcessBuilder("kill", String.valueOf(pid)).start();
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                throw new IOException("Failed to kill process: " + pid);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid PID: " + args);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}