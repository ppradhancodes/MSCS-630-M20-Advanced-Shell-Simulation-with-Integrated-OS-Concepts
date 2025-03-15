package commands.commandsList;

import commands.AbstractCommand;
import manager.Job;
import manager.JobManager;
import shell.ShellState;

import java.io.IOException;

public class BgCommand extends AbstractCommand {
    public BgCommand(ShellState state, JobManager jobManager) {
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

            if (job.getStatus() != Job.JobStatus.STOPPED) {
                System.out.println("Job is not stopped: " + jobId);
                return;
            }

            System.out.println("Resuming job in background: " + job);

            // Restart the job
            jobManager.updateJobStatus(jobId, Job.JobStatus.RUNNING);

            // Here you would restart the process if it was a real OS
            System.out.println("[" + jobId + "] " + job.getCommand() + " " + job.getArgs() + " &");
        } catch (NumberFormatException e) {
            System.out.println("Invalid job ID: " + args);
        }
    }
}