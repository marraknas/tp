package seedu.findingbrudders.logic.commands;

import static seedu.findingbrudders.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.findingbrudders.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.findingbrudders.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.findingbrudders.logic.Messages;
import seedu.findingbrudders.model.Model;
import seedu.findingbrudders.model.ModelManager;
import seedu.findingbrudders.model.UserPrefs;
import seedu.findingbrudders.model.udder.Udder;
import seedu.findingbrudders.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Udder validUdder = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validUdder);

        assertCommandSuccess(new AddCommand(validUdder), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validUdder)),
                expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Udder udderInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddCommand(udderInList), model,
                AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
