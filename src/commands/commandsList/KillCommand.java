package commands.commandsList;

import commands.AbstractCommand;
import manager.JobManager;
import shell.ShellState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class KillCommand extends AbstractCommand {
    public KillCommand(ShellState state, JobManager jobManager) {
        super(state, jobManager);
    }

    @Override
    public void execute(String args) {
        try {
            int pid = Integer.parseInt(args.trim());

            // Check if the process exists before attempting to kill it
            Process checkProcess = new ProcessBuilder("ps", "-p", String.valueOf(pid)).start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(checkProcess.getInputStream()));
            String line;
            boolean processExists = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains(String.valueOf(pid))) {
                    processExists = true;
                    break;
                }
            }

            if (!processExists) {
                System.out.println("Process with PID " + pid + " does not exist or is already terminated.");
                return;
            }

            // Kill the process
            Process process = new ProcessBuilder("bash", "-c", "kill -9 " + pid).start();
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                throw new IOException("Failed to kill process: " + pid);
            }
            System.out.println("Successfully killed process: " + pid);
        } catch (NumberFormatException e) {
            System.out.println("Invalid PID: " + args);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // Restore the interrupted status
            System.out.println("Error: Process execution interrupted.");
        }
    }
}
