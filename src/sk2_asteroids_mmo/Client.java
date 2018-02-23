/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk2_asteroids_mmo;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 *
 * @author patry
 */
public class Client implements IClient {

    private final String username;
    private final String host;
    private final int port;
    private Socket socket;

    Connection connection = null;

    public Client(String host, int port, String username) {
        this.host = host;
        this.port = port;
        this.username = username;
    }

    @Override
    public void update() {
    }

    @Override
    public List<Interactable> getObjects() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sendPosition(double x, double y) {
        connection.sendDouble(x);
        connection.sendDouble(y);
    }

    @Override
    public boolean login(String username, String password) {
        try {
            socket = new Socket(host, port);
        } catch (IOException ex) {
            return false;
        }

        connection = new Connection(socket);
        connection.start();

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
