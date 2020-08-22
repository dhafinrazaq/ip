package duke;

import java.io.IOException;

import command.Command;

import exception.DukeException;

public class Duke {
    private final String DATA_DIRECTORY = "data";
    private final String SAVED_FILE_PATH = "data/duke.txt";

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public static void main(String[] args) {
        Duke duke = new Duke();
        duke.run();
    }

    public Duke() {
        ui = new Ui();
        storage = new Storage(DATA_DIRECTORY, SAVED_FILE_PATH);
        try {
            tasks = new TaskList(storage.loadSavedFile());
        } catch (IOException e) {
            ui.sayException(e);
        }
    }

    public void run() {
        ui.hello();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine(); // show the divider line ("------")
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (DukeException e) {
                ui.sayException(e);
            } finally {
                ui.showLine();
            }
        }
    }
}

