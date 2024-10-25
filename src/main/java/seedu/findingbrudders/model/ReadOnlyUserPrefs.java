package seedu.findingbrudders.model;

import java.nio.file.Path;

import seedu.findingbrudders.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getAddressBookFilePath();

}
