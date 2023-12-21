package test;

import java.util.Scanner;
public class Client extends Personne{
private int num_carte=0;

public int getNum_carte() {
	return num_carte;
}

public void setNum_carte(int num_carte) {
	this.num_carte = num_carte;
}
public Client(String nom, int cin) {
	super(nom, cin);
}

public Client(int cin) {
	super(cin);
	Scanner sc = new Scanner(System.in);
	System.out.println("Veuillez entrer le numero de votre carte");
	this.num_carte = sc.nextInt();
}



@Override
public void Afficher() {
	// TODO Auto-generated method stub
	throw new UnsupportedOperationException("Unimplemented method 'Afficher'");
}

}