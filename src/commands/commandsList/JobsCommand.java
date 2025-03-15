package commands.commandsList;

import commands.AbstractCommand;
import manager.Job;
import manager.JobManager;
import shell.ShellState;

import java.util.Collection;

public class JobsCommand extends AbstractCommand {
    public JobsCommand(ShellState state, JobManager jobManager) {
        super(state, jobManager);
    }

    @Override
    public void execute(String args) {
        Collection<Job> jobs = jobManager.getAllJobs();
        if (jobs.isEmpty()) {
            System.out.println("No active jobs");
            return;
        }

        for (Job job : jobs) {
            System.out.println(job);
        }
    }
}
