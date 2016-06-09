package de.haw.rnp.util.enumerations;

import de.haw.rnp.util.ChatUtil;

public enum FieldType {
    IP(1),
    Port(2),
    UserList(3),
    Name(4),
    Text(5);

    private int code;

    FieldType(int code) {
        this.code = code;
    }

    public byte[] getCode() {
        return ChatUtil.intToTwoBytesArray(code);
    }

    private static FieldType[] allValues = values();

    public static FieldType fromInt(int n) {return allValues[n-1];}

    public static FieldType fromBytes(byte[] bytes) {
        return fromInt(ChatUtil.byteArrayToInt(bytes));
    }
}
