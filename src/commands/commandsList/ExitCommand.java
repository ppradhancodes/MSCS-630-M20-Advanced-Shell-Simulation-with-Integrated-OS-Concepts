package commands.commandsList;

import commands.AbstractCommand;
import manager.JobManager;
import shell.CustomShell;
import shell.ShellState;

import java.lang.reflect.Field;

public class ExitCommand extends AbstractCommand {
    public ExitCommand(ShellState state, JobManager jobManager) {
        super(state, jobManager);
    }

    @Override
    public void execute(String args) {
        System.out.println("Exiting shell...");
        // Access the main shell class through reflection to call exit method
        try {
            Field shellField = AbstractCommand.class.getDeclaredField("shell");
            shellField.setAccessible(true);
            CustomShell shell = (CustomShell) shellField.get(this);
            shell.exit();
        } catch (Exception e) {
            System.exit(0); // Fallback if reflection fails
        }
    }
}
