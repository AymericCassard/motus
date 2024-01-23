package com.lajol.app;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    public static void main(String[] args) {
        //scanner
        Scanner ecrire = new Scanner(System.in);
        try {
            Socket ClientSocket = new Socket(InetAddress.getLocalHost(), 9001);
            System.out.println("Attente de connexion au serveur...");
            //reception socket de connection au serveur jeu
            InetSocketAddress target = (InetSocketAddress) new ObjectInputStream(ClientSocket.getInputStream()).readObject();
            System.out.println(target);
            if (target == null){
                System.out.println("Pas de serveur disponible !");
            }

            Socket targetSocket = new Socket();

            targetSocket.connect(target);
            //ecoute pour le mot du serveur jeu
            ObjectInputStream inputStream = new ObjectInputStream(targetSocket.getInputStream());

            // WIN
            if ((String) inputStream.readObject() == "SUCCESS"){
                ClientSocket.close();
            }
            //suite
            System.out.println(": "+ inputStream.read());
            ObjectOutputStream outputStream = new ObjectOutputStream(targetSocket.getOutputStream());
            System.out.println("Devine le mot :");
            outputStream.writeObject(ecrire.next());

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}
