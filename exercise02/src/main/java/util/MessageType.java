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
}
