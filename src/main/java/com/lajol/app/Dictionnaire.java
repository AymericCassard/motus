package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dictionnaire {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9002);
            System.out.println("Serveur en attente de connexion...");

            //attente serveur jeu
            Socket socketServeurJeu = serverSocket.accept();
            System.out.println("connexion établie");

            //intercepté msg du serveur jeu
            BufferedReader inputMSG = new BufferedReader(new InputStreamReader(socketServeurJeu.getInputStream()));

            //envoyer msg au serveur jeu
            PrintWriter output = new PrintWriter(socketServeurJeu.getOutputStream(), true);

            //recuperation du mots
            File FichierMots = new File("ListeMots.txt");
            String leMot = GetMot(FichierMots);

            output.write(leMot);
            socketServeurJeu.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String GetMot(File file) {
        List<String> lines = readLinesFromFile(file);
        if (lines.isEmpty()) {
            return "";
        }
        Random random = new Random();
        int randomIndex = random.nextInt(lines.size());
        return lines.get(randomIndex);
    }
    private static List<String> readLinesFromFile(File file) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }


}
