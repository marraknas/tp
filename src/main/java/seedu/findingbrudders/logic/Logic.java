package seedu.findingbrudders.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.findingbrudders.commons.core.GuiSettings;
import seedu.findingbrudders.logic.commands.CommandResult;
import seedu.findingbrudders.logic.commands.exceptions.CommandException;
import seedu.findingbrudders.logic.parser.exceptions.ParseException;
import seedu.findingbrudders.model.ReadOnlyAddressBook;
import seedu.findingbrudders.model.udder.Udder;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see seedu.findingbrudders.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Udder> getFilteredPersonList();

    /**
     * Returns the user prefs' findingbrudders book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
