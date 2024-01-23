package com.lajol.metier;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurJeu {

  private boolean isGaming;
  private String motDictionnaire;
  private String progressMot;
  private int toursClient;

  private ServerSocket serverSocket;
  private Socket socket;
  private ObjectInputStream input;
  private ObjectOutputStream output;

  public ServeurJeu(int port) throws IOException {
    this.serverSocket = new ServerSocket(port);
    this.isGaming = false;
    this.toursClient = 1;
  }

  public String getMotFromDictionnaire() throws IOException, ClassNotFoundException {
    Socket DictionnaireSocket = new Socket(InetAddress.getLocalHost(), 9002);
    String sentMot = (String) new ObjectInputStream(DictionnaireSocket.getInputStream()).readObject();
    System.out.println(sentMot);
    DictionnaireSocket.close();
    return sentMot;
  }

  public void startPartie() throws IOException, ClassNotFoundException {
    this.toursClient = 1;
    this.isGaming = true;
    this.motDictionnaire = getMotFromDictionnaire();
    StringBuilder hiddenMot = new StringBuilder(motDictionnaire.charAt(0) + "");
    System.out.println(hiddenMot);
    for (int i = 1; i < motDictionnaire.length(); i++) {
      hiddenMot.append('_');
    }
    this.progressMot = hiddenMot.toString();
    System.out.println(progressMot);
    System.out.println(progressMot.length());
    System.out.println(motDictionnaire.length());
    new ObjectOutputStream(this.socket.getOutputStream()).writeObject(progressMot);
  }

  public void processTour(String response) throws IOException, ClassNotFoundException {
    if (toursClient < 7) {
      if (!checkVictory(response)) {
        StringBuilder hiddenMot = new StringBuilder(progressMot);
        for (int i = 1; i < response.length(); i++) {
          if (motDictionnaire.charAt(i) == response.charAt(i)) {
            hiddenMot.setCharAt(i, motDictionnaire.charAt(i));
          }
        }
        new ObjectOutputStream(this.socket.getOutputStream()).writeObject(hiddenMot.toString());
        this.toursClient++;
      } else {
        new ObjectOutputStream(this.socket.getOutputStream()).writeObject("SUCCESS");
      }
    } else {
      new ObjectOutputStream(this.socket.getOutputStream()).writeObject("FAIL");
      //reset la condition startListening
      this.isGaming = false;
    }
  }

  public boolean checkVictory(String motClient) {
    return motClient.equals(motDictionnaire);
  }

  public void startListening() throws IOException, ClassNotFoundException {
    this.socket = serverSocket.accept();
    String response = (String) new ObjectInputStream(this.socket.getInputStream()).readObject();
    System.out.println(response);
    if (response.equals("?")) {
      // C'est un Middleware
      new ObjectOutputStream(this.socket.getOutputStream()).writeObject(!this.isGaming);
    } else {
      if (isGaming) {
        processTour(response);
      } else {
        startPartie();
      }
    }
    this.socket.close();
    this.socket = null;
  }

}
