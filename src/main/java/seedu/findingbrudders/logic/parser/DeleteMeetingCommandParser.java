package seedu.findingbrudders.logic.parser;

import static seedu.findingbrudders.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.findingbrudders.commons.core.index.Index;
import seedu.findingbrudders.logic.commands.DeleteMeetingCommand;
import seedu.findingbrudders.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteMeetingCommand object
 */
public class DeleteMeetingCommandParser implements Parser<DeleteMeetingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteMeetingCommand
     * and returns a DeleteMeetingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteMeetingCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteMeetingCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMeetingCommand.MESSAGE_USAGE), pe);
        }
    }
}
