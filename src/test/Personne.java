package test;

import java.util.Scanner;
public abstract class Personne {
protected String nom;
protected int cin;

public String getNom() {
	return nom;
}
public void setNom(String nom) {
	this.nom = nom;
}
public int getCin() {
	return cin;
}
public void setCin(int cin) {
	this.cin = cin;
}


public Personne(String nom, int cin) {
	super();
	this.nom = nom;
	this.cin = cin;
}
public Personne(int cin) {
	super();
	Scanner sc = new Scanner(System.in);
	System.out.println("veuillez saisir votre nom");
	this.nom = sc.next();
	this.cin = cin;
}
public abstract void Afficher();
}