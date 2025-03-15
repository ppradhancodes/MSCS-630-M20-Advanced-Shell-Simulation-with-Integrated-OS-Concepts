package shell;

import commands.BackgroundableCommand;
import commands.Command;
import factory.CommandFactory;
import manager.JobManager;

import java.util.Scanner;

public class CustomShell {
    private boolean isRunning = true;
    private static final String PROMPT = "momolover >";
    private final JobManager jobManager;
    private final CommandFactory commandFactory;
    private ShellState state;

    public CustomShell(){
        this.jobManager = new JobManager();
        this.commandFactory = new CommandFactory();
        this.state = new ShellState();
    }

    //start the shell command
    public void start(){
        Scanner scanner = new Scanner(System.in);

        while (isRunning){
            System.out.print(PROMPT);
            String input = scanner.nextLine().trim();

            if(!input.isEmpty()){
                processCommand(input);
            }
            else{
                System.out.println(input + " not found");
            }
        }
    }

    public void processCommand(String input){

        boolean background = false;

        if(input.endsWith("&")){
            background = true;
            input = input.substring(0, input.length() -1).trim();
        }
        String[] parts = input.split("\\s+" , 2);
        String commandName = parts[0];
        String args = parts.length >1 ? parts[1]: "";

        try {
            Command command = commandFactory.getCommand(commandName, state, jobManager);
            if (command != null) {
                if (background && command instanceof BackgroundableCommand) {
                    int jobId = jobManager.createJob(commandName, args);
                    ((BackgroundableCommand) command).executeInBackground(args, jobId);
                    System.out.println("[" + jobId + "] " + commandName + " " + args + " &");
                } else {
                    command.execute(args);
                }
            } else {
                System.out.println("Command not found: " + commandName);
            }
        } catch (Exception e) {
            System.out.println("Error executing command: " + e.getMessage());
        }
    }

    // Method to stop the shell
    public void exit() {
        this.isRunning = false;
    }
}
