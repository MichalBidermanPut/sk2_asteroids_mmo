/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk2_asteroids_mmo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author patry
 */
public class Connection extends Thread {

    private InetAddress address;
    private int port;
    private DatagramSocket socket;
    private byte[] buffer;
    private DatagramPacket dpacket;

    public LinkedList<String> messagesQueue;

    public Connection(InetAddress address, int port) {
        this.address = address;
        this.port = port;
        this.buffer = new byte[2048];
        messagesQueue = new LinkedList<>();
    }

    public void sendInt(int message) {
        try {
            ByteBuffer buff = ByteBuffer.allocate(4);
            byte[] b = buff.order(ByteOrder.LITTLE_ENDIAN).putInt(message).array();
            DatagramPacket packet = new DatagramPacket(b, b.length, address, port);
            socket.send(packet);
        } catch (IOException ex) {
        }
    }

    public void sendDouble(double message) {
        try {
            ByteBuffer buff = ByteBuffer.allocate(8);
            byte[] b = buff.order(ByteOrder.LITTLE_ENDIAN).putDouble(message).array();
            DatagramPacket packet = new DatagramPacket(b, b.length, address, port);
            socket.send(packet);
        } catch (IOException ex) {
        }
    }
    
    public void sendString(String message) {
        byte[] b = message.getBytes();
        DatagramPacket packet = new DatagramPacket(b, b.length, address, port);
        try {
            socket.send(packet);
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            socket.receive(dpacket);
            String msg = new String(buffer, 0, dpacket.getLength());
            dpacket.setLength(buffer.length);
            messagesQueue.add(msg);
            close();
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void close() {
        socket.close();
    }
}
