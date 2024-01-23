package com.lajol.app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Score {
    ServerSocket serverSocket;

    {
        try {
            serverSocket = new ServerSocket(9004);
            System.out.println("Serveur en attente de connexion...");
            //en cas de connexion on recois un score = nom / score
            //attente serveur jeu
            Socket socketServeurJeu = serverSocket.accept();
            System.out.println("connexion établie , inscription du score");


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
