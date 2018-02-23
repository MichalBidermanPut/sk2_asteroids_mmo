/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk2_asteroids_mmo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedList;

/**
 *
 * @author patry
 */
public class Connection extends Thread {

    private Socket socket;
    private BufferedReader in;
    private DataOutputStream out;

    private LinkedList<String> messagesQueue;

    public Connection(Socket socket) {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
        }
        this.socket = socket;
    }

    public void sendInt(int x) {
        try {
            ByteBuffer buff = ByteBuffer.allocate(4);
            byte[] b = buff.order(ByteOrder.LITTLE_ENDIAN).putInt(x).array();
            out.write(b);
        } catch (IOException ex) {
        }
    }

    public void sendDouble(double x) {
        try {
            ByteBuffer buff = ByteBuffer.allocate(8);
            byte[] b = buff.order(ByteOrder.LITTLE_ENDIAN).putDouble(x).array();
            out.write(b);
        } catch (IOException ex) {
        }
    }

    @Override
    public void run() {
        String s;
        try {
            while ((s = in.readLine()) != null) {
                messagesQueue.add(s);
            }
            out.close();
            in.close();
            socket.close();
        } catch (IOException ex) {
        }
    }
}
