package de.haw.rnp.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Testing the ChatUtils.
 *
 * @author Daniel Schruhl
 */
public class ChatUtilTest {

    @Test
    public void intToByte() throws Exception {
        byte result = ChatUtil.intToByte(3);
        assertEquals((byte) 0x3, result);
        result = ChatUtil.intToByte(265);
        assertEquals((byte) 0xF80, result);
        result = ChatUtil.intToByte(132);
        assertEquals((byte) 0x84, result);
    }

    @Test
    public void intToOneByteArray() throws Exception {
        byte[] result = ChatUtil.intToOneByteArray(3);
        assertEquals((byte) 0x3, result[0]);
        assertEquals(1, result.length);
    }

    @Test
    public void intToByteArray() throws Exception {
        byte[] result = ChatUtil.intToByteArray(13000);
        assertEquals((byte) 0x0, result[0]);
        assertEquals((byte) 0x0, result[1]);
        assertEquals((byte) 0x32, result[2]);
        assertEquals((byte) 0xC8, result[3]);
        assertEquals(4, result.length);
    }

    @Test
    public void intToTwoBytesArray() throws Exception {
        byte[] result = ChatUtil.intToTwoBytesArray(13000);
        assertEquals((byte) 0x32, result[0]);
        assertEquals((byte) 0xC8, result[1]);
        assertEquals(2, result.length);
    }

    @Test
    public void concat() throws Exception {
        byte[] a = new byte[]{(byte) 0x0, (byte) 0x3};
        byte[] b = new byte[]{(byte) 0x2, (byte) 0x4};
        byte[] result = ChatUtil.concat(a, b);
        assertEquals(4, result.length);
        assertEquals((byte) 0x0, result[0]);
        assertEquals((byte) 0x3, result[1]);
        assertEquals((byte) 0x2, result[2]);
        assertEquals((byte) 0x4, result[3]);
    }

    @Test
    public void byteArrayToInt() throws Exception {
        byte[] a = new byte[]{(byte) 0x32, (byte) 0xC8};
        int result = ChatUtil.byteArrayToInt(a);
        assertEquals(13000, result);
    }

    @Test
    public void cut() throws Exception {
        byte[] a = new byte[]{(byte) 0x0, (byte) 0x3};
        byte[] result = ChatUtil.cut(a, 1);
        assertEquals(1, result.length);
        assertEquals(0x3, result[0]);
        result = ChatUtil.cut(a, 0);
        assertEquals(2, result.length);
        assertEquals(0x0, result[0]);
        assertEquals((byte) 0x3, result[1]);
    }

}