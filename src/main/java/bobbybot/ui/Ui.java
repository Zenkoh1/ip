package bobbybot.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import bobbybot.BobbyBot;
import bobbybot.TaskList;
import bobbybot.tasks.Task;
import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Represents the user interface of BobbyBot.
 * Contains methods to interact with the user (input and output).
 */
public class Ui {

    private MainWindow mainWindow;
    private Stage stage;

    /**
     * Starts the JavaFX UI.
     *
     * @param stage     The stage to display the UI.
     * @param bobbyBot The BobbyBot object to interact with.
     */
    public void start(Stage stage, BobbyBot bobbyBot) {
        mainWindow = new MainWindow();
        this.stage = stage;
        Scene scene = new Scene(mainWindow);
        this.stage.setTitle(bobbyBot.getName());
        this.stage.setScene(scene);
        mainWindow.setBobbyBot(bobbyBot);
        printResponse("Hello! I'm " + bobbyBot.getName() + "\nWhat can I do for you?");
        this.stage.show();

        assert this.stage.isShowing();
    }

    /**
     * Stops the JavaFX UI.
     */
    public void stop() {
        // Delay closing the window to allow the user to read the final message.
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event -> stage.close());
        delay.play();

        assert !stage.isShowing();
    }

    /**
     * Prints the response to the JavaFX UI.
     * @param response The response to be printed.
     */
    public void printResponse(String... response) {
        mainWindow.printResponse(Arrays.stream(response).reduce("", (x, y) -> x + "\n" + y));
    }

    /**
     * Prints a descriptive message, showing the task that has been removed.
     *
     * @param tasks The list of tasks.
     * @param task  The task that has been removed.
     */
    public void printRemoveTask(TaskList tasks, Task task) {
        printResponse(
                "Noted. I've removed this task:",
                "\t" + task,
                "Now you have " + tasks.getSize() + " task(s) in the list."
        );
    }

    /**
     * Prints a descriptive message, showing the task that has been added.
     *
     * @param tasks The list of tasks.
     * @param task  The task that has been added.
     */
    public void printAddTask(TaskList tasks, Task task) {
        printResponse(
                "Got it. I've added this task:",
                "\t" + task,
                "Now you have " + tasks.getSize() + " task(s) in the list."
        );
    }

    /**
     * Prints the tasks that match the search query.
     *
     * @param tasks The list of tasks that match the search query.
     */
    public void listMatchingTasks(TaskList tasks) {
        ArrayList<String> taskListString = new ArrayList<>();
        taskListString.add("Here are the matching tasks in your list:");
        IntStream
                .range(0, tasks.getSize())
                .mapToObj(i -> i + 1 + ". " + tasks.getTask(i))
                .forEach(taskListString::add);
        printResponse(taskListString.toArray(String[]::new));
    }

    /**
     * Prints all the tasks in the list.
     *
     * @param tasks The list of tasks.
     */
    public void listTasks(TaskList tasks) {
        ArrayList<String> taskListString = new ArrayList<>();
        taskListString.add("Here are the tasks in your list:");
        IntStream
                .range(0, tasks.getSize())
                .mapToObj(i -> i + 1 + ". " + tasks.getTask(i))
                .forEach(taskListString::add);
        printResponse(taskListString.toArray(String[]::new));
    }

    /**
     * Prints an error message.
     *
     * @param e The exception that caused the error.
     */
    public void printError(Exception e) {
        printResponse(e.getMessage());
    }
}
