package de.haw.rnp.chat.networkmanager;

import util.ChatUtil;
import util.FieldType;
import util.MessageType;
import util.Triplet;

import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ProtocolMessage {

    //constant byte lengths
    private static final int IP_LENGTH = 4;
    private static final int PORT_LENGTH = 2;
    private static final int NAME_MIN = 3;
    private static final int NAME_MAX = 24;
    private static final int USERLIST_MULTI = IP_LENGTH + PORT_LENGTH + 2;
    private static final int RESERVED = 2;
    private static final int BYTE_LENGTH = 1;

    //CommonHeader
    private int version;
    private MessageType messageType;
    private byte[] reserved;
    private InetAddress senderIp;
    private int senderPort;
    private int fieldCount;
    private ArrayList<Triplet<FieldType, Integer, byte[]>> fields;

    public ProtocolMessage(int version, MessageType messageType, InetAddress senderIp, int senderPort,int fieldCount) {
        this.version = version;
        this.messageType = messageType;
        this.senderIp = senderIp;
        this.senderPort = senderPort;
        this.fieldCount = fieldCount;
        this.reserved = new byte[]{0x0,0x0};
        this.fields = new ArrayList<>();
    }

    public byte[] getMessageAsByteArray(){
        return ChatUtil.concat(convertHeader(),convertFields());
    }

    public void addIpField(InetAddress ipAddress){
        fields.add(new Triplet<>(FieldType.IP, IP_LENGTH, ipAddress.getAddress()));
    }

    public void addPortField(int port){
        byte[] portByte = ChatUtil.intToByteArray(port);
        fields.add(new Triplet<>(FieldType.Port, PORT_LENGTH, ChatUtil.intToTwoBytesArray(port)));
    }

    public void addUserListField(HashMap<InetAddress, Integer> userlist){
        byte[] userlistByte = userListToByteArray(userlist);
        fields.add(new Triplet<>(FieldType.UserList, USERLIST_MULTI * userlist.size(), userlistByte));
    }

    public void addNameField(String name){
        byte[] nameByte = name.getBytes(StandardCharsets.US_ASCII);
        fields.add(new Triplet<>(FieldType.Name, nameByte.length, nameByte));
    }

    public void addTextField(String text){
        byte[] textByte = text.getBytes(StandardCharsets.US_ASCII);
        fields.add(new Triplet<>(FieldType.Text, textByte.length, textByte));
    }

    private byte[] convertHeader(){
        byte[] header = new byte[2];
        byte[] fieldByte = ChatUtil.intToTwoBytesArray(fieldCount);
        byte[] portByte = ChatUtil.intToTwoBytesArray(senderPort);
        int pos = 0;
        header[pos] = ChatUtil.intToByte(version);
        pos += BYTE_LENGTH;
        header[pos] = messageType.getCode();
        header = ChatUtil.concat(header, reserved, senderIp.getAddress(), portByte, fieldByte);
        return header;
    }

    private byte[] convertFields(){
        byte[] result = new byte[0];
        for(Triplet<FieldType, Integer, byte[]> field : fields){
            byte[] prefix = new byte[4];
            prefix[1] = field.getFirst().getCode();
            prefix[3] = ChatUtil.intToByte(field.getSecond());
            result = ChatUtil.concat(result, prefix, field.getThird());
        }
        return result;
    }

    private byte[] userListToByteArray(HashMap<InetAddress, Integer> userlist){
        byte[] result = new byte[0];
        for(Map.Entry<InetAddress, Integer> user : userlist.entrySet()){
            byte[] port = ChatUtil.intToTwoBytesArray(user.getValue());
            result = ChatUtil.concat(result, user.getKey().getAddress(), reserved, port);
        }
        return result;
    }

    private boolean validateMinFields(int num){
        if(messageType == MessageType.Login && num < 2 && num > 3)
            return false;
        else if(messageType == MessageType.Logout && num != 2)
            return false;
        else if(messageType == MessageType.TextMessage && num != 2)
            return false;
        else if(messageType == MessageType.MyName && num != 2)
            return false;
        return true;
    }
}
