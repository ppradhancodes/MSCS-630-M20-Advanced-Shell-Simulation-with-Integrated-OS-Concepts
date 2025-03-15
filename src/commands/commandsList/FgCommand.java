package commands.commandsList;

import commands.AbstractCommand;
import manager.Job;
import manager.JobManager;
import shell.ShellState;

import java.io.IOException;

public class FgCommand extends AbstractCommand {
    public FgCommand(ShellState state, JobManager jobManager) {
        super(state, jobManager);
    }

    @Override
    public void execute(String args) throws IOException {
        try {
            int jobId = Integer.parseInt(args.trim());
            Job job = jobManager.getJob(jobId);

            if (job == null) {
                System.out.println("No such job: " + jobId);
                return;
            }

            if (job.getStatus() == Job.JobStatus.COMPLETED) {
                System.out.println("Job already completed: " + jobId);
                return;
            }

            System.out.println("Bringing job to foreground: " + job);

            // If the job has a process, wait for it to complete
            if (job.getProcess() != null) {
                try {
                    job.getProcess().waitFor();
                    int exitCode = job.getProcess().exitValue();
                    System.out.println("[" + jobId + "] exited with code " + exitCode);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Interrupted while waiting for job: " + jobId);
                }
            }

            jobManager.updateJobStatus(jobId, Job.JobStatus.COMPLETED);
        } catch (NumberFormatException e) {
            System.out.println("Invalid job ID: " + args);
        }
    }
}
