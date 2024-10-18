package finding.brudders.logic.parser;

import static finding.brudders.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static finding.brudders.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static finding.brudders.testutil.Assert.assertThrows;
import static finding.brudders.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import finding.brudders.logic.commands.AddCommand;
import finding.brudders.logic.commands.ClearCommand;
import finding.brudders.logic.commands.DeleteCommand;
import finding.brudders.logic.commands.EditCommand;
import finding.brudders.logic.commands.EditCommand.EditPersonDescriptor;
import finding.brudders.logic.commands.ExitCommand;
import finding.brudders.logic.commands.FindCommand;
import finding.brudders.logic.commands.HelpCommand;
import finding.brudders.logic.commands.ListCommand;
import finding.brudders.logic.parser.exceptions.ParseException;
import finding.brudders.model.udder.Person;
import finding.brudders.model.udder.PersonContainsKeywordsPredicate;
import finding.brudders.testutil.EditPersonDescriptorBuilder;
import finding.brudders.testutil.PersonBuilder;
import finding.brudders.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        String input = "n/Benson m/cs";
        FindCommand command = (FindCommand) parser.parseCommand(FindCommand.COMMAND_WORD + " " + input);
        PersonContainsKeywordsPredicate expectedPredicate =
                new PersonContainsKeywordsPredicate("Benson", null, null, null, "cs", null, null);
        assertEquals(new FindCommand(expectedPredicate), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
