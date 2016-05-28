package de.haw.rnp.chat.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the Message Model
 */
public class MessageTest {

    private Message message;

    @Before
    public void setup() {
        message = new Message("foobar");
    }

    @Test
    public void getText() throws Exception {
        assertEquals("foobar", message.getType());
    }

    @Test
    public void setText() throws Exception {
        message.setType("foo");
        assertEquals("foo", message.getType());
    }

    @Test
    public void testToString() {
        assertEquals("foobar", message.toString());
    }
}