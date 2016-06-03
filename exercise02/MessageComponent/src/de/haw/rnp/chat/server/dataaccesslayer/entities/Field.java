package de.haw.rnp.chat.server.dataaccesslayer.entities;

import de.haw.rnp.chat.server.dataaccesslayer.enumerations.FieldType;

public class Field<T> {
    private FieldType fieldType;
    private int length;
    private T data;

    public Field(FieldType fieldType, int length, T data){
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
}
