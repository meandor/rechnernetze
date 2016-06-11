package de.haw.rnp.client;

import de.haw.rnp.adapter.incomingclient.accesslayer.IInClientAdapterServices;
import de.haw.rnp.adapter.incomingclient.dataaccesslayer.FieldDTO;
import de.haw.rnp.adapter.incomingclient.dataaccesslayer.FrameDTO;
import de.haw.rnp.adapter.outgoingclient.accesslayer.IOutClientAdapterServices;
import de.haw.rnp.client.model.User;
import de.haw.rnp.client.observers.MessageObserver;
import de.haw.rnp.client.observers.UserlistObserver;
import de.haw.rnp.client.view.ViewController;
import de.haw.rnp.util.AddressType;
import de.haw.rnp.util.enumerations.FieldType;
import de.haw.rnp.util.enumerations.MessageType;
import javafx.application.Platform;
import javafx.collections.ObservableList;

import javax.swing.text.View;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.concurrent.Future;

public class Controller implements IControllerService {

    private static final int IP_LENGTH = 4;
    private static final int PORT_LENGTH = 2;
    private static final int RESERVED = 2;
    private static final int USERLIST_MUL = IP_LENGTH + PORT_LENGTH + RESERVED;
    private static final int LOGIN_LENGTH = 3;
    private static final int MESSAGE_LENGTH = 2;
    private static final int LOGOUT_LENTGH = 2;
    private static final int VERSION = 1;

    private ObservableList<User> users;
    private ViewController viewController;
    private MessageObserver messageObserver;
    private UserlistObserver userlistObserver;
    private IInClientAdapterServices inAdapterServices;
    private IOutClientAdapterServices outAdapterServices;
    private MainApp main;

    public Controller(IInClientAdapterServices inAdapterServices, IOutClientAdapterServices outAdapterServices, MainApp main){
        this.main = main;
        this.inAdapterServices = inAdapterServices;
        this.outAdapterServices = outAdapterServices;
        this.users = this.main.getList();
        init();
    }

    private void init(){
        viewController = new ViewController(this.main.getPrimaryStage(), this, users);

        messageObserver = new MessageObserver(this.viewController);
        userlistObserver = new UserlistObserver(this.users);

        outAdapterServices.registerObserverToUsers(userlistObserver);
        outAdapterServices.registerObserverToMessages(messageObserver);

        main.getPrimaryStage().setOnCloseRequest(t -> {
            if (viewController.getLocal() != null) {
                inAdapterServices.stopServer();
            }
            Platform.exit();
        });
    }

    @Override
    public boolean startServer(AddressType localAddress) {
        return inAdapterServices.startServer(localAddress);
    }

    @Override
    public boolean sendLogin(AddressType recipient) {
        User local = viewController.getLocal();
        if(local.getAddress().equals(recipient))
                return false;

        FrameDTO frame = new FrameDTO(local.getAddress(), recipient, VERSION, MessageType.Login, LOGIN_LENGTH);
        FieldDTO<InetAddress> ip = new FieldDTO<>(FieldType.IP, IP_LENGTH, local.getAddress().getIp());
        FieldDTO<Integer> port = new FieldDTO<>(FieldType.Port, PORT_LENGTH, local.getAddress().getPort());
        FieldDTO<String> name = new FieldDTO<>(FieldType.Name, local.getName().length(), local.getName());
        frame.addFieldDTO(ip, port, name);
        return inAdapterServices.sendLogin(frame, local.getName());
    }

    @Override
    public boolean sendMessage(String message, ArrayList<AddressType> recipients) {
        User local = viewController.getLocal();
        for(AddressType recipient : recipients) {
            FrameDTO frame = new FrameDTO(local.getAddress(), recipient, VERSION, MessageType.TextMessage, MESSAGE_LENGTH);
            FieldDTO<String> msg = new FieldDTO<>(FieldType.Text, message.length(), message);
            FieldDTO<ArrayList<AddressType>> adr = new FieldDTO<>(FieldType.UserList, USERLIST_MUL * recipients.size(), recipients);
            frame.addFieldDTO(msg, adr);
            inAdapterServices.sendMessage(frame);
        }
        return true;
    }

    @Override
    public void sendLogout() {
        User local = viewController.getLocal();
        User recipient = users.get(0);
        FrameDTO frame = new FrameDTO(local.getAddress(), recipient.getAddress(), VERSION, MessageType.Logout, LOGOUT_LENTGH);
        FieldDTO<InetAddress> ip = new FieldDTO<>(FieldType.IP, IP_LENGTH, local.getAddress().getIp());
        FieldDTO<Integer> port = new FieldDTO<>(FieldType.Port, PORT_LENGTH, local.getAddress().getPort());
        frame.addFieldDTO(ip, port);
        inAdapterServices.sendLogout(frame);
    }
}
