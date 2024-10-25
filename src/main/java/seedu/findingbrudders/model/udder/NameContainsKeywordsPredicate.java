package seedu.findingbrudders.model.udder;

import java.util.List;
import java.util.function.Predicate;

import seedu.findingbrudders.commons.util.StringUtil;
import seedu.findingbrudders.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Udder}'s {@code Name} matches any of the keywords given. (No longer used)
 */
public class NameContainsKeywordsPredicate implements Predicate<Udder> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Udder udder) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(udder.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordsPredicate)) {
            return false;
        }

        NameContainsKeywordsPredicate otherNameContainsKeywordsPredicate = (NameContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
