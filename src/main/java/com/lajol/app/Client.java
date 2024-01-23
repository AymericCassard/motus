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
            new ObjectOutputStream(targetSocket.getOutputStream()).writeObject("!");
            
            //ecoute pour le mot du serveur jeu
            ObjectInputStream inputStream = new ObjectInputStream(targetSocket.getInputStream());
            String response = (String) inputStream.readObject();

            // WIN
            if (response == "SUCCESS"){
                ClientSocket.close();
            }
            //suite
            targetSocket.close();
            targetSocket = null;
            targetSocket = new Socket();

            targetSocket.connect(target);
            System.out.println(": "+ response);
            ObjectOutputStream outputStream = new ObjectOutputStream(targetSocket.getOutputStream());
            System.out.print("Devine le mot :");
            outputStream.writeObject(ecrire.next());

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}
