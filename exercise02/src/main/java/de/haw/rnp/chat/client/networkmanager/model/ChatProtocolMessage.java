package de.haw.rnp.chat.client.networkmanager.model;

import de.haw.rnp.chat.client.model.User;

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Message that is send by the ChatProtocol. Each message has a common header and fields.
 * For more information see: https://github.com/bashtee/rnworkinggroup3
 */
public abstract class ChatProtocolMessage {
    protected byte[] commonHeader;
    protected int totalSize;
    private List<byte[]> fields;

    public ChatProtocolMessage(byte messageType, InetAddress senderIP, int port, int fieldCount) {
        this.totalSize = 12;
        this.fields = new ArrayList<>();
        byte[] portByte = this.intToByteArray(port);
        byte[] fieldCountByte = this.intToByteArray(fieldCount);
        this.commonHeader = this.createCommonHeader(messageType, senderIP.getAddress(), portByte, fieldCountByte);
    }

    private byte[] createCommonHeader(byte messageType, byte[] senderIP, byte[] port, byte[] fieldCount) {
        byte[] result = new byte[12];
        byte[] firstRow = new byte[]{0x01, messageType, 0x00, 0x00};
        System.arraycopy(firstRow, 0, result, 0, firstRow.length);
        System.arraycopy(senderIP, 0, result, 4, senderIP.length);
        System.arraycopy(port, 2, result, 8, 2);
        System.arraycopy(fieldCount, 2, result, 10, 2);

        return result;
    }

    protected byte[] intToByteArray(int number) {
        return ByteBuffer.allocate(Integer.BYTES).putInt(number).array();
    }

    protected void addField(byte[] field) {
        this.fields.add(field);
        this.totalSize += field.length;
    }

    protected byte[] IPField(InetAddress hostName) {
        byte[] result = new byte[8];
        result[1] = 0x1;
        result[3] = 0x4;
        System.arraycopy(hostName.getAddress(), 0, result, 4, 4);
        return result;
    }

    protected byte[] portField(int port) {
        byte[] result = new byte[6];
        result[1] = 0x2;
        result[3] = 0x2;
        byte[] portByte = this.intToByteArray(port);
        result[4] = portByte[2];
        result[5] = portByte[3];
        return result;
    }

    protected byte[] nameField(String name) {
        byte[] nameByte = name.getBytes(StandardCharsets.US_ASCII);
        byte[] result = new byte[4 + nameByte.length];
        result[1] = 0x4;
        result[3] = (byte) nameByte.length;
        System.arraycopy(nameByte, 0, result, 4, nameByte.length);
        return result;
    }

    protected byte[] textField(String text) {
        byte[] textByte = text.getBytes(StandardCharsets.US_ASCII);
        byte[] result = new byte[4 + textByte.length];
        result[1] = 0x5;
        result[3] = (byte) textByte.length;
        System.arraycopy(textByte, 0, result, 4, textByte.length);
        return result;
    }

    protected byte[] userListField(List<User> userList) {
        int size = 4 + userList.size() * 8;
        byte[] result = new byte[size];
        result[1] = (byte) 0x3;
        result[3] = (byte) (size - 4);
        int position = 4;
        for (User u : userList) {
            System.arraycopy(u.getHostName().getAddress(), 0, result, position, 4);
            position += 6;
            byte[] portByte = this.intToByteArray(u.getPort());
            result[position] = portByte[2];
            result[position + 1] = portByte[3];
            position += 2;
        }
        return result;
    }

    /**
     * Returns the full message containing of the fields and the common header.
     *
     * @return full message
     */
    public byte[] getFullMessage() {
        byte[] result = new byte[this.totalSize];
        System.arraycopy(this.commonHeader, 0, result, 0, this.commonHeader.length);
        int position = 12;
        for (byte[] field : this.fields) {
            System.arraycopy(field, 0, result, position, field.length);
            position += field.length;
        }
        return result;
    }

    @Override
    public String toString() {
        byte[] data = this.getFullMessage();
        String result = "";
        int align = 0;
        for (byte b : data) {
            result += String.format("0x%x ", b);
            if ((align % 4) == 0 && align > 1) {
                result += "\n";
            }
            align++;
        }
        return result;
    }
}
