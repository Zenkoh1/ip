package bobbybot.commands;

import bobbybot.DukeException;
import bobbybot.TaskList;
import bobbybot.Task;
import bobbybot.Storage;
import bobbybot.Deadline;
import bobbybot.ui.Ui;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandDeadline extends Command{

    private final String description;
    private final String by;


    public CommandDeadline(String argument) throws DukeException{
        Pattern r = Pattern.compile("(.*) /by (.*)");
        Matcher m = r.matcher(argument);
        if (m.find()) {
            description = m.group(1).trim();
            by = m.group(2).trim();
        } else {
            throw new DukeException("Please specify it in this format 'deadline <description> /by <by>'.");
        }
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        Task deadline = new Deadline(description, by);
        tasks.addTask(deadline);
        ui.printAddTask(tasks, deadline);
        try {
            storage.saveTasksToFile(tasks.toArray());
        } catch (IOException e) {
            throw new DukeException("Error saving to file.");
        }
    }
}
