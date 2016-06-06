package de.haw.rnp.chat.server.dataaccesslayer.entities;

import de.haw.rnp.chat.server.dataaccesslayer.enumerations.FieldType;
import de.haw.rnp.chat.util.ChatUtil;

import java.util.Arrays;

public class Field<T> {
    private FieldType fieldType;
    private int length;
    private T data;

    public Field(FieldType fieldType, int length, T data){
        this.fieldType = fieldType;
        this.length = length;
        this.data = data;
    }

    public Field(byte[] header){
        fieldType = FieldType.fromBytes(Arrays.copyOf(header, 2));
        length = ChatUtil.byteArrayToInt(Arrays.copyOfRange(header, 2, 4));
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

    public byte[] getFieldAsBytes(){
        byte[] result = new byte[0];
        ChatUtil.concat(result, fieldType.getCode(), ChatUtil.intToTwoBytesArray(length));
        if(fieldType == FieldType.IP){
            User tmp = (User) data;
            return ChatUtil.concat(result, tmp.getAddressAsBytes());
        }else if(fieldType == FieldType.Port){
            User tmp = (User) data;
            return ChatUtil.concat(result, tmp.getPortAsBytes());
        }else if(fieldType == FieldType.Name){
            User tmp = (User) data;
            return ChatUtil.concat(result, tmp.getUsernameAsBytes());
        }else if(fieldType == FieldType.Text){
            Message tmp = (Message) data;
            return ChatUtil.concat(result, tmp.getMessageAsBytes());
        }else{
            Userlist tmp = (Userlist) data;
            return ChatUtil.concat(result, tmp.getUserlistAsBytes());
        }
    }
}
