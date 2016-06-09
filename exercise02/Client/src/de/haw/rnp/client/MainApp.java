package de.haw.rnp.client;

import de.haw.rnp.adapter.incomingclient.accesslayer.IInClientAdapterServices;
import de.haw.rnp.adapter.incomingclient.accesslayer.InClientAdapterFacade;
import de.haw.rnp.adapter.incomingpeer.accesslayer.IIncomingPeerAdapterServices;
import de.haw.rnp.adapter.incomingpeer.accesslayer.IncomingPeerAdapterFacade;
import de.haw.rnp.adapter.outgoingclient.accesslayer.IOutClientAdapterServices;
import de.haw.rnp.adapter.outgoingclient.accesslayer.IOutClientAdapterServicesForInPeer;
import de.haw.rnp.adapter.outgoingclient.accesslayer.OutClientAdapterFacade;
import de.haw.rnp.adapter.outgoingpeer.accesslayer.IOutgoingPeerAdapterServices;
import de.haw.rnp.adapter.outgoingpeer.accesslayer.OutgoingPeerAdapterFacade;
import de.haw.rnp.client.model.User;
import de.haw.rnp.component.transport.accesslayer.ITransportServices;
import de.haw.rnp.component.transport.accesslayer.ITransportServicesForIncomingPeerAdapter;
import de.haw.rnp.component.transport.accesslayer.TransportFacade;
import de.haw.rnp.component.user.accesslayer.IUserServices;
import de.haw.rnp.component.user.accesslayer.UserFacade;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class MainApp extends Application {

    IOutgoingPeerAdapterServices outgoingPeerAdapterServices;
    IIncomingPeerAdapterServices incomingPeerAdapterServices;
    ITransportServices transportServices;
    ITransportServicesForIncomingPeerAdapter transportServicesForIncomingPeerAdapter;
    IUserServices userServices;

    IControllerService controller;
    IOutClientAdapterServices outClientAdapterServices;
    IOutClientAdapterServicesForInPeer outClientAdapterServicesForInPeer;
    IInClientAdapterServices inClientAdapterServices;

    private Stage primaryStage;
    ObservableList<User> users;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        users = FXCollections.observableArrayList();

        userServices = new UserFacade();
        outgoingPeerAdapterServices = new OutgoingPeerAdapterFacade();
        transportServices = new TransportFacade(userServices, outgoingPeerAdapterServices);
        transportServicesForIncomingPeerAdapter = new TransportFacade(userServices, outgoingPeerAdapterServices);
        OutClientAdapterFacade ocaf = new OutClientAdapterFacade();
        outClientAdapterServices = ocaf;
        outClientAdapterServicesForInPeer = ocaf;
        incomingPeerAdapterServices = new IncomingPeerAdapterFacade(transportServicesForIncomingPeerAdapter, outClientAdapterServicesForInPeer);
        inClientAdapterServices = new InClientAdapterFacade(incomingPeerAdapterServices, transportServices);

        controller = new Controller(inClientAdapterServices, outClientAdapterServices, this);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
    public ObservableList<User> getList(){return users;}

    public static void main(String[] args) {
        launch(args);
    }
}