package seedu.findingbrudders.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.findingbrudders.testutil.Assert.assertThrows;
import static seedu.findingbrudders.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.findingbrudders.commons.core.GuiSettings;
import seedu.findingbrudders.logic.Messages;
import seedu.findingbrudders.logic.commands.exceptions.CommandException;
import seedu.findingbrudders.model.AddressBook;
import seedu.findingbrudders.model.Model;
import seedu.findingbrudders.model.ReadOnlyAddressBook;
import seedu.findingbrudders.model.ReadOnlyUserPrefs;
import seedu.findingbrudders.model.udder.Meeting;
import seedu.findingbrudders.model.udder.Udder;
import seedu.findingbrudders.testutil.PersonBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Udder validUdder = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validUdder).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validUdder)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validUdder), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Udder validUdder = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validUdder);
        ModelStub modelStub = new ModelStubWithPerson(validUdder);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Udder alice = new PersonBuilder().withName("Alice").build();
        Udder bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different udder -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(ALICE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
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
        public void addMeeting(Udder target, Meeting meeting) {
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
     * A Model stub that contains a single udder.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Udder udder;

        ModelStubWithPerson(Udder udder) {
            requireNonNull(udder);
            this.udder = udder;
        }

        @Override
        public boolean hasPerson(Udder udder) {
            requireNonNull(udder);
            return this.udder.isSamePerson(udder);
        }
    }

    /**
     * A Model stub that always accept the udder being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Udder> personsAdded = new ArrayList<>();

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
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
