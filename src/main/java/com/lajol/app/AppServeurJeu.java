package com.lajol.app;

import java.io.IOException;

import com.lajol.metier.ServeurJeu;

public class AppServeurJeu {

  public static void main(String[] args) {

    ServeurJeu serveurJeu = null;
    try {
      serveurJeu = new ServeurJeu(9005);
      while (true) {
        serveurJeu.startListening();
      }
    } catch (IOException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }

  }

}
