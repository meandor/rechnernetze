package util;

public enum MessageType {
    Login(1),
    Logout(2),
    TextMessage(3),
    MyName(4);

    private int code;

    private MessageType(int code) {
        this.code = code;
    }

    public byte getCode() {
        return ChatUtil.intToByte(code);
    }

    private static MessageType[] allValues = values();
    public static MessageType fromByte(byte n) {return allValues[Byte.toUnsignedInt(n)-1];}
}
