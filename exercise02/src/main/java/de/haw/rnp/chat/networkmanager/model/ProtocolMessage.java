package de.haw.rnp.chat.networkmanager.model;

import util.ChatUtil;
import util.FieldType;
import util.MessageType;
import util.Triplet;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class ProtocolMessage {

    //constant byte lengths
    private static final int IP_LENGTH = 4;
    private static final int PORT_LENGTH = 2;
    private static final int FIELDTYPE_LENGTH = 2;
    private static final int USERLIST_MULTI = IP_LENGTH + PORT_LENGTH + 2;
    private static final int BYTE_LENGTH = 1;
    private static final int HEADER_LENGTH = 12;

    //CommonHeader
    private int version;
    private MessageType messageType;
    private byte[] reserved;
    private InetAddress senderIp;
    private int senderPort;
    private int fieldCount;
    private ArrayList<Triplet<FieldType, Integer, byte[]>> fields;
    private List<byte[]> fieldlist;

    public ProtocolMessage(int version, MessageType messageType, InetAddress senderIp, int senderPort,int fieldCount) {
        this.version = version;
        this.messageType = messageType;
        this.senderIp = senderIp;
        this.senderPort = senderPort;
        this.fieldCount = fieldCount;
        this.init();
    }

    public ProtocolMessage(byte[] protocolMessage) {
        this.init();
        readHeader(Arrays.copyOf(protocolMessage, HEADER_LENGTH));
        readFields(Arrays.copyOfRange(protocolMessage, HEADER_LENGTH, protocolMessage.length));
    }

    public byte[] getMessageAsByteArray(){
        return ChatUtil.concat(convertHeader(),convertFields());
    }

    public List<byte[]> getFieldsAsByteList(){
        if(fieldlist == null){
            fieldlist = new ArrayList<>();
            for(Triplet<FieldType, Integer, byte[]> field : fields){
                byte[] array = ChatUtil.concat(field.getFirst().getCode(), ChatUtil.intToByteArray(field.getSecond()), field.getThird());
                fieldlist.add(array);
            }
        }
        return  fieldlist;
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
        return ChatUtil.concat(header, reserved, senderIp.getAddress(), portByte, fieldByte);
    }

    private byte[] convertFields(){
        byte[] result = new byte[0];
        for(Triplet<FieldType, Integer, byte[]> field : fields){
            byte[] prefix = Arrays.copyOf(field.getFirst().getCode(), 4);
            prefix[3] = ChatUtil.intToByte(field.getSecond());
            result = ChatUtil.concat(result, prefix, field.getThird());
            for (byte res : prefix) {
                System.out.println("prefix: " + field.getFirst().getCode()[0] + field.getFirst().getCode()[1]);
            }
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

    private HashMap<InetAddress, Integer> byteArrayToUserList(Triplet<FieldType, Integer, byte[]> triplet){
        HashMap<InetAddress, Integer> hashmap = new HashMap<>();
        int noOfEntries = Integer.divideUnsigned(triplet.getSecond(), USERLIST_MULTI);
        byte[] entries = Arrays.copyOfRange(triplet.getThird(), FIELDTYPE_LENGTH*2 - 1, triplet.getThird().length);
        for(int counter = 0; counter < noOfEntries; counter++){
            InetAddress ip = null;
            try {
                ip = InetAddress.getByAddress(Arrays.copyOf(entries, IP_LENGTH));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            int pos = IP_LENGTH + PORT_LENGTH;
            int offset = IP_LENGTH + (2*PORT_LENGTH);
            int port = ChatUtil.byteArrayToInt(Arrays.copyOfRange(entries, pos, offset));
            hashmap.put(ip, port);
            entries = Arrays.copyOfRange(entries, offset, entries.length);
        }
        return hashmap;
    }

    private void readFields(byte[] fieldArr){
        for(int pos = 0; pos < fieldArr.length-1; ){
            byte[] fieldTypeByte = Arrays.copyOfRange(fieldArr, pos, pos + FIELDTYPE_LENGTH);
            FieldType fieldType = FieldType.fromInt(ChatUtil.byteArrayToInt(fieldTypeByte));
            pos += FIELDTYPE_LENGTH;
            int length = ChatUtil.byteArrayToInt(Arrays.copyOfRange(fieldArr, pos, pos + FIELDTYPE_LENGTH));
            pos += FIELDTYPE_LENGTH;
            byte[] field = Arrays.copyOfRange(fieldArr, pos, pos + length);
            pos += length;
            fields.add(new Triplet<>(fieldType, length, field));
        }
    }

    private void readHeader(byte[] header){
        version = Byte.toUnsignedInt(header[0]);
        messageType = MessageType.fromByte(header[1]);
        try {
            senderIp = InetAddress.getByAddress(Arrays.copyOfRange(header, 4, 8));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        senderPort = ChatUtil.byteArrayToInt(Arrays.copyOfRange(header, 8, 10));
        fieldCount = ChatUtil.byteArrayToInt(Arrays.copyOfRange(header, 10, 12));
    }

    private void init(){
        this.reserved = new byte[]{0x0,0x0};
        this.fields = new ArrayList<>();
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
