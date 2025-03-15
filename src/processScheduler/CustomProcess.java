package processScheduler;


// Process class to represent a simulated process
public class CustomProcess {
    private final int id;
    private final String name;
    private int priority;
    private int burstTime;  // Total execution time needed
    private int remainingTime;  // Remaining execution time
    private int waitingTime;
    private int turnaroundTime;
    private ProcessState state;

    public enum ProcessState {
        READY, RUNNING, BLOCKED, TERMINATED
    }

    public CustomProcess(int id, String name, int priority, int burstTime) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
        this.state = ProcessState.READY;
    }

    // Getters and setters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }
    public int getBurstTime() { return burstTime; }
    public int getRemainingTime() { return remainingTime; }
    public void setRemainingTime(int remainingTime) { this.remainingTime = remainingTime; }
    public int getWaitingTime() { return waitingTime; }
    public void setWaitingTime(int waitingTime) { this.waitingTime = waitingTime; }
    public int getTurnaroundTime() { return turnaroundTime; }
    public void setTurnaroundTime(int turnaroundTime) { this.turnaroundTime = turnaroundTime; }
    public ProcessState getState() { return state; }
    public void setState(ProcessState state) { this.state = state; }

    // Execute the process for a specified time slice
    public boolean execute(int timeSlice) {
        int timeToExecute = Math.min(timeSlice, remainingTime);

        try {
            System.out.println("Executing process " + id + " (" + name + ") for " + timeToExecute + " time units...");
            Thread.sleep(timeToExecute * 100); // Simulate execution (scaled for demonstration)
            remainingTime -= timeToExecute;
        } catch (InterruptedException e) {
            System.out.println("Process " + id + " execution interrupted.");
            return false;
        }

        if (remainingTime <= 0) {
            state = ProcessState.TERMINATED;
            System.out.println("Process " + id + " (" + name + ") completed execution.");
            return true; // Process completed
        }

        System.out.println("Process " + id + " (" + name + ") suspended. Remaining time: " + remainingTime);
        return false; // Process not completed yet
    }

    @Override
    public String toString() {
        return "Process{id=" + id +
                ", name='" + name + '\'' +
                ", priority=" + priority +
                ", remainingTime=" + remainingTime + "/" + burstTime +
                ", state=" + state + '}';
    }
}
