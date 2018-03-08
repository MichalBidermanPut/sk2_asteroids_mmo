/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk2_asteroids_mmo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author patry
 */
public class Client {

    private final String host;
    private final int portLogin;
    private final int portUpdate;

    Craft craft;
    Connection connection = null;

    public Client(String host, int portLogin, int portUpdate) {
        this.host = host;
        this.portLogin = portLogin;
        this.portUpdate = portUpdate;
        this.craft = new Craft();
    }

    public boolean login(String username, int lenUsername) {
        try {
            InetAddress address = InetAddress.getByName(host);
            connection = new Connection(address, portLogin, portUpdate);
            connection.start();
            connection.sendLogin(username, lenUsername);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public void update() {
        connection.sendUpdate(craft);
    }

    public String receiveLogin() {
        if (connection.messagesQueueLogin.isEmpty()) {
            return null;
        } else {
            String message = connection.messagesQueueLogin.getFirst();
            connection.messagesQueueLogin.removeFirst();
            return message;
        }
    }

    public String receiveUpdate() {
        if (connection.messagesQueueUpdate.isEmpty()) {
            return null;
        } else {
            String message = connection.messagesQueueUpdate.getFirst();
            connection.messagesQueueUpdate.removeFirst();
            return message;
        }
    }

    public void logout() {
        if (connection != null) {
            connection.close();
        }
    }

    public Craft getCraft() {
        return craft;
    }
}
