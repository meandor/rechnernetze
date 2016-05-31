package util;

/**
 * Created by admin on 31.05.2016.
 */
public enum FieldType {
    IP(1),
    Port(2),
    UserList(3),
    Name(4),
    Text(5);

    private int code;

    private FieldType(int code) {
        this.code = code;
    }

    public byte[] getCode() {
        return ChatUtil.intToTwoBytesArray(code);
    }

    private static FieldType[] allValues = values();

    public static FieldType fromInt(int n) {return allValues[n-1];}
}
