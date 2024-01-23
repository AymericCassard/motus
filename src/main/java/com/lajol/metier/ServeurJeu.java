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
  private String mot;
  
  private ServerSocket serverSocket;
  private Socket socket;
  private ObjectInputStream input;
  private ObjectOutputStream output;

  public ServeurJeu(int port) throws IOException {
    this.serverSocket = new ServerSocket(port);
    this.isGaming = false;
  }

  public String getMotFromDictionnaire() throws IOException, ClassNotFoundException {
    Socket DictionnaireSocket = new Socket(InetAddress.getLocalHost(), 9002);
    String sentMot = (String)new ObjectInputStream(DictionnaireSocket.getInputStream()).readObject();
    DictionnaireSocket.close();
    return sentMot;
  }

  public void respondToMiddleware() throws IOException {
    this.socket = serverSocket.accept();
    new ObjectOutputStream(this.socket.getOutputStream()).writeObject(this.isGaming);
    this.socket.close();
  }


}
