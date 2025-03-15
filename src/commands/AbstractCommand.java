package commands;

import manager.JobManager;
import shell.ShellState;

public abstract class AbstractCommand implements Command {

     protected final ShellState state;
     protected final JobManager jobManager;

     public AbstractCommand(ShellState state, JobManager jobManager) {
         this.state = state;
         this.jobManager = jobManager;
     }
}
