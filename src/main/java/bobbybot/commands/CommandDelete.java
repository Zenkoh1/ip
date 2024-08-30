package bobbybot.commands;

import java.io.IOException;

import bobbybot.BobbyBotException;
import bobbybot.Storage;
import bobbybot.Task;
import bobbybot.TaskList;
import bobbybot.ui.Ui;



/**
 * Represents a command to delete a task.
 */
public class CommandDelete extends Command {
    private final int index;

    /**
     * Creates a new CommandDelete object.
     *
     * @param argument The argument to the command.
     * @throws BobbyBotException If the argument is invalid.
     */
    public CommandDelete(String argument) throws BobbyBotException {
        String[] params = argument.split(" ");
        if (params.length != 1) {
            throw new BobbyBotException("Please specify exactly one task number.");
        }
        String indexParam = params[0];
        if (!indexParam.matches("\\d+")) {
            throw new BobbyBotException("Please specify a valid number.");
        }
        this.index = Integer.parseInt(argument);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BobbyBotException {
        if (index < 0 || index >= tasks.getSize()) {
            throw new BobbyBotException("Please specify a task number that is in range.");
        }
        Task task = tasks.deleteTask(index);
        ui.printRemoveTask(tasks, task);
        try {
            storage.saveTasksToFile(tasks.toArray());
        } catch (IOException e) {
            throw new BobbyBotException("Error saving to file.");
        }
    }
}
