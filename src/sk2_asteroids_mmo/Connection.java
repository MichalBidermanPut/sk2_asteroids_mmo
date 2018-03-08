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
    private int portLogin;
    private int portUpdate;
    private DatagramSocket socketLogin;
    private byte[] bufferLogin;
    private DatagramPacket dpacketLogin;

    private DatagramSocket socketUpdate;
    private byte[] bufferUpdate;
    private DatagramPacket dpacketUpdate;

    public LinkedList<String> messagesQueueLogin;
    public LinkedList<String> messagesQueueUpdate;

    public Connection(InetAddress address, int portLogin, int portUpdate) {
        this.address = address;
        this.portLogin = portLogin;
        this.portUpdate = portUpdate;
        this.bufferLogin = new byte[256];
        this.bufferUpdate = new byte[16384];
        messagesQueueLogin = new LinkedList<>();
        messagesQueueUpdate = new LinkedList<>();
        try {
            this.socketLogin = new DatagramSocket(portLogin);
            this.socketUpdate = new DatagramSocket(portUpdate);
        } catch (SocketException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        dpacketLogin = new DatagramPacket(this.bufferLogin, this.bufferLogin.length);
        dpacketUpdate = new DatagramPacket(this.bufferUpdate, this.bufferUpdate.length);
    }

    public void sendLogin(String username, int lenUsername) {
        ByteBuffer buffPortUpdate = ByteBuffer.allocate(4);
        ByteBuffer buffLenUsername = ByteBuffer.allocate(4);
        byte[] bytePortUpdate = buffPortUpdate.order(ByteOrder.LITTLE_ENDIAN).putInt(portUpdate).array();
        byte[] byteLenUsername = buffLenUsername.order(ByteOrder.LITTLE_ENDIAN).putInt(lenUsername).array();
        byte[] byteUsername = username.getBytes();
        byte[] bytePadding = new byte[256 - bytePortUpdate.length - byteLenUsername.length - byteUsername.length];
        for (int i = 0; i < bytePadding.length; i++) {
            bytePadding[i] = 0;
        }
        byte[] byteMessage = new byte[256];
        System.arraycopy(bytePortUpdate, 0, byteMessage, 0, bytePortUpdate.length);
        System.arraycopy(byteLenUsername, 0, byteMessage, bytePortUpdate.length, byteLenUsername.length);
        System.arraycopy(byteUsername, 0, byteMessage, (bytePortUpdate.length + byteLenUsername.length), byteUsername.length);
        System.arraycopy(bytePadding, 0, byteMessage, (bytePortUpdate.length + byteLenUsername.length + byteUsername.length), bytePadding.length);
        DatagramPacket packet = new DatagramPacket(byteMessage, byteMessage.length, address, portLogin);
        try {
            socketLogin.send(packet);
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
        DatagramPacket packet = new DatagramPacket(byteMessage, byteMessage.length, address, portUpdate);
        try {
            socketUpdate.send(packet);
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            socketLogin.receive(dpacketLogin);
            String msg = new String(bufferLogin, 0, dpacketLogin.getLength());
            dpacketLogin.setLength(bufferLogin.length);
            messagesQueueLogin.add(msg);
            while (true) {
                socketUpdate.receive(dpacketUpdate);
                msg = new String(bufferUpdate, 0, dpacketUpdate.getLength());
                dpacketUpdate.setLength(bufferUpdate.length);
                messagesQueueUpdate.add(msg);
            }
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            close();
        }
    }

    public void close() {
        socketLogin.close();
        socketUpdate.close();
    }
}
