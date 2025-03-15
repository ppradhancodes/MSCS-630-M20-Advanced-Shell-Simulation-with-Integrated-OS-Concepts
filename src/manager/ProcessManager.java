package manager;

import processScheduler.CustomProcess;
import processScheduler.PriorityScheduler;
import processScheduler.ProcessScheduler;
import processScheduler.RoundRobinScheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * ProcessManager to manage a single scheduler at a time
 */
public class ProcessManager {
    private ProcessScheduler activeScheduler;
    private int nextProcessId;
    private boolean isRunning;

    public ProcessManager() {
        this.activeScheduler = null;
        this.nextProcessId = 1;
        this.isRunning = false;
    }

    /**
     * Set the active scheduler
     * @param scheduler The scheduler to activate
     */
    public void setScheduler(ProcessScheduler scheduler) {
        if (activeScheduler != null && isRunning) {
            activeScheduler.stop();
            isRunning = false;
        }

        activeScheduler = scheduler;
        System.out.println("Set active scheduler to: " + activeScheduler.getName());
    }

    /**
     * Generate a set of random processes for simulation
     * @param count Number of processes to generate
     */
    public void generateRandomProcesses(int count) {
        if (activeScheduler == null) {
            System.out.println("No active scheduler. Please set a scheduler first.");
            return;
        }

        Random random = new Random();
        String[] processNames = {"Browser", "Editor", "Calculator", "FileManager", "Terminal",
                "VideoPlayer", "MusicPlayer", "GameEngine", "Compiler", "Downloader"};

        System.out.println("Generating " + count + " random processes...");

        for (int i = 0; i < count; i++) {
            String name = processNames[random.nextInt(processNames.length)] + "-" + nextProcessId;
            int priority = random.nextInt(10) + 1; // Priority 1-10 (1 is highest)
            int burstTime = random.nextInt(10) + 1; // Burst time 1-10

            CustomProcess process = new CustomProcess(nextProcessId++, name, priority, burstTime);
            activeScheduler.addProcess(process);
        }
    }

    /**
     * Add a custom process to the active scheduler
     * @param name Process name
     * @param priority Process priority
     * @param burstTime Process burst time
     * @return The created process
     */
    public CustomProcess addProcess(String name, int priority, int burstTime) {
        if (activeScheduler == null) {
            System.out.println("No active scheduler. Please set a scheduler first.");
            return null;
        }

        CustomProcess process = new CustomProcess(nextProcessId++, name, priority, burstTime);
        activeScheduler.addProcess(process);
        System.out.println("Added process: " + process);
        return process;
    }

    /**
     * Create and set a new Round Robin scheduler
     * @param timeQuantum Time quantum for the scheduler
     */
    public void createRoundRobinScheduler(int timeQuantum) {
        if (activeScheduler != null && isRunning) {
            activeScheduler.stop();
            isRunning = false;
        }

        activeScheduler = new RoundRobinScheduler(timeQuantum);
        System.out.println("Created and set Round Robin scheduler with time quantum: " + timeQuantum);
    }

    /**
     * Create and set a new Priority scheduler
     */
    public void createPriorityScheduler() {
        if (activeScheduler != null && isRunning) {
            activeScheduler.stop();
            isRunning = false;
        }

        activeScheduler = new PriorityScheduler();
        System.out.println("Created and set Priority scheduler");
    }

    /**
     * Start the active scheduler
     */
    public void startScheduler() {
        if (activeScheduler == null) {
            System.out.println("No active scheduler. Please set a scheduler first.");
            return;
        }

        if (isRunning) {
            System.out.println("Scheduler is already running.");
            return;
        }

        activeScheduler.run();
        isRunning = true;
        System.out.println("Started scheduler: " + activeScheduler.getName());
    }

    /**
     * Stop the active scheduler
     */
    public void stopScheduler() {
        if (activeScheduler == null) {
            System.out.println("No active scheduler is running.");
            return;
        }

        if (!isRunning) {
            System.out.println("Scheduler is not running.");
            return;
        }

        activeScheduler.stop();
        isRunning = false;
        System.out.println("Stopped scheduler: " + activeScheduler.getName());
    }

    /**
     * Get the status of the active scheduler
     * @return Status information of the active scheduler
     */
    public String getSchedulerStatus() {
        if (activeScheduler == null) {
            return "No active scheduler";
        }

        return activeScheduler.getStatus();
    }

    /**
     * Get the active scheduler
     * @return The active scheduler instance
     */
    public ProcessScheduler getActiveScheduler() {
        return activeScheduler;
    }

    /**
     * Check if a scheduler is currently running
     * @return true if a scheduler is running, false otherwise
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Restart the active scheduler (stop and start again)
     */
    public void restartScheduler() {
        if (activeScheduler == null) {
            System.out.println("No active scheduler. Please set a scheduler first.");
            return;
        }

        if (isRunning) {
            stopScheduler();
        }

        startScheduler();
        System.out.println("Restarted scheduler: " + activeScheduler.getName());
    }
}