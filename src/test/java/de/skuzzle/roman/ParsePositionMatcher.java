package de.skuzzle.roman;

import java.text.ParseException;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ParsePositionMatcher extends TypeSafeMatcher<ParseException> {

    private final int expectedErrorPosition;

    private ParsePositionMatcher(int expectedErrorPosition) {
        this.expectedErrorPosition = expectedErrorPosition;
    }

    public static Matcher<ParseException> errorPosition(int position) {
        return new ParsePositionMatcher(position);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(String.format(
                "parse exception with error index %d", this.expectedErrorPosition));
    }

    @Override
    protected boolean matchesSafely(ParseException item) {
        return item.getErrorOffset() == this.expectedErrorPosition;
    }

}
