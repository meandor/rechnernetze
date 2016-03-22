package de.haw.rnp01.messageticker.test;

import de.haw.rnp01.messageticker.model.RandomGenerator;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by on 21.03.2016.
 */
public class RandomGeneratorTest {

    @Test
    public void testGetInstance() throws Exception {
        assertEquals(RandomGenerator.getInstance(), RandomGenerator.getInstance());
    }

    @Test
    public void testGenerateRandomMessageType() throws Exception {
        ArrayList<String> possibilities = new ArrayList<String>();
        possibilities.add("INFO");
        possibilities.add("WARN");
        possibilities.add("CORR");
        String testMessageType = RandomGenerator.getInstance().generateRandomMessageType();
        assertTrue(possibilities.contains(testMessageType));
    }

    @Test
    public void testGenerateRandomSleepTime() throws Exception {
        long r1 = RandomGenerator.getInstance().generateRandomSleepTime();
        long r2 = RandomGenerator.getInstance().generateRandomSleepTime();
        assertTrue(r1 >= 1000 && r1 <= 5000);
        assertTrue(r2 >= 1000 && r2 <= 5000);
        assertFalse(r1 == r2);
    }

    @Test
    public void testGenerateRandomMessage() throws Exception {
        String r1 = RandomGenerator.getInstance().generateRandomMessage();
        String r2 = RandomGenerator.getInstance().generateRandomMessage();
        String pattern = "[0-9a-zA-Z]+";
        assertTrue(r1.matches(pattern));
        assertTrue(r2.matches(pattern));
        assertFalse(r1 == r2);
    }
}