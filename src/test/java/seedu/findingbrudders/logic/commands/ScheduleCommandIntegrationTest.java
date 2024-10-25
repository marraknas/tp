package seedu.findingbrudders.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.findingbrudders.logic.commands.ScheduleCommand.MESSAGE_TIME_CLASH;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.findingbrudders.commons.core.index.Index;
import seedu.findingbrudders.logic.commands.exceptions.CommandException;
import seedu.findingbrudders.model.AddressBook;
import seedu.findingbrudders.model.Model;
import seedu.findingbrudders.model.ModelManager;
import seedu.findingbrudders.model.UserPrefs;
import seedu.findingbrudders.model.udder.Meeting;
import seedu.findingbrudders.model.udder.Udder;
import seedu.findingbrudders.model.udder.exceptions.TimeClashException;
import seedu.findingbrudders.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code ScheduleCommand}.
 */
public class ScheduleCommandIntegrationTest {
    public static final Udder ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withRole("mUdder").withMajor("bza")
            .withPhone("94351253")
            .withTags("friends").build();
    private Model model;


    @BeforeEach
    public void setUp() {
        model = new ModelManager(new AddressBook(), new UserPrefs());
        model.addPerson(ALICE);
    }

    @Test
    public void execute_meetingClash_throwsCommandException() {
        Udder validUdder = new PersonBuilder().build();
        model.addPerson(validUdder);

        LocalDateTime startTime = LocalDateTime.of(2024, 10, 9, 9, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 10, 9, 10, 0);
        String location = "The Terrace";

        try {
            Meeting existingMeeting = new Meeting(validUdder.getName(), startTime, endTime, location);
            model.addMeeting(validUdder, existingMeeting);

            LocalDateTime newStartTime = LocalDateTime.of(2024, 10, 9, 9, 30);
            LocalDateTime newEndTime = LocalDateTime.of(2024, 10, 9, 10, 30);
            ScheduleCommand scheduleCommand = new ScheduleCommand(Index.fromZeroBased(0), newStartTime,
                    newEndTime, "New Location");

            CommandResult commandResult = scheduleCommand.execute(model);

            assertEquals(new CommandResult(MESSAGE_TIME_CLASH).getFeedbackToUser(),
                    commandResult.getFeedbackToUser());
        } catch (TimeClashException | CommandException e) {
            // This should not happen in the setup phase
            throw new RuntimeException(e);
        }
    }
}
