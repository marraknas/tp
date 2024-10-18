package finding.brudders.logic.commands;

import static finding.brudders.logic.commands.CommandTestUtil.assertCommandSuccess;
import static finding.brudders.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import finding.brudders.model.AddressBook;
import finding.brudders.model.Model;
import finding.brudders.model.ModelManager;
import finding.brudders.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
