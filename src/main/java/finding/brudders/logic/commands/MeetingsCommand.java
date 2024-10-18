package finding.brudders.logic.commands;

import static java.util.Objects.requireNonNull;

import finding.brudders.model.Model;

/**
 * Lists all meetings in the address book to the user.
 */
public class MeetingsCommand extends Command {

    public static final String COMMAND_WORD = "meetings";

    public static final String MESSAGE_SUCCESS = "Listed all meetings";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        StringBuilder message = new StringBuilder();
        message.append(model.listMeetings());
        return new CommandResult(MESSAGE_SUCCESS + "\n" + message);
    }
}
