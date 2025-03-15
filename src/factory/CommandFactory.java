package factory;

import commands.commandsList.*;
import commands.Command;
import manager.JobManager;
import shell.ShellState;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CommandFactory {
    private final Map<String, Supplier<Command>> commandRegistry;

    public CommandFactory() {
        commandRegistry = new HashMap<>();
    }

    public Command getCommand(String name, ShellState state, JobManager jobManager) {
        switch (name.toLowerCase()) {
            case "cd": return new CdCommand(state, jobManager);
            case "pwd": return new PwdCommand(state, jobManager);
            case "exit": return new ExitCommand(state, jobManager);
            case "echo": return new EchoCommand(state, jobManager);
            case "clear": return new ClearCommand(state, jobManager);
            case "ls": return new LsCommand(state, jobManager);
            case "cat": return new CatCommand(state, jobManager);
            case "mkdir": return new MkdirCommand(state, jobManager);
            case "rmdir": return new RmdirCommand(state, jobManager);
            case "rm": return new RmCommand(state, jobManager);
            case "touch": return new TouchCommand(state, jobManager);
            case "kill": return new KillCommand(state, jobManager);
            case "jobs": return new JobsCommand(state, jobManager);
            case "fg": return new FgCommand(state, jobManager);
            case "bg": return new BgCommand(state, jobManager);
            case "sleep": return new SleepCommand(state, jobManager);
            default: return null;
        }
    }
}
