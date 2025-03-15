package processScheduler;

// PriorityScheduler implementation using a priority queue

import java.util.PriorityQueue;

public class PriorityScheduler implements ProcessScheduler {
    private final PriorityQueue<CustomProcess> processQueue;
    private boolean running;
    private Thread schedulerThread;
    private CustomProcess currentProcess;
    private int clock;

    public PriorityScheduler() {
        // Lower number = higher priority
        this.processQueue = new PriorityQueue<>((p1, p2) -> {
            if (p1.getPriority() != p2.getPriority()) {
                return Integer.compare(p1.getPriority(), p2.getPriority());
            }
            return Integer.compare(p1.getId(), p2.getId()); // FCFS for same priority
        });
        this.running = false;
        this.currentProcess = null;
        this.clock = 0;
    }

    @Override
    public void addProcess(CustomProcess process) {
        processQueue.add(process);
        System.out.println("Added process " + process.getId() + " (" + process.getName() + ") with priority " + process.getPriority() + " to Priority scheduler");

        // Check for preemption if scheduler is running
        if (running && currentProcess != null &&
                process.getPriority() < currentProcess.getPriority()) {
            System.out.println("Preempting current process due to higher priority arrival.");
            if (schedulerThread != null) {
                schedulerThread.interrupt();
            }
        }
    }

    @Override
    public void run() {
        if (running) {
            System.out.println("Scheduler is already running.");
            return;
        }

        if (processQueue.isEmpty()) {
            System.out.println("No processes to schedule.");
            return;
        }

        running = true;

        schedulerThread = new Thread(() -> {
            System.out.println("Starting Priority-based scheduler");

            while (running && !processQueue.isEmpty()) {
                currentProcess = processQueue.poll();
                currentProcess.setState(CustomProcess.ProcessState.RUNNING);

                System.out.println("\nClock: " + clock);
                System.out.println("Scheduled: " + currentProcess);

                boolean completed = currentProcess.execute(currentProcess.getRemainingTime());
                clock += currentProcess.getBurstTime() - currentProcess.getRemainingTime();

                // Update waiting time for all processes in queue
                for (CustomProcess p : processQueue) {
                    p.setWaitingTime(p.getWaitingTime() + (currentProcess.getBurstTime() - currentProcess.getRemainingTime()));
                }

                if (!completed && running) {
                    // This shouldn't happen in non-preemptive priority scheduling
                    // but we'll handle it just in case
                    currentProcess.setState(CustomProcess.ProcessState.READY);
                    processQueue.add(currentProcess);
                }

                currentProcess = null;

                // Small delay to make output readable
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    // Check if we were interrupted for preemption
                    if (running && !processQueue.isEmpty()) {
                        System.out.println("Scheduler preempted, checking for higher priority processes.");
                        continue;
                    }
                    Thread.currentThread().interrupt();
                }
            }

            if (running) {
                System.out.println("\nAll processes completed in Priority scheduler.");
                running = false;
            } else {
                System.out.println("\nPriority scheduler stopped.");
            }
        });

        schedulerThread.start();
    }

    @Override
    public void stop() {
        running = false;
        if (schedulerThread != null && schedulerThread.isAlive()) {
            schedulerThread.interrupt();
            try {
                schedulerThread.join(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Priority scheduler stopped.");
    }

    @Override
    public String getStatus() {
        StringBuilder status = new StringBuilder("Priority-Based Scheduler\n");
        status.append("Clock: ").append(clock).append("\n");
        status.append("Status: ").append(running ? "Running" : "Stopped").append("\n");

        if (currentProcess != null) {
            status.append("Currently executing: ").append(currentProcess).append("\n");
        }

        if (processQueue.isEmpty()) {
            status.append("No processes in queue.\n");
        } else {
            status.append("Processes in queue (ordered by priority):\n");
            // We need to clone the queue to avoid disturbing it
            PriorityQueue<CustomProcess> tempQueue = new PriorityQueue<>(processQueue);
            while (!tempQueue.isEmpty()) {
                status.append("  ").append(tempQueue.poll()).append("\n");
            }
        }

        return status.toString();
    }

    @Override
    public String getName() {
        return "Priority-Based";
    }
}
