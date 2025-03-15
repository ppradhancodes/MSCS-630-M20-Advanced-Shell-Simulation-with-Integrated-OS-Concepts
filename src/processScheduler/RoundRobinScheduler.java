package processScheduler;

import java.util.LinkedList;
import java.util.Queue;

public class RoundRobinScheduler implements  ProcessScheduler{


    private final Queue<CustomProcess> processQueue;
    private final int timeQuantum;
    private boolean running;
    private Thread schedulerThread;
    private int clock;

    public RoundRobinScheduler(int timeQuantum) {
        this.processQueue = new LinkedList<>();
        this.timeQuantum = timeQuantum;
        this.running = false;
        this.clock = 0;
    }

    @Override
    public void addProcess(CustomProcess process) {
        processQueue.add(process);
        System.out.println("Added process " + process.getId() + " (" + process.getName() + ") to Round Robin scheduler");

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
            System.out.println("Starting Round Robin scheduler with time quantum: " + timeQuantum);

            while (running && !processQueue.isEmpty()) {
                CustomProcess currentProcess = processQueue.poll();
                currentProcess.setState(CustomProcess.ProcessState.RUNNING);

                System.out.println("\nClock: " + clock);
                System.out.println("Scheduled: " + currentProcess);

                boolean completed = currentProcess.execute(timeQuantum);
                clock += Math.min(timeQuantum, currentProcess.getBurstTime() - currentProcess.getRemainingTime());

                if (!completed && running) {
                    currentProcess.setState(CustomProcess.ProcessState.READY);
                    processQueue.add(currentProcess); // Re-add to queue if not completed
                }

                // Update waiting time for all processes in queue
                for (CustomProcess p : processQueue) {
                    p.setWaitingTime(p.getWaitingTime() + timeQuantum);
                }

                // Small delay to make output readable
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            if (running) {
                System.out.println("\nAll processes completed in Round Robin scheduler.");
                running = false;
            } else {
                System.out.println("\nRound Robin scheduler stopped.");
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
        System.out.println("Round Robin scheduler stopped.");
    }

    @Override
    public String getStatus() {
        StringBuilder status = new StringBuilder("Round Robin Scheduler (Time Quantum: " + timeQuantum + ")\n");
        status.append("Clock: ").append(clock).append("\n");
        status.append("Status: ").append(running ? "Running" : "Stopped").append("\n");

        if (processQueue.isEmpty()) {
            status.append("No processes in queue.\n");
        } else {
            status.append("Processes in queue:\n");
            for (CustomProcess p : processQueue) {
                status.append("  ").append(p).append("\n");
            }
        }

        return status.toString();
    }

    @Override
    public String getName() {
        return "Round Robin";
    }


}
