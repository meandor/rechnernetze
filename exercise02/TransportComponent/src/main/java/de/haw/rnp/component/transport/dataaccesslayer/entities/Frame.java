package de.haw.rnp.component.transport.dataaccesslayer.entities;

import de.haw.rnp.adapter.outgoingclient.dataaccesslayer.MessageDTO;
import de.haw.rnp.adapter.outgoingclient.dataaccesslayer.UserDTO;
import de.haw.rnp.util.AddressType;
import de.haw.rnp.util.ChatUtil;
import de.haw.rnp.util.enumerations.FieldType;
import de.haw.rnp.util.enumerations.MessageType;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Frame {
    private AddressType sender;
    private AddressType recipient;
    private int version;
    private MessageType messageType;
    private int length;
    private Collection<Field> fields;

    public Frame(AddressType sender, AddressType recipient, int version, MessageType messageType, int length) {
        this.version = version;
        this.messageType = messageType;
        this.length = length;
        this.sender = sender;
        this.recipient = recipient;
        this.fields = new ArrayList<>();
    }

    public Frame(byte[] bytes) {
        version = Byte.toUnsignedInt(bytes[0]);
        messageType = MessageType.fromByte(bytes[1]);

        try {
            InetAddress senderIp = InetAddress.getByAddress(Arrays.copyOfRange(bytes, 4, 8));
            int senderPort = ChatUtil.byteArrayToInt(Arrays.copyOfRange(bytes, 8, 10));
            sender = new AddressType(senderIp, senderPort);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        length = ChatUtil.byteArrayToInt(Arrays.copyOfRange(bytes, 10, 12));
        this.fields = new ArrayList<>();
    }

    public AddressType getSender() {
        return sender;
    }

    public void setSender(AddressType sender) {
        this.sender = sender;
    }

    public AddressType getRecipient() {
        return recipient;
    }

    public void setRecipient(AddressType recipient) {
        this.recipient = recipient;
    }

    public void addField(Field field) {
        fields.add(field);
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Collection<Field> getFields() {
        return fields;
    }

    public void setFields(Collection<Field> fields) {
        this.fields = fields;
    }

    public byte[] getFrameAsBytes() {
        byte[] result = new byte[0];
        byte[] reserved = new byte[]{0x0, 0x0};

        //building the header
        result = ChatUtil.concat(result,
                ChatUtil.intToOneByteArray(version),
                messageType.getCodeAsArray(),
                reserved,
                sender.getIp().getAddress(),
                ChatUtil.intToTwoBytesArray(sender.getPort()),
                ChatUtil.intToTwoBytesArray(length));

        //adding fields
        for (Field field : fields) {
            result = ChatUtil.concat(result, field.getFieldAsBytes());
        }

        return result;
    }

    public UserDTO toUserDTO() {
        if (fields.size() < 2)
            return null;
        InetAddress ip = null;
        int port = 0;
        String name = "";

        for (Field field : fields) {
            if (field.getFieldType() == FieldType.IP)
                ip = (InetAddress) field.getData();
            if (field.getFieldType() == FieldType.Port)
                port = (Integer) field.getData();
            if (field.getFieldType() == FieldType.Name)
                name = (String) field.getData();
        }
        return new UserDTO(new AddressType(ip, port), name);
    }

    public UserDTO senderToUserDTO() {
        if (this.sender == null)
            return null;
        InetAddress ip = this.sender.getIp();
        int port = this.sender.getPort();
        String name = "";
        return new UserDTO(new AddressType(ip, port), name);
    }

    public MessageDTO toMessageDTO() {
        if (fields.size() != 2)
            return null;

        String msg = "";
        for (Field field : fields) {
            if (field.getFieldType() == FieldType.Text)
                msg = (String) field.getData();
        }

        return new MessageDTO(sender, msg);
    }
}
