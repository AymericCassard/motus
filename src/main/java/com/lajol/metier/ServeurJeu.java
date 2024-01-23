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
    String sentMot = (String)new ObjectInputStream(DictionnaireSocket.getInputStream()).readObject();
    DictionnaireSocket.close();
    return sentMot;
  }

  public void startPartie() throws IOException, ClassNotFoundException {
    this.toursClient = 1;
    this.isGaming = true;
    this.motDictionnaire = getMotFromDictionnaire();
    StringBuilder hiddenMot = new StringBuilder(motDictionnaire.charAt(0));
    for (int i = 1; i < motDictionnaire.length(); i++) {
      hiddenMot.append('_');
    }
    this.progressMot = hiddenMot.toString();
    new ObjectOutputStream(this.socket.getOutputStream()).writeObject(progressMot);
  }

  public void startListening() throws IOException,ClassNotFoundException {
    this.socket = serverSocket.accept();
    String response = (String)new ObjectInputStream(this.socket.getInputStream()).readObject();
    if (response.equals("?")) {
      //C'est un Middleware
      new ObjectOutputStream(this.socket.getOutputStream()).writeObject(this.isGaming);
    } else {
      if(isGaming) {
        
      } else {
        startPartie();
      }
    }
    this.socket.close();
    this.socket = null;
  }

}
