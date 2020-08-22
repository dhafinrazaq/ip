package command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;

import exception.DukeException;
import exception.EmptyDeleteException;
import exception.NoSuchTaskException;

import task.Task;

import java.io.IOException;
import java.util.Arrays;

public class DeleteCommand extends Command {

    public DeleteCommand(String[] splitCommand) {
        super(splitCommand);
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        try {
            String oneBasedIndex = splitCommand[1];
            int zeroBasedIndex = Integer.parseInt(oneBasedIndex) - 1;
            Task toDelete = tasks.remove(zeroBasedIndex);

            ui.sayDeletedTask(toDelete, tasks.size());
            storage.save(tasks);
        } catch (IOException e) {
            ui.sayException(e);
        } catch (IndexOutOfBoundsException e) { // No description
            throw new EmptyDeleteException();
        } catch (NoSuchTaskException e) {
            ui.sayException(e);
        }
    }

    public boolean isExit() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof DeleteCommand) {
            DeleteCommand other = (DeleteCommand) o;
            return Arrays.equals(other.splitCommand, this.splitCommand);
        } else {
            return false;
        }
    }
}
