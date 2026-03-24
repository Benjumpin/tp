package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewCommand;
import seedu.address.model.person.Name;

public class ViewCommandParserTest {

    private ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_validArgsByName_returnsViewCommand() {
        // Simulates: view n/Alice Pauline
        assertParseSuccess(parser, " n/Alice Pauline", new ViewCommand(null, new Name("Alice Pauline")));
    }

    @Test
    public void parse_validArgsByIndex_returnsViewCommand() {
        // Simulates: view 1
        assertParseSuccess(parser, " 1", new ViewCommand(INDEX_FIRST_PERSON, null));
    }

    @Test
    public void parse_bothIndexAndName_throwsParseException() {
        // Simulates mutually exclusive error: view 1 n/Alice Pauline
        assertParseFailure(parser, " 1 n/Alice Pauline",
                "Please provide either an index OR a name, not both.");
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Missing both index and name completely (empty string)
        assertParseFailure(parser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }
}
