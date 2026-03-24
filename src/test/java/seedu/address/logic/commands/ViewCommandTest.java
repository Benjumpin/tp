package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

public class ViewCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_viewByName_success() {
        Person personToView = model.getFilteredPersonList().get(0);
        ViewCommand viewCommand = new ViewCommand(null, personToView.getName());

        String expectedMessage = String.format(ViewCommand.MESSAGE_VIEW_PERSON_SUCCESS, personToView.getName().fullName);

        // We explicitly check that the CommandResult contains the right person
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false, personToView);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(viewCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_viewByIndex_success() {
        Person personToView = model.getFilteredPersonList().get(0);
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_PERSON, null);

        String expectedMessage = String.format(ViewCommand.MESSAGE_VIEW_PERSON_SUCCESS, personToView.getName().fullName);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false, personToView);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(viewCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_contactNotFoundByName_failure() {
        Name notInModelName = new Name("Unknown Person Name");
        ViewCommand viewCommand = new ViewCommand(null, notInModelName);

        assertCommandFailure(viewCommand, model, ViewCommand.MESSAGE_PERSON_NOT_FOUND);
    }

    @Test
    public void execute_invalidIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ViewCommand viewCommand = new ViewCommand(outOfBoundIndex, null);

        assertCommandFailure(viewCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Name nameA = new Name("Alice");
        Name nameB = new Name("Bob");

        ViewCommand viewByIndexFirst = new ViewCommand(INDEX_FIRST_PERSON, null);
        ViewCommand viewByIndexSecond = new ViewCommand(INDEX_SECOND_PERSON, null);
        ViewCommand viewByNameA = new ViewCommand(null, nameA);
        ViewCommand viewByNameB = new ViewCommand(null, nameB);

        // same object -> returns true
        org.junit.jupiter.api.Assertions.assertTrue(viewByIndexFirst.equals(viewByIndexFirst));
        org.junit.jupiter.api.Assertions.assertTrue(viewByNameA.equals(viewByNameA));

        // same values -> returns true
        ViewCommand viewByIndexFirstCopy = new ViewCommand(INDEX_FIRST_PERSON, null);
        org.junit.jupiter.api.Assertions.assertTrue(viewByIndexFirst.equals(viewByIndexFirstCopy));

        // different types -> returns false
        org.junit.jupiter.api.Assertions.assertFalse(viewByIndexFirst.equals(1));

        // null -> returns false
        org.junit.jupiter.api.Assertions.assertFalse(viewByIndexFirst.equals(null));

        // different target types (index vs name) -> returns false
        org.junit.jupiter.api.Assertions.assertFalse(viewByIndexFirst.equals(viewByNameA));

        // different index -> returns false
        org.junit.jupiter.api.Assertions.assertFalse(viewByIndexFirst.equals(viewByIndexSecond));

        // different name -> returns false
        org.junit.jupiter.api.Assertions.assertFalse(viewByNameA.equals(viewByNameB));
    }
}
