package processScheduler;

public interface ProcessScheduler {
    void addProcess(CustomProcess customProcess);
    void run();
    void stop();
    String getStatus();
    String getName();
}
