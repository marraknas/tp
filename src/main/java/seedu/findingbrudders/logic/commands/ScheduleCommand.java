package seedu.findingbrudders.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.findingbrudders.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.findingbrudders.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.findingbrudders.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.time.LocalDateTime;
import java.util.List;

import seedu.findingbrudders.commons.core.index.Index;
import seedu.findingbrudders.commons.util.ToStringBuilder;
import seedu.findingbrudders.logic.Messages;
import seedu.findingbrudders.logic.commands.exceptions.CommandException;
import seedu.findingbrudders.model.Model;
import seedu.findingbrudders.model.udder.Meeting;
import seedu.findingbrudders.model.udder.Udder;
import seedu.findingbrudders.model.udder.exceptions.TimeClashException;

/**
 * Schedules a meeting with another udder from the findingbrudders book.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Schedules a meeting with another "
            + "udder from the findingbrudders book.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_LOCATION + "LOCATION "
            + PREFIX_START_TIME + "START_TIME "
            + PREFIX_END_TIME + "END_TIME "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_LOCATION + "The Terrace "
            + PREFIX_START_TIME + "09-10-2024 09:00 "
            + PREFIX_END_TIME + "09-10-2024 10:00 ";

    public static final String MESSAGE_SUCCESS = "New meeting with %1$s added: %2$s";

    public static final String MESSAGE_TIME_CLASH = "A meeting with anUdder will occur at that time. "
            + "Please reschedule to a timing when you are available!";

    public static final String MESSAGE_INVALID_ARGUMENT = "Invalid argument received! %1$s.";

    private final Index index;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final String location;

    private Meeting toAdd;

    /**
     * Creates a ScheduleCommand to add the specified {@code Meeting}
     * @param index of the udder in the filtered udder list to schedule a meeting with
     */
    public ScheduleCommand(Index index, LocalDateTime startTime, LocalDateTime endTime, String location) {
        requireNonNull(index);
        requireNonNull(startTime);
        requireNonNull(endTime);
        requireNonNull(location);

        this.index = index;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Udder> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Udder udderToScheduleMeetingWith = lastShownList.get(index.getZeroBased());

        try {
            toAdd = new Meeting(udderToScheduleMeetingWith.getName(), startTime, endTime, location);
        } catch (CommandException e) {
            return new CommandResult(String.format(MESSAGE_INVALID_ARGUMENT, e.getMessage()));
        }

        try {
            model.addMeeting(udderToScheduleMeetingWith, toAdd);
        } catch (TimeClashException e) {
            return new CommandResult(MESSAGE_TIME_CLASH);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, udderToScheduleMeetingWith.getName(),
                Messages.format(toAdd)));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}

