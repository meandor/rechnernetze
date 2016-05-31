package util;

import java.nio.ByteBuffer;

public final class ChatUtil {

    private ChatUtil(){}

    public static byte intToByte(int number){
        if(number <= 255 && number > 0)
            return (byte) number;
        else return Byte.MIN_VALUE;
    }

    public static byte[] intToByteArray(int number) {
        return ByteBuffer.allocate(Integer.BYTES).putInt(number).array();
    }

    public static byte[] intToPort(int port){
        byte[] result = new byte[2];
        byte[] byteArray = intToByteArray(port);
        result[0] = byteArray[2];
        result[1] = byteArray[3];
        return result;
    }
}
