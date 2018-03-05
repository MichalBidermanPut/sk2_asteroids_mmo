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
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
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
        this.buffer = new byte[16384];
        messagesQueue = new LinkedList<>();
        try {
            this.socket = new DatagramSocket(port);
        } catch (SocketException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        dpacket = new DatagramPacket(this.buffer, this.buffer.length);
    }

    public void sendLogin(String username, int lenUsername) {
        ByteBuffer buffLenUsername = ByteBuffer.allocate(4);
        byte[] byteLenUsername = buffLenUsername.order(ByteOrder.LITTLE_ENDIAN).putInt(lenUsername).array();
        byte[] byteUsername = username.getBytes();
        byte[] bytePadding = new byte[256 - byteLenUsername.length - byteUsername.length];
        for (int i = 0; i < bytePadding.length; i++) {
            bytePadding[i] = 0;
        }
        byte[] byteMessage = new byte[256];
        System.arraycopy(byteLenUsername, 0, byteMessage, 0, byteLenUsername.length);
        System.arraycopy(byteUsername, 0, byteMessage, byteLenUsername.length, byteUsername.length);
        System.arraycopy(bytePadding, 0, byteMessage, (byteLenUsername.length + byteUsername.length), bytePadding.length);
        DatagramPacket packet = new DatagramPacket(byteMessage, byteMessage.length, address, port);
        try {
            socket.send(packet);
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendUpdate(Craft craft) {
        ByteBuffer buffLenBullets = ByteBuffer.allocate(4);
        byte[] byteLenBullets = buffLenBullets.order(ByteOrder.LITTLE_ENDIAN).putInt(craft.getNewBullets().size()).array();
        long time = System.currentTimeMillis();
        ByteBuffer buffTime = ByteBuffer.allocate(8);
        byte[] byteTime = buffTime.order(ByteOrder.LITTLE_ENDIAN).putLong(time).array();
        ByteBuffer buffX = ByteBuffer.allocate(4);
        byte[] byteX = buffX.order(ByteOrder.LITTLE_ENDIAN).putInt((int) craft.getX()).array();
        ByteBuffer buffY = ByteBuffer.allocate(4);
        byte[] byteY = buffY.order(ByteOrder.LITTLE_ENDIAN).putInt((int) craft.getY()).array();
        ByteBuffer buffID = ByteBuffer.allocate(4);
        byte[] byteID = buffID.order(ByteOrder.LITTLE_ENDIAN).putInt(craft.getID()).array();
        byte[] byteMessage = new byte[1024];
        int len = 0;
        System.arraycopy(byteLenBullets, 0, byteMessage, len, byteLenBullets.length);
        len += byteLenBullets.length;
        System.arraycopy(byteTime, 0, byteMessage, len, byteTime.length);
        len += byteTime.length;
        System.arraycopy(byteX, 0, byteMessage, len, byteX.length);
        len += byteX.length;
        System.arraycopy(byteY, 0, byteMessage, len, byteY.length);
        len += byteY.length;
        System.arraycopy(byteID, 0, byteMessage, len, byteID.length);
        len += byteID.length;
        for (Bullet bullet : craft.getNewBullets()) {
            System.arraycopy(byteID, 0, byteMessage, len, byteID.length);
            len += byteID.length;
            ByteBuffer buffXBullets = ByteBuffer.allocate(4);
            byte[] byteXBullet = buffXBullets.order(ByteOrder.LITTLE_ENDIAN).putInt((int) bullet.getX()).array();
            System.arraycopy(byteXBullet, 0, byteMessage, len, byteXBullet.length);
            len += byteXBullet.length;
            ByteBuffer buffYBullets = ByteBuffer.allocate(4);
            byte[] byteYBullet = buffYBullets.order(ByteOrder.LITTLE_ENDIAN).putInt((int) bullet.getY()).array();
            System.arraycopy(byteYBullet, 0, byteMessage, len, byteYBullet.length);
            len += byteYBullet.length;
            ByteBuffer buffRotationBullets = ByteBuffer.allocate(4);
            byte[] byteRotationBullet = buffRotationBullets.order(ByteOrder.LITTLE_ENDIAN).putInt((int) bullet.getRotation()).array();
            System.arraycopy(byteRotationBullet, 0, byteMessage, len, byteRotationBullet.length);
            len += byteRotationBullet.length;
            ByteBuffer buffTimeBullets = ByteBuffer.allocate(8);
            byte[] byteTimeBullet = buffTimeBullets.order(ByteOrder.LITTLE_ENDIAN).putLong(bullet.getTime()).array();
            System.arraycopy(byteTimeBullet, 0, byteMessage, len, byteTimeBullet.length);
            len += byteTimeBullet.length;
        }
        craft.setNewBullets(new ArrayList<Bullet>());
        DatagramPacket packet = new DatagramPacket(byteMessage, byteMessage.length, address, port);
        try {
            socket.send(packet);
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                socket.receive(dpacket);
                String msg = new String(buffer, 0, dpacket.getLength());
                dpacket.setLength(buffer.length);
                messagesQueue.add(msg);
            }
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            close();
        }
    }

    public void close() {
        socket.close();
    }
}
