package shell;

import manager.ProcessManager;

import java.io.File;
import java.io.IOException;

// Shell State Class
public class ShellState {
    public File currentDirectory;
    private ProcessManager processManager;

    public ShellState() {
        currentDirectory = new File(System.getProperty("user.dir"));
        processManager = new ProcessManager();
    }

    public File getCurrentDirectory() {
        return currentDirectory;
    }

    public void setCurrentDirectory(File directory) throws IOException {
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IOException("Directory does not exist: " + directory.getPath());
        }

        currentDirectory = directory.getCanonicalFile();
    }

    public String getCurrentDirectoryPath() {
        return currentDirectory.getAbsolutePath();
    }

    public ProcessManager getProcessManager() {
        return processManager;
    }
}
