package seedu.findingbrudders.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.findingbrudders.commons.core.index.Index;
import seedu.findingbrudders.commons.util.ToStringBuilder;
import seedu.findingbrudders.logic.Messages;
import seedu.findingbrudders.logic.commands.exceptions.CommandException;
import seedu.findingbrudders.model.Model;
import seedu.findingbrudders.model.udder.Meeting;
import seedu.findingbrudders.model.udder.Name;
import seedu.findingbrudders.model.udder.Udder;

/**
 * Deletes a meeting from an Udder's list of meetings.
 */
public class DeleteMeetingCommand extends Command {

    public static final String COMMAND_WORD = "deletem";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the meeting identified by the index number used in the command result panel.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_MEETING_SUCCESS = "Deleted Meeting: %1$s";

    private final Index targetIndex;

    public DeleteMeetingCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (targetIndex.getZeroBased() >= model.getMeetingSize()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEETING_INDEX);
        }

        Meeting toDelete = model.getMeeting(targetIndex.getZeroBased());
        Name target = toDelete.getPersonToMeet();
        Udder udderToDeleteMeeting = null;
        for (Udder udder : model.getFilteredPersonList()) {
            if (udder.getName().equals(target)) {
                udderToDeleteMeeting = udder;
            }
        }

        if (udderToDeleteMeeting == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEETING_INDEX);
        }

        model.deleteMeeting(udderToDeleteMeeting, toDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_MEETING_SUCCESS, Messages.format(toDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteMeetingCommand otherDeleteMeetingCommand = (DeleteMeetingCommand) other;
        return targetIndex.equals(otherDeleteMeetingCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
