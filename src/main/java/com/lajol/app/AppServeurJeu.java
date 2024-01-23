package com.lajol.app;

import java.io.IOException;

import com.lajol.metier.ServeurJeu;

public class AppServeurJeu {

  public static void main(String[] args) {
    try {
      ServeurJeu serveurJeu = new ServeurJeu(9005);
      System.out.println(serveurJeu.getMotFromDictionnaire());
    } catch(IOException | ClassNotFoundException e) {
      System.out.println(e);
    }
  }
	
}
