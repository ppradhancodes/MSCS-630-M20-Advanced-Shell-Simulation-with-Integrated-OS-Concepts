package commands.commandsList;

import commands.AbstractCommand;
import commands.BackgroundableCommand;
import manager.Job;
import manager.JobManager;
import shell.ShellState;

import java.io.IOException;

public class SleepCommand extends AbstractCommand implements BackgroundableCommand {
    public SleepCommand(ShellState state, JobManager jobManager) {
        super(state, jobManager);
    }

    @Override
    public void execute(String args) throws IOException {
        try {
            if (args.isEmpty()) {
                System.out.println("Usage: sleep <seconds>");
                return;
            }

            int seconds = Integer.parseInt(args.trim());
            if (seconds < 0) {
                System.out.println("Sleep time cannot be negative");
                return;
            }

            System.out.println("Sleeping for " + seconds + " seconds...");
            Thread.sleep(seconds * 1000);
            System.out.println("Sleep completed");
        } catch (NumberFormatException e) {
            System.out.println("Invalid sleep time: " + args);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Sleep interrupted");
        }
    }

    @Override
    public void executeInBackground(String args, int jobId) {
        Thread thread = new Thread(() -> {
            try {
                // Parse sleep time
                if (args.isEmpty()) {
                    System.out.println("[" + jobId + "] Usage: sleep <seconds>");
                    jobManager.updateJobStatus(jobId, Job.JobStatus.COMPLETED);
                    return;
                }

                int seconds = Integer.parseInt(args.trim());
                if (seconds < 0) {
                    System.out.println("[" + jobId + "] Sleep time cannot be negative");
                    jobManager.updateJobStatus(jobId, Job.JobStatus.COMPLETED);
                    return;
                }

                // Store thread in job for possible interruption
                Job job = jobManager.getJob(jobId);
                if (job != null) {
                    job.setThread(Thread.currentThread());
                }

                // Sleep for specified time
                Thread.sleep(seconds * 1000);

                System.out.println("[" + jobId + "] Sleep completed");
                jobManager.updateJobStatus(jobId, Job.JobStatus.COMPLETED);
            } catch (NumberFormatException e) {
                System.out.println("[" + jobId + "] Invalid sleep time: " + args);
                jobManager.updateJobStatus(jobId, Job.JobStatus.COMPLETED);
            } catch (InterruptedException e) {
                System.out.println("[" + jobId + "] Sleep interrupted");
                jobManager.updateJobStatus(jobId, Job.JobStatus.STOPPED);
            }
        });
        thread.start();

        System.out.println("[" + jobId + "] Started sleep " + args + " in background");
    }
}
