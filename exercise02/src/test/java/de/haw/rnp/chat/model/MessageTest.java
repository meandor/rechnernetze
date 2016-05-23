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
        message = new Message("foo", "bar");
    }

    @Test
    public void getType() throws Exception {
        assertEquals("foo", message.getType());
    }

    @Test
    public void setType() throws Exception {
        message.setType("foobar");
        assertEquals("foobar", message.getType());
    }

    @Test
    public void getContent() throws Exception {
        assertEquals("bar", message.getContent());
    }

    @Test
    public void setContent() throws Exception {
        message.setContent("1337");
        assertEquals("1337", message.getContent());
    }

    @Test
    public void testToString() {
        assertEquals("foo: bar", message.toString());
    }
}