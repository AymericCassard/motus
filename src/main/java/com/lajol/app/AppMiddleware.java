package com.lajol.app;

import java.io.IOException;

import com.lajol.metier.Middleware;
import com.lajol.metier.ServeurJeu;

public class AppMiddleware {

  public static void main(String[] args) {
    try {
      Middleware middleware = new Middleware();
      while(true) {
        middleware.waitForClient();
      }
    } catch(IOException | ClassNotFoundException e) {
      System.out.println(e);
    }
  }
	
}
