package finding.brudders.logic.commands;

import static finding.brudders.logic.parser.CliSyntax.PREFIX_END_TIME;
import static finding.brudders.logic.parser.CliSyntax.PREFIX_LOCATION;
import static finding.brudders.logic.parser.CliSyntax.PREFIX_START_TIME;
import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.List;

import finding.brudders.commons.core.index.Index;
import finding.brudders.commons.util.ToStringBuilder;
import finding.brudders.logic.Messages;
import finding.brudders.logic.commands.exceptions.CommandException;
import finding.brudders.model.Model;
import finding.brudders.model.udder.Meeting;
import finding.brudders.model.udder.Person;

/**
 * Schedules a meeting with another udder from the address book.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Schedules a meeting with another "
            + "udder from the address book.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_LOCATION + "LOCATION "
            + PREFIX_START_TIME + "START_TIME "
            + PREFIX_END_TIME + "END_TIME "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_LOCATION + "The Terrace "
            + PREFIX_START_TIME + "09-10-2024 09:00 "
            + PREFIX_END_TIME + "09-10-2024 10:00 ";

    public static final String MESSAGE_SUCCESS = "New meeting with %1$s added: %2$s";

    private final Index index;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final String location;

    private Meeting toAdd;

    /**
     * Creates a ScheduleCommand to add the specified {@code Meeting}
     *
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
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToScheduleMeetingWith = lastShownList.get(index.getZeroBased());

        toAdd = new Meeting(personToScheduleMeetingWith.getName(), startTime, endTime, location);

        model.addMeeting(personToScheduleMeetingWith, toAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS, personToScheduleMeetingWith.getName(),
                Messages.format(toAdd)));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}

