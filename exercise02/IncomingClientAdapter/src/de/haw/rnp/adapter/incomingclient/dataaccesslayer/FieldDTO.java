package de.haw.rnp.adapter.incomingclient.dataaccesslayer;

import de.haw.rnp.util.enumerations.FieldType;

public class FieldDTO<T> {

    private FieldType fieldType;
    private int length;
    private T data;

    public FieldDTO(FieldType fieldType, int length, T data){
        this.fieldType = fieldType;
        this.length = length;
        this.data = data;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public int getLength() {
        return length;
    }

    public T getData() {
        return data;
    }
}
