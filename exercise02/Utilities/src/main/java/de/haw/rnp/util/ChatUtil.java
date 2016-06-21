package de.haw.rnp.util;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Utils used for the chat programm.
 *
 * @author Sebastian Wientzek
 */
public final class ChatUtil {

    /**
     * No Object of this class can be created.
     */
    private ChatUtil() {
    }

    /**
     * Converts a int to a byte.
     *
     * @param number int number
     * @return byte of the int
     */
    public static byte intToByte(int number) {
        if (number <= 255 && number > 0)
            return (byte) number;
        else return Byte.MIN_VALUE;
    }

    /**
     * Returns the int as a byte array with just one entry.
     *
     * @param number int of the converted number
     * @return byte array of size one with the int as a byte
     */
    public static byte[] intToOneByteArray(int number) {
        return new byte[]{intToByte(number)};
    }

    /**
     * Returns a int as a byte array.
     *
     * @param number int to be converted
     * @return byte array with size of 4
     */
    public static byte[] intToByteArray(int number) {
        return ByteBuffer.allocate(Integer.BYTES).putInt(number).array();
    }

    /**
     * Returns the int as a two byte array.
     *
     * @param number int number to be converted
     * @return array with the bytes
     */
    public static byte[] intToTwoBytesArray(int number) {
        byte[] result = new byte[2];
        byte[] byteArray = intToByteArray(number);
        result[0] = byteArray[2];
        result[1] = byteArray[3];
        return result;
    }

    /**
     * Returns two byte arrays concatenated.
     *
     * @param a array 1
     * @param b array 2
     * @return concatenation of array 1 and 2
     */
    public static byte[] concat(byte[] a, byte[]... b) {
        int length = a.length;
        for (byte[] bytes : b) {
            length += bytes.length;
        }
        byte[] result = Arrays.copyOf(a, length);
        int pos = a.length;
        for (byte[] bytes : b) {
            System.arraycopy(bytes, 0, result, pos, bytes.length);
            pos += bytes.length;
        }
        return result;
    }

    /**
     * Returns the byte array into a integer.
     *
     * @param b byte array
     * @return integer of the byte array
     */
    public static int byteArrayToInt(byte[] b) {
        return b[1] & 0xFF |
                (b[0] & 0xFF) << 8;
    }

    /**
     * Shortens an array and cuts away everything too long.
     *
     * @param a byte array to be shortened
     * @param b int size of the resulting cutted array
     * @return byte array that is cutted
     */
    public static byte[] cut(byte[] a, int b) {
        return Arrays.copyOfRange(a, b, a.length);
    }

    /**
     * Indicates the size of a ByteBuffer.
     */
    public static final int BYTEBUFFER_SIZE = 60;
}
