package de.haw.rnp.util;

import java.nio.ByteBuffer;
import java.util.Arrays;

public final class ChatUtil {

    private ChatUtil(){}

    public static byte intToByte(int number){
        if(number <= 255 && number > 0)
            return (byte) number;
        else return Byte.MIN_VALUE;
    }

    public static byte[] intToOneByteArray(int number){
        return new byte[]{intToByte(number)};
    }

    public static byte[] intToByteArray(int number) {
        return ByteBuffer.allocate(Integer.BYTES).putInt(number).array();
    }

    public static byte[] intToTwoBytesArray(int number){
        byte[] result = new byte[2];
        byte[] byteArray = intToByteArray(number);
        result[0] = byteArray[2];
        result[1] = byteArray[3];
        return result;
    }

    public static byte[] concat (byte[] a, byte[]... b){
        int length = a.length;
        for(byte[] bytes : b){
            length += bytes.length;
        }
        byte[] result = Arrays.copyOf(a, length);
        int pos = a.length;
        for(byte[] bytes : b){
            System.arraycopy(bytes, 0, result, pos, bytes.length);
            pos += bytes.length;
        }
        return result;
    }

    public static int byteArrayToInt(byte[] b)
    {
        return   b[1] & 0xFF |
                (b[0] & 0xFF) << 8;
    }

    public static byte[] cut(byte[] a, int b){
        return Arrays.copyOfRange(a, b, a.length);
    }
}
