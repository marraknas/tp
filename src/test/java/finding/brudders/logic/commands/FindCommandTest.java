package finding.brudders.logic.commands;

import static finding.brudders.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static finding.brudders.logic.commands.CommandTestUtil.assertCommandSuccess;
import static finding.brudders.testutil.TypicalPersons.ALICE;
import static finding.brudders.testutil.TypicalPersons.BENSON;
import static finding.brudders.testutil.TypicalPersons.CARL;
import static finding.brudders.testutil.TypicalPersons.DANIEL;
import static finding.brudders.testutil.TypicalPersons.ELLE;
import static finding.brudders.testutil.TypicalPersons.FIONA;
import static finding.brudders.testutil.TypicalPersons.GEORGE;
import static finding.brudders.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import finding.brudders.model.Model;
import finding.brudders.model.ModelManager;
import finding.brudders.model.UserPrefs;
import finding.brudders.model.udder.NameContainsKeywordsPredicate;
import finding.brudders.model.udder.PersonContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        PersonContainsKeywordsPredicate firstPredicate =
                new PersonContainsKeywordsPredicate("first", null, null, null, null, null, null);
        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate("second", null, null, null, null, null, null);

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different udder -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_oneField_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate("someone", null, null, null, null, null, null);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_oneField_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(null, null, null, null, "cs", null, null);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, ELLE), model.getFilteredPersonList());
    }

    @Test
    public void execute_nameField_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate("A", null, null, null, null, null, null);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, CARL, DANIEL, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_nameField_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate("Alice", null, null, null, null, null, null);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(ALICE), model.getFilteredPersonList());
    }

    @Test
    public void execute_phoneField_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(null, "9482", null, null, null, null, null);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ELLE, FIONA, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_phoneField_noPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(null, "12312312", null, null, null, null, null);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_emailField_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(null, null, "alice@example.com", null, null, null, null);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(ALICE), model.getFilteredPersonList());
    }

    @Test
    public void execute_emailField_noPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(null, null, "hoohoohaahaa@example.com", null, null, null, null);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_roleField_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(null, null, null, "mUdder", null, null, null);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, CARL, DANIEL, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_majorField_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(null, null, null, null, "cs", null, null);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, ELLE), model.getFilteredPersonList());
    }

    @Test
    public void execute_addressField_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(null, null, null, null, null, "wall street", null);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(CARL), model.getFilteredPersonList());
    }

    @Test
    public void execute_addressField_noPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(null, null, null, null, null, "somenonexistentplace", null);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_singleTagInTagField_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        List<String> expectedTags = List.of("friend");
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(null, null, null, null, null, null, expectedTags);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleTagsInTagField_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        List<String> expectedTags = List.of("friends", "owesMoney");
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(null, null, null, null, null, null, expectedTags);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleTagsInTagField_noPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        List<String> expectedTags = List.of("friends", "owesMoney", "gymBrother");
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(null, null, null, null, null, null, expectedTags);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleFields_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate("Benson", null, null, "brUdder", null, null, null);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleFields_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(null, null, null, "brUdder", "cs", null, null);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, ELLE), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleFields_noPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate("Peter Orlando", null, null, "brUdder", "cs", null, null);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate("test", null, null, null, null, null, null);
        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate} (No longer used).
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
