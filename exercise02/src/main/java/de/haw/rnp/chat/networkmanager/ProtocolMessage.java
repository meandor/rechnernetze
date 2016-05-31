package de.haw.rnp.chat.networkmanager;

import de.haw.rnp.chat.model.User;
import de.haw.rnp.chat.networkmanager.tasks.ClientCloseTask;
import de.haw.rnp.chat.networkmanager.tasks.ServerAwaitConnectionsTask;
import de.haw.rnp.chat.networkmanager.tasks.ServerStartTask;
import util.ChatUtil;
import util.FieldType;
import util.MessageType;
import util.Triplet;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;



public class ProtocolMessage {

    //constant byte lengths
    private static final int IP_LENGTH = 4;
    private static final int PORT_LENGTH = 2;
    private static final int NAME_MIN = 3;
    private static final int NAME_MAX = 24;
    private static final int USERLIST_MULTI = IP_LENGTH + PORT_LENGTH + 2;
    private static final int RESERVED = 2;

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

    public void addIpField(InetAddress ipAddress){
        fields.add(new Triplet<>(FieldType.IP, IP_LENGTH, ipAddress.getAddress()));
    }

    public void addPortField(int port){
        byte[] portByte = ChatUtil.intToByteArray(port);
        fields.add(new Triplet<>(FieldType.Port, PORT_LENGTH, ChatUtil.intToPort(port)));
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

    public byte[] getByteArray(){
        byte[] header = new byte[12];
        header[0] = ChatUtil.intToByte(version);
        header[1] = messageType.getCode();
        return header;
    }

    private byte[] userListToByteArray(HashMap<InetAddress, Integer> userlist){
        byte[] result = new byte[USERLIST_MULTI * userlist.size()];
        int pos = 0;
        for(Map.Entry<InetAddress, Integer> user : userlist.entrySet()){
            System.arraycopy(user.getKey().getAddress(), 0, result, pos, IP_LENGTH);
            pos += IP_LENGTH;
            System.arraycopy(reserved,0, result, pos, RESERVED);
            pos += RESERVED;
            byte[] port = ChatUtil.intToPort(user.getValue());
            System.arraycopy(port, 0, result, pos, PORT_LENGTH);
            pos += PORT_LENGTH;
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
