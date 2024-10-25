package seedu.findingbrudders.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.findingbrudders.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.findingbrudders.commons.core.GuiSettings;
import seedu.findingbrudders.commons.core.index.Index;
import seedu.findingbrudders.logic.Messages;
import seedu.findingbrudders.logic.commands.exceptions.CommandException;
import seedu.findingbrudders.model.AddressBook;
import seedu.findingbrudders.model.Model;
import seedu.findingbrudders.model.ReadOnlyAddressBook;
import seedu.findingbrudders.model.ReadOnlyUserPrefs;
import seedu.findingbrudders.model.udder.Meeting;
import seedu.findingbrudders.model.udder.Udder;
import seedu.findingbrudders.model.udder.exceptions.TimeClashException;
import seedu.findingbrudders.testutil.PersonBuilder;

public class ScheduleCommandTest {
    private Index index = Index.fromZeroBased(0);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private LocalDateTime startTime = LocalDateTime.parse("30-07-2024 11:00", formatter);
    private LocalDateTime endTime = LocalDateTime.parse("30-07-2024 12:00", formatter);
    private String location = "A Valid Location";
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ScheduleCommand(null, startTime, endTime, location));
        assertThrows(NullPointerException.class, () -> new ScheduleCommand(index, null, endTime, location));
        assertThrows(NullPointerException.class, () -> new ScheduleCommand(index, startTime, null, location));
        assertThrows(NullPointerException.class, () -> new ScheduleCommand(index, startTime, endTime, null));
    }

    @Test
    public void execute_meetingAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingMeetingAdded modelStub = new ScheduleCommandTest.ModelStubAcceptingMeetingAdded();
        Udder udder = new PersonBuilder().build();
        Meeting validMeeting = new Meeting(udder.getName(), startTime, endTime, location);;

        CommandResult commandResult = new ScheduleCommand(index, startTime, endTime, location).execute(modelStub);

        assertEquals(String.format(ScheduleCommand.MESSAGE_SUCCESS, udder.getName(), Messages.format(validMeeting)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validMeeting), modelStub.meetingsAdded);
    }

    @Test
    public void execute_meetingClash_throwsCommandException() {
        Udder validUdder = new PersonBuilder().build();
        LocalDateTime startTime = LocalDateTime.of(2024, 10, 9, 9, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 10, 9, 10, 0);

        try {
            Meeting existingMeeting = new Meeting(validUdder.getName(), startTime, endTime, "Location A");

            ModelStubWithMeeting modelStub = new ModelStubWithMeeting(validUdder, existingMeeting);

            LocalDateTime newStartTime = LocalDateTime.of(2024, 10, 9, 9, 30);
            LocalDateTime newEndTime = LocalDateTime.of(2024, 10, 9, 10, 30);
            ScheduleCommand scheduleCommand = new ScheduleCommand(Index.fromZeroBased(0), newStartTime,
                    newEndTime, "Location B");

            assertThrows(CommandException.class, "Meeting times overlap", () -> scheduleCommand.execute(modelStub));
        } catch (TimeClashException | CommandException e) {
            // This should not happen in the setup phase
            throw new RuntimeException(e);
        }
    }


    /**
     * A default model stub that have all methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Udder udder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Udder udder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Udder target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Udder target, Udder editedUdder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Udder> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Udder> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMeeting(Udder target, Meeting meeting) throws CommandException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String listMeetings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getMeetingSize() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteMeeting(Udder target, Meeting meeting) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Meeting getMeeting(int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasMeeting(Meeting meeting) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMeeting(Udder target, Meeting meeting, Meeting editedMeeting) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single udder and meeting under the udder.
     */
    private class ModelStubWithMeeting extends ModelStub {
        private final Udder udder;
        private final ArrayList<Meeting> meetings = new ArrayList<>();

        ModelStubWithMeeting(Udder udder, Meeting meeting) {
            requireNonNull(udder);
            requireNonNull(meeting);
            this.udder = udder;
            this.meetings.add(meeting);
        }

        @Override
        public boolean hasPerson(Udder udder) {
            requireNonNull(udder);
            return this.udder.isSamePerson(udder);
        }

        @Override
        public void addMeeting(Udder udder, Meeting meeting) throws CommandException {
            requireNonNull(meeting);
            for (Meeting existingMeeting : meetings) {
                if (existingMeeting.isOverlap(meeting)) {
                    throw new CommandException("Meeting times overlap");
                }
            }
            meetings.add(meeting);
        }

        @Override
        public ObservableList<Udder> getFilteredPersonList() {
            return javafx.collections.FXCollections.observableList(Arrays.asList(udder));
        }
    }

    /**
     * A Model stub that always accept the Meeting being added.
     */
    private class ModelStubAcceptingMeetingAdded extends ModelStub {
        private ArrayList<Meeting> meetingsAdded = new ArrayList<>();
        private ArrayList<Udder> personsAdded = new ArrayList<>();
        private Udder udder = new PersonBuilder().build();


        @Override
        public boolean hasPerson(Udder udder) {
            requireNonNull(udder);
            return personsAdded.stream().anyMatch(udder::isSamePerson);
        }

        @Override
        public void addPerson(Udder udder) {
            requireNonNull(udder);
            personsAdded.add(udder);
        }

        @Override
        public ObservableList<Udder> getFilteredPersonList() {
            personsAdded.add(udder);
            return javafx.collections.FXCollections.observableList(personsAdded);
        }

        @Override
        public void addMeeting(Udder udder, Meeting meeting) {
            requireNonNull(meeting);
            requireNonNull(udder);
            meetingsAdded.add(meeting);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
