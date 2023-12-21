package test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Agence {

    private static final String YELLOW = "\033[33m";
    private static final String DEFAULT = "\033[0m";

    public Voyage ajouter_voyage(ArrayList<Voyage> Liste) {
        Voyage v = new Voyage(Liste.size() + 1);
        ajouterVoyageTXT(v.voyageTXT());
        return v;
    }

    public void afficher_voyage(ArrayList<Voyage> Liste) {
        System.out.println("Liste des voyages : ");
        System.out.println("+-----+--------------------+--------------------+-----+---------------+------+----------+");
        System.out.printf("|" + YELLOW + "%5s" + DEFAULT, "ID");
        System.out.printf("|" + YELLOW + "%20s" + DEFAULT, "DESTINATION");
        System.out.printf("|" + YELLOW + "%20s" + DEFAULT, "HOTEL");
        System.out.printf("|" + YELLOW + "%5s" + DEFAULT, "JOURS");
        System.out.printf("|" + YELLOW + "%15s" + DEFAULT, "Date Depart");
        System.out.printf("|" + YELLOW + "%5s" + DEFAULT, "PLACES");
        System.out.printf("|" + YELLOW + "%10s" + DEFAULT + "|\n", "PRIX");
        System.out.println("+-----+--------------------+--------------------+-----+---------------+------+----------+");
        for (int i = 0; i < Liste.size(); i++) {
            Liste.get(i).AfficherDetails();
        }
    }

    public void supprimer_voyage(ArrayList<Voyage> Liste, ArrayList<Reservation> LR) {
        afficher_voyage(Liste);
        Scanner sc = new Scanner(System.in);
        System.out.print("Saisir ID du voyage à supprimer : ");
        int id = sc.nextInt();
        for (int i = 0; i < Liste.size(); i++) {
            Voyage v = Liste.get(i);
            if (v.getId() == id) {
                Liste.remove(i);
                effacer_reservation(LR,id);
                System.out.println("Voyage supprimé avec succès");
                break; // Added break to exit the loop after deleting the voyage
            }
        }
    }
    
    
    
    private void ajouterVoyageTXT (String contenu) {
      try {
       	  String filePath = System.getProperty("user.dir") + "\\src\\voyages.txt";
       	  FileWriter fileWriter = new FileWriter(filePath, true);
       	  BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
       	  bufferedWriter.write(contenu);
       	  bufferedWriter.close();
      }
      catch (IOException e) {
    	  e.printStackTrace();
      }
    }
    
    public static void effacer_reservation (ArrayList<Reservation> LR, int idv) {
    	for(int i=0; i<LR.size();i++) {
    		if(LR.get(i).getV().getId()==idv) {
    			LR.remove(i);
    			if(i!=0) i--;
    		}
    	}
    }
    

}
