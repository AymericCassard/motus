package com.lajol.app;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;


public class Client {
    public static void main(String[] args) {
        try {
            Socket ClientSocket = new Socket(InetAddress.getLocalHost(), 9001);
            System.out.println("Attente de connexion au serveur...");
            //reception socket de connection au serveur jeu
            InetSocketAddress target = (InetSocketAddress) new ObjectInputStream(ClientSocket.getInputStream()).readObject();
            Socket targetSocket = new Socket();
            targetSocket.connect(target);

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}
