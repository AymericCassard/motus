package com.lajol.metier;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Middleware {

  private ServerSocket serverSocket;
  private Socket socket;
  private ObjectInputStream input;
  private ObjectOutputStream output;

  public Middleware() {
    try {
      this.serverSocket = new ServerSocket(9001);
    } catch(IOException e) {
      System.out.println(e);
    }

  }

  public void waitForClient() throws IOException, ClassNotFoundException {
    this.socket = serverSocket.accept();
    System.out.println(getServeurJeuAddress());
    new ObjectOutputStream(socket.getOutputStream()).writeObject(getServeurJeuAddress());
    this.socket.close();
  }

  public InetSocketAddress getServeurJeuAddress() throws IOException, ClassNotFoundException, UnknownHostException {
    int serveurJeuPort = 9005;
    for(int i = 0; i < 2; i++) {
      Socket serveurJeuSocket = new Socket(InetAddress.getLocalHost(), serveurJeuPort );
      new ObjectOutputStream(serveurJeuSocket.getOutputStream()).writeObject("?");
      boolean available = (boolean)new ObjectInputStream(serveurJeuSocket.getInputStream()).readObject();
      System.out.println(available);
      serveurJeuSocket.close();
      if(available) {
        return new InetSocketAddress(InetAddress.getLocalHost(), serveurJeuPort );
      }
    }
    return null;
  }
}
