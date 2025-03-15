package manager;

// Job Management
public class Job {
    private final int id;
    private final String command;
    private final String args;
    private Process process;
    private JobStatus status;
    private Thread thread;

    public enum JobStatus {
        RUNNING, STOPPED, COMPLETED
    }

    public Job(int id, String command, String args) {
        this.id = id;
        this.command = command;
        this.args = args;
        this.status = JobStatus.RUNNING;
    }

    // Getters and setters
    public int getId() { return id; }
    public String getCommand() { return command; }
    public String getArgs() { return args; }
    public Process getProcess() { return process; }
    public void setProcess(Process process) { this.process = process; }
    public JobStatus getStatus() { return status; }
    public void setStatus(JobStatus status) { this.status = status; }
    public Thread getThread() { return thread; }
    public void setThread(Thread thread) { this.thread = thread; }

    @Override
    public String toString() {
        return "[" + id + "] " + status.toString() + " " + command + " " + args;
    }
}
