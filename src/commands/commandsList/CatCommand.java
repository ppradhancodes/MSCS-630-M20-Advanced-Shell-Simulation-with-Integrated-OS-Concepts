package commands.commandsList;

import commands.AbstractCommand;
import commands.BackgroundableCommand;
import manager.Job;
import manager.JobManager;
import shell.ShellState;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CatCommand extends AbstractCommand implements BackgroundableCommand {
    public CatCommand(ShellState state, JobManager jobManager) {
        super(state, jobManager);
    }

    @Override
    public void execute(String args) throws IOException {
        if (args.isEmpty()) {
            System.out.println("Usage: cat <filename>");
            return;
        }

        File file = new File(state.getCurrentDirectory(), args);
        if (!file.exists() || file.isDirectory()) {
            throw new IOException("File not found: " + args);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    @Override
    public void executeInBackground(String args, int jobId) {
        Thread thread = new Thread(() -> {
            try {
                execute(args);
                jobManager.updateJobStatus(jobId, Job.JobStatus.COMPLETED);
            } catch (Exception e) {
                System.out.println("Background job error: " + e.getMessage());
            }
        });
        thread.start();
    }
}
