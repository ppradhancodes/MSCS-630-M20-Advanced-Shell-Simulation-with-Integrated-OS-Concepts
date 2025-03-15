package commands.commandsList;


import commands.AbstractCommand;
import commands.BackgroundableCommand;
import manager.Job;
import manager.JobManager;
import shell.ShellState;

import java.io.File;

public class LsCommand extends AbstractCommand implements BackgroundableCommand {
    public LsCommand(ShellState state, JobManager jobManager) {
        super(state, jobManager);
    }

    @Override
    public void execute(String args) {
        File dir = state.getCurrentDirectory();
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                String name = file.getName();
                String indicator = file.isDirectory() ? "/" : "";
                System.out.println(name + indicator);
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
