package manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class JobManager {
    private final Map<Integer, Job> jobs;
    private int nextJobId;

    public JobManager() {
        this.jobs = new HashMap<>();
        this.nextJobId = 1;
    }

    public int createJob(String command, String args) {
        int jobId = nextJobId++;
        jobs.put(jobId, new Job(jobId, command, args));
        return jobId;
    }

    public Job getJob(int jobId) {
        return jobs.get(jobId);
    }

    public Collection<Job> getAllJobs() {
        return jobs.values();
    }

    public void updateJobStatus(int jobId, Job.JobStatus status) {
        Job job = jobs.get(jobId);
        if (job != null) {
            job.setStatus(status);
        }
    }

    public void removeJob(int jobId) {
        jobs.remove(jobId);
    }
}
