package de.skuzzle.roman;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.Is;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class LiteralTest {

    @Test
    public void testEqualsContract() throws Exception {
        EqualsVerifier.forClass(Literal.class).withIgnoredFields("chars").verify();
    }

    @Test
    public void testMatchesEnd() throws Exception {
        final Literal subject = Literal.define("IV", 10);
        assertThat(subject.matches("XIV".toCharArray(), 1), Is.is(true));
    }

    @Test
    public void testMatchesMiddle() throws Exception {
        final Literal subject = Literal.define("IV", 10);
        assertThat(subject.matches("XIVX".toCharArray(), 1), Is.is(true));
    }

    @Test
    public void testMatchesStart() throws Exception {
        final Literal subject = Literal.define("IV", 10);
        assertThat(subject.matches("IVX".toCharArray(), 0), Is.is(true));
    }

    @Test
    public void testMatchesSame() throws Exception {
        final Literal subject = Literal.define("IV", 10);
        assertThat(subject.matches("IV".toCharArray(), 0), Is.is(true));
    }

    @Test
    public void testNoMatchToLong() throws Exception {
        final Literal subject = Literal.define("IV", 10);
        assertThat(subject.matches("IV".toCharArray(), 1), Is.is(false));
    }

    @Test
    public void testNoMatchDifferentString() throws Exception {
        final Literal subject = Literal.define("IV", 10);
        assertThat(subject.matches("IX".toCharArray(), 0), Is.is(false));
    }
}
