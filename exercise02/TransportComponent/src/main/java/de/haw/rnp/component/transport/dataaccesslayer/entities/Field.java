package de.haw.rnp.component.transport.dataaccesslayer.entities;

import de.haw.rnp.util.AddressType;
import de.haw.rnp.util.ChatUtil;
import de.haw.rnp.util.enumerations.FieldType;

import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

/**
 * Generic Class for a Frame Field. The Fields are specified in the Protocol Standards.
 *
 * @param <T> Type of the Field
 */
public class Field<T> {
    private FieldType fieldType;
    private int length;
    private T data;

    public Field(FieldType fieldType, int length, T data) {
        this.fieldType = fieldType;
        this.length = length;
        this.data = data;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * Returns the field converted to Protocol specific Byte representation.
     *
     * @return this Field as bytes
     */
    public byte[] getFieldAsBytes() {
        byte[] result = new byte[0];
        result = ChatUtil.concat(result, fieldType.getCode(), ChatUtil.intToTwoBytesArray(length));
        if (fieldType == FieldType.IP) {
            InetAddress tmp = (InetAddress) data;
            return ChatUtil.concat(result, tmp.getAddress());
        } else if (fieldType == FieldType.Port) {
            int tmp = (Integer) data;
            return ChatUtil.concat(result, ChatUtil.intToTwoBytesArray(tmp));
        } else if (fieldType == FieldType.Name) {
            String tmp = (String) data;
            return ChatUtil.concat(result, tmp.getBytes(StandardCharsets.US_ASCII));
        } else if (fieldType == FieldType.Text) {
            String tmp = (String) data;
            return ChatUtil.concat(result, tmp.getBytes(StandardCharsets.US_ASCII));
        } else {
            return ChatUtil.concat(result, userListToBytes());
        }
    }

    @SuppressWarnings("unchecked")
    private byte[] userListToBytes() {
        Collection<AddressType> tmp = (Collection) data;
        byte[] result = new byte[0];
        byte[] reserved = new byte[]{0x0, 0x0};
        for (AddressType address : tmp) {
            result = ChatUtil.concat(result, address.getIp().getAddress(), reserved, ChatUtil.intToTwoBytesArray(address.getPort()));
        }
        return result;
    }
}
