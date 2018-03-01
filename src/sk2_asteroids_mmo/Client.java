/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk2_asteroids_mmo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author patry
 */
public class Client {

    private final String username;
    private final String host;
    private final int port;

    Connection connection = null;

    public Client(String host, int port, String username) {
        this.host = host;
        this.port = port;
        this.username = username;
    }

    public void update(double x, double y, double rotation, ArrayList<Bullet> newBullets) {
        String message = String.valueOf((int) x * 10000) + " ";
        message += String.valueOf((int) y * 10000) + " ";
        message += String.valueOf((int) rotation) + " ";
        message += String.valueOf(newBullets.size());

        for (int i = 0; i < newBullets.size(); i++) {
            message += " " + String.valueOf((int) newBullets.get(i).getX());
            message += " " + String.valueOf((int) newBullets.get(i).getY());
            message += " " + String.valueOf((int) newBullets.get(i).getRotation());
            message += String.valueOf(System.currentTimeMillis());
        }

        connection.sendString(message);
    }

    public List<Interactable> getObjects() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void sendPosition(double x, double y) {
        connection.sendDouble(x);
        connection.sendDouble(y);
    }

    public boolean login(String username, String password) {
        try {
            InetAddress address = InetAddress.getByName(host);
            connection = new Connection(address, port);
            connection.start();
            connection.sendString(username);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
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

}
