/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk2_asteroids_mmo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author patry
 */
public class Client {

    private final String host;
    private final int port;

    Craft craft;
    Connection connection = null;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        this.craft = new Craft();
    }

    public List<Interactable> getObjects() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void sendPosition(double x, double y) {
        //connection.sendDouble(x);
        //connection.sendDouble(y);
    }

    public boolean login(String username, int lenUsername) {
        try {
            InetAddress address = InetAddress.getByName(host);
            connection = new Connection(address, port);
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

    public String receiveMessage() {
        if (connection.messagesQueue.isEmpty()) {
            return null;
        } else {
            String message = connection.messagesQueue.getFirst();
            connection.messagesQueue.removeFirst();
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
