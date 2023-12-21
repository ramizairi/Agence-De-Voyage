package test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Site {

Scanner sc=new Scanner(System.in);

public void reserver(ArrayList<Reservation> LR, int id_voyage, ArrayList<Voyage> LV, int cin) {
	if(rech_voyage(id_voyage, LV)==null) {
		System.out.println("on n'a pas de voyage avec le numéro "+id_voyage);
	}
	else {
		System.out.println("Entrer le nombre des adultes");
		int nb_adulte = sc.nextInt();
		System.out.println("Entrer le nombre des enfants");
		int nb_enfant = sc.nextInt();
		if((nb_adulte+nb_enfant)<=LV.get(id_voyage-1).getNb_place()) {
		Reservation r=new Reservation(LR.size()+1, cin, rech_voyage(id_voyage, LV), nb_adulte, nb_enfant,nb_adulte+nb_enfant);
		LR.add(r);
		System.out.println("Votre reservation est la reservation num: "+r.getId()+" est confirmé sous le somme totale: "+r.getSomme());
		ajouterReservationTXT(r.reservationTXT());
		}
		
		else {
			System.out.println("On n'a pas "+(nb_adulte+nb_enfant)+" places dans ce voyage");
		}
	}
}
public void modifier_reservation(ArrayList<Reservation> LR, ArrayList<Voyage> LV, int cin) {
	Boolean x=false;
	ArrayList<Integer> LI=new ArrayList<>();
	
	for(int i=0;i<LR.size();i++){
		if(LR.get(i).getCin()==cin) {
			x=true;
			System.out.println(LR.get(i).toString());
			LI.add(LR.get(i).getId());
		}
	}
	if(x==false) {
		System.out.println("vous n'avez pas de réservations");
	}
	else {
		int h;
		do {
			System.out.println("entrer le numéro de réservation à modifier");
			h=sc.nextInt();
		}while(rech_reservation(LI, h)==false);
		if(LR.get(h-1).getPaiement()==true) {
			System.out.println("vous avez déjà payé vous ne pouvez pas modifier votre reservation");
		}
		else {
		System.out.println("Entrer le nombre des adultes");
		int nb_adulte = sc.nextInt();
		System.out.println("Entrer le nombre des enfants");
		int nb_enfant = sc.nextInt();
		if((nb_adulte+nb_enfant)<=LV.get(LR.get(h-1).getV().getId()-1).getNb_place()) {
			LR.get(h-1).setSomme((nb_enfant*0.7*LV.get(LR.get(h-1).getId()-1).getPrix())+(nb_adulte*LV.get(LR.get(h-1).getId()-1).getPrix()));
			LR.get(h-1).setNb_enfant(nb_enfant);
			LR.get(h-1).setNb_adulte(nb_adulte);
			System.out.println("modification effectuée! votre reservation est "+LR.get(h-1).toString());
		}
		else {
			System.out.println("on n'a pas "+(nb_adulte+nb_enfant)+" places disponibles dans ce voyage");
		}
	}}
}
public void annuler_reservation(ArrayList<Reservation> LR, int cin) {
	Boolean x=false;
	ArrayList<Integer> LI=new ArrayList<>();
	
	for(int i=0;i<LR.size();i++){
		if(LR.get(i).getCin()==cin) {
			x=true;
			System.out.println(LR.get(i).toString());
			LI.add(LR.get(i).getId());
		}
	}
	if(x==false) {
		System.out.println("vous n'avez pas de réservations");
	}
	else {
		int h;
		do {
			System.out.println("entrer le numéro de réservation à modifier");
			h=sc.nextInt();
		}while(rech_reservation(LI, h)==false);
		if(LR.get(h-1).getPaiement()==true) {
			System.out.println("vous avez déjà payé vous ne pouvez pas modifier votre reservation");
		}
		else {
			LR.remove(h-1);
			System.out.println("votre reservation est annulée");
			}
		}
		
}


public void payer(ArrayList<Reservation> LR, ArrayList<Voyage> LV, int cin, Client C) {
	Boolean x=false;
	ArrayList<Integer> LI=new ArrayList<>();
	
	for(int i=0;i<LR.size();i++){
		if(LR.get(i).getCin()==cin) {
			x=true;
			System.out.println(LR.get(i).toString());
			LI.add(LR.get(i).getId());
		}
	}
	if(x==false) {
		System.out.println("vous n'avez pas de réservations");
	}
	else {
		int h;
		do {
			System.out.println("entrer le numéro de réservation à payer");
			h=sc.nextInt();
		}while(rech_reservation(LI, h)==false);
		if(LR.get(h-1).getPaiement()==true) {
			System.out.println("vous avez déjà payé cette reservation");
		}
		else {
			LR.get(h-1).setPaiement(true);
			LV.get(LR.get(h-1).getV().getId()-1).setNb_place(LV.get(LR.get(h-1).getV().getId()-1).getNb_place()-LR.get(h-1).getNb_personne());
			System.out.println("le montant "+LR.get(h-1).getSomme()+" est été crédité de votre carte " + C.getNum_carte());
		}
	}
}

public Voyage rech_voyage(int id_voyage, ArrayList<Voyage> LV) {
	for(int i=0;i<LV.size();i++) {
		if(LV.get(i).getId()==id_voyage) {
			return LV.get(i);
		}
	}
	return null;
}

public Boolean rech_reservation(ArrayList<Integer> LI, int x) {
	for(int i=0;i<LI.size();i++) {
		if(LI.get(i)==x) {
			return true;
		}
	}
	return false;
}

public int rech_carte(ArrayList<Client> LC, int cin) {
	int x=0;
	for(int i=0; i<LC.size();i++) {
		if(LC.get(i).getCin()==cin) {
			x=LC.get(i).getNum_carte();
		}
	}
	return x;
}

private void ajouterReservationTXT (String contenu) {
    try {
     	  String filePath = System.getProperty("user.dir") + "\\src\\reservations.txt";
     	  FileWriter fileWriter = new FileWriter(filePath, true);
     	  BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
     	  bufferedWriter.write(contenu);
     	  bufferedWriter.close();
    }
    catch (IOException e) {
  	  e.printStackTrace();
    }
  }


}