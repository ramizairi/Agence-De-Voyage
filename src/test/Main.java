package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    static ArrayList <Client> ListeClient = new ArrayList <>() ;
	static ArrayList <Voyage> ListeVoyage = new ArrayList <>() ;
    static ArrayList <Reservation> LR = new ArrayList <>() ;
    
    static String loginUsername;
    
    static String filePath = System.getProperty("user.dir") + "\\Authentification\\authClient.txt";

	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        chargerVoyages();
        chargerReservations();
        
        int choix1;
        
        ChargerClients(ListeClient);

        do {
            System.out.println("\n\t **********\tESPACE DE CONNECTION\t**********");
            System.out.println("\t Veillez choisir le numero correspondant à votre choix");
            System.out.println("\t 0 ) Quitter l'application");
            System.out.println("\t 1 ) Se connecter en tant que client");
            System.out.println("\t 2 ) Se connecter en tant que admin");
              choix1 = scanner.nextInt();
          
              switch (choix1) {
                  case 0:
                  System.out.println("\t Fermeture en cours ...");
                  pause(500);
                  System.out.println("\t 3");
                  pause(1000);
                  System.out.println("\t 2");
                  pause(1000);
                  System.out.println("\t 1");
                  pause(1000);
                  System.exit(0);
                      break;
                  case 1:
                    int choix;
                      do {
                            System.out.println("\n\t **********\tESPACE DE CONNECTION\t**********");
                            System.out.println("\t [1] Se Connecter ");
                            System.out.println("\t [2] Registre ");
                            System.out.println("\t [0] Retour au menu principal ");
                            choix = scanner.nextInt();
                      } while (choix != 1 && choix != 2 && choix != 0);
                      if (choix == 1 && loginClient(scanner)) {
                          MenuClient();
                      } else if (choix == 2 ) {
                        System.out.println("Register user");
                        registerUser(scanner);
                      } else {
                        main(args);
                      }
                      break;
                  case 2:
                      if (loginAdmin(scanner)) {
                        MenuAdmin();
                      }
                      break;
                  default:
              }
          } while(choix1 != 0 && choix1 != 1 && choix1 != 2);

	}
    //--MenuClient
    public static void MenuClient(){
        String choixAdherent;
        Scanner scanner = new Scanner(System.in);
        Site S = new Site();
        do {
            System.out.println("\n\t *****\tClasse De Menu\t*****");
            System.out.println("\t Veillez choisir le numero correspondant à votre choix");
            System.out.println("\t 0 ) Se Deconnecter");
            System.out.println("\t 1 ) Afficher Vos Reservation");
            System.out.println("\t 2 ) Ajouter Reservation");
            System.out.println("\t 3 ) Modifier Reservation");
            System.out.println("\t 4 ) Annuler Reservation");
            System.out.println("\t 5 ) Payer Reservation");
            choixAdherent = scanner.next();

            Agence ag = new Agence();
            switch (choixAdherent){
                case "0":  main(null); break;
                case "1": 
                    for(int i=0;i<LR.size();i++){
                    if(LR.get(i).getCin()==Integer.parseInt(loginUsername)) {
                        System.out.println(LR.get(i).toString());
                    } }
                    break;
                case "2":
                    ag.afficher_voyage(ListeVoyage);
                    System.out.println("\t Choisir l'ID du voyage :");
                    int idv = scanner.nextInt();
                    S.reserver(LR, idv, ListeVoyage, Integer.parseInt(loginUsername));
                    break;
                case "3":
                    S.modifier_reservation(LR, ListeVoyage, Integer.parseInt(loginUsername));
                    EcrireReservationsTXT(LR);
                    break;
                case "4":
                    S.annuler_reservation(LR, Integer.parseInt(loginUsername));
                    EcrireReservationsTXT(LR);
                    break;
                case "5":
                String choixSousMenu;
                do {
                        System.out.println("\n\t *****\tClasse De Menu\t*****");
                        System.out.println("\t Veillez choisir le numero correspondant à votre choix");
                        System.out.println("\t 0 ) Se Deconnecter");
                        System.out.println("\t 1 ) Payer Par Carte Bancaire");
                        System.out.println("\t 2 ) Payer Par Cash");
                        choixSousMenu = scanner.next();
                        switch (choixSousMenu) {
                            case "0":
                            MenuClient();
                                break;
                            case "1":
                                System.out.println("\t Donner Le Numero de carte");
                                int numcarte = scanner.nextInt();
                                System.out.println("\t Donner LA date d'experision");
                                int dateex = scanner.nextInt();
                                System.out.println("\t Donner Le CVV");
                                int cvv = scanner.nextInt();
                                Client c = ListeClient.get(rech_client(ListeClient,Integer.parseInt(loginUsername)));
                                c.setNum_carte(numcarte);
                                S.payer(LR, ListeVoyage, Integer.parseInt(loginUsername),c);
                                EcrireVoyagesTXT(ListeVoyage);
                                EcrireReservationsTXT(LR);
                                break;
                            case "2":
                                System.out.println("Merci d'aller à notre local le plutot possible pour garantir vos places !!!");
                                break;
                            default:
                                break;
                        }
                    } while (!choixSousMenu.equals("0"));
                    break;
                default:    System.out.println("\t Choix invalide! veillez réessayer"); pause(1000); break;
            }
        }while (!choixAdherent.equals("0"));
    }

    
    public static void MenuAdmin(){
        String choixAdherent;
        String choixSousMenu;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("\n\t *****\tClasse De Menu\t*****");
            System.out.println("\t Veillez choisir le numero correspondant à votre choix");
            System.out.println("\t 0 ) Se Deconnecter");
            System.out.println("\t 1 ) Gerer Voyage");
            System.out.println("\t 2 ) Gerer Reservation");
            System.out.println("\t 3 ) Gerer Clients");
            System.out.println("\t 4 ) Modifier les données personnelle");
            choixAdherent = scanner.next();
            switch (choixAdherent){
                case "0":  main(null); break;
                case "1":   
                    do {
                        System.out.println("\n\t *****\tClasse De Menu\t*****");
                        System.out.println("\t Veillez choisir le numero correspondant à votre choix");
                        System.out.println("\t 0 ) Se Deconnecter");
                        System.out.println("\t 1 ) Ajouter voyage");
                        System.out.println("\t 2 ) Supprimer Voyage");
                        System.out.println("\t 3 ) Afficher Voyages");
                        choixSousMenu = scanner.next();
                        Agence ag = new Agence();
                        switch (choixSousMenu) {
                            case "0":
                            MenuAdmin();
                                break;
                            case "1":
                                ListeVoyage.add(ag.ajouter_voyage(ListeVoyage));
                                break;
                            case "2":
                                ag.supprimer_voyage(ListeVoyage,LR);
                                EcrireVoyagesTXT(ListeVoyage);
                                break;
                            case "3":
                                ag.afficher_voyage(ListeVoyage);
                                break;
                        
                            default:
                                break;
                        }
                    } while (!choixSousMenu.equals("0"));
                ; break;
                case "2":
                do {
                        System.out.println("\n\t *****\tClasse De Menu\t*****");
                        System.out.println("\t Veillez choisir le numero correspondant à votre choix");
                        System.out.println("\t 0 ) Se Deconnecter");
                        System.out.println("\t 1 ) Ajouter Reservation");
                        System.out.println("\t 2 ) Supprimer Reservation");
                        System.out.println("\t 3 ) Afficher Reservation");
                        choixSousMenu = scanner.next();
                        Agence ag = new Agence();
                        switch (choixSousMenu) {
                            case "0":
                            MenuAdmin();
                                break;
                            case "1":
                                ListeVoyage.add(ag.ajouter_voyage(ListeVoyage));
                                break;
                            case "2":
                                ag.supprimer_voyage(ListeVoyage,LR);
                                break;
                            case "3":
                                ag.afficher_voyage(ListeVoyage);
                                break;
                        
                            default:
                                break;
                        }
                    } while (!choixSousMenu.equals("0"));
                    break;
                case "3":
                do {
                        System.out.println("\n\t *****\tClasse De Menu\t*****");
                        System.out.println("\t Veillez choisir le numero correspondant à votre choix");
                        System.out.println("\t 0 ) Se Deconnecter");
                        System.out.println("\t 1 ) Ajouter Client");
                        System.out.println("\t 2 ) Supprimer Client");
                        System.out.println("\t 3 ) Afficher Clients");
                        choixSousMenu = scanner.next();
                        switch (choixSousMenu) {
                            case "0":
                            MenuAdmin();
                                break;
                            case "1":
                                    AddClient(scanner);
                                break;
                            case "2":
                                    System.out.println("\t Donner Le CIN de Clients");
                                    String cin = scanner.next();
                                    Authentification.deleteClient(cin);
                                break;
                            case "3":
                                    Authentification.displayClients();
                                break;
                        
                            default:
                                break;
                        }
                    } while (!choixSousMenu.equals("0"));
                    break;
                default:    System.out.println("\t Choix invalide! veillez réessayer"); pause(1000); break;
            }
        }while (!choixAdherent.equals("0"));
    }
    //------Reserver
 
    //---Login client
  static boolean loginClient(Scanner scanner) {
    while (true) {
      System.out.println("\n\t **********\tESPACE DE CONNEXION\t**********");
      System.out.println("\t Entrer le CIN : ");
      loginUsername = scanner.next();
      System.out.println("\t Entrer le mot de passe : ");
      String loginPassword = scanner.next();
      
      if (Authentification.loginClient(loginUsername, loginPassword)) {
        System.out.print(ANSI_GREEN);
        System.out.println("Logged sucessfully");
        pause(1000);
        System.out.print(ANSI_RESET);
        return true;
      } else {
        System.out.print(ANSI_RED);
        System.out.println("User or password are incorrect. reessayer");
        pause(1000);
        System.out.print(ANSI_RESET);
      }
    }
  }
  //---Login Admin
  static boolean loginAdmin(Scanner scanner) {
    while (true) {
      System.out.println("\n\t **********\tESPACE DE CONNEXION\t**********");
      System.out.println("\t Entrer le CIN : ");
      String loginUsername = scanner.next();
      System.out.println("\t Entrer le mot de passe : ");
      String loginPassword = scanner.next();
      
      if (Authentification.loginAdmin(loginUsername, loginPassword)) {
        System.out.print(ANSI_GREEN);
        System.out.println("Logged sucessfully");
        pause(1000);
        System.out.print(ANSI_RESET);
        return true;
      } else {
        System.out.print(ANSI_RED);
        System.out.println("User or password are incorrect. reessayer");
        pause(1000);
        System.out.print(ANSI_RESET);
      }
    }
} 

  //---Register
  static boolean registerUser(Scanner scanner) {
    System.out.println("Enter CIN:");
    String newCIN = scanner.next();
    System.out.println("Enter Password:");
    String NewPassword = scanner.next();
    System.out.println("Enter Name:");
    String NewNom = scanner.next();

    boolean registrationSuccess = Authentification.registerUser(newCIN, NewPassword, NewNom);

    if (registrationSuccess) {
        System.out.println("Registration successful.");
        main(null);
    } else {
        System.out.println("Registration failed. Please try again.");
    }
    return registrationSuccess;
}
    static boolean AddClient(Scanner scanner) {
    System.out.println("Enter CIN:");
    String newCIN = scanner.next();
    System.out.println("Enter Password:");
    String NewPassword = scanner.next();
    System.out.println("Enter Name:");
    String NewNom = scanner.next();

    boolean registrationSuccess = Authentification.registerUser(newCIN, NewPassword, NewNom);

    if (registrationSuccess) {
        System.out.println("Registration successful.");
    } else {
        System.out.println("Registration failed. Please try again.");
    }
    return registrationSuccess;
    }
    //---Pause
  public static void pause(int timeToPause){
    try
    {
        Thread.sleep(timeToPause);
    }
    catch(InterruptedException ex)
    {
        Thread.currentThread().interrupt();
    }
  }   

  
  public static int rech_client (ArrayList<Client> LC, int cin) {
	  for(int i=0; i<LC.size();i++) {
		  if(LC.get(i).getCin()==cin) return i;
	  }
	  return LC.size();
  }
  
  public static void ChargerClients(ArrayList<Client> LC) {
	    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	        String line;

	        while ((line = reader.readLine()) != null) {
	            if (line.startsWith("CIN:")) {
	                String cin = line.substring(4).trim();

	                line = reader.readLine();
	                String password = line.substring(9).trim();

	                line = reader.readLine();
	                String name = line.substring(5).trim();
	                
	                LC.add(new Client(name,Integer.parseInt(cin)));
	            }
	        }
	    } catch (IOException ex) {
	        Logger.getLogger(Authentification.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
  
  
  public static void chargerVoyages () {
  	    try {
		 String filePath = System.getProperty("user.dir") + "\\src\\voyages.txt";
		 BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
			 String line;
			 while ((line = bufferedReader.readLine()) != null) {
		             String details[]= line.split(";");
		             Voyage v = new Voyage(Integer.parseInt(details[0]),details[1],details[2],Integer.parseInt(details[3]),details[4],Integer.parseInt(details[5]),Double.parseDouble(details[6]));
		             ListeVoyage.add(v);
		         }
		         bufferedReader.close();
		 
  	     }
	     catch (IOException e){
		         	 e.printStackTrace();
			 }
  }
		 
  
  
      public static void chargerReservations () {
	  	try {
			 String filePath = System.getProperty("user.dir") + "\\src\\reservations.txt";
			 BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
			 String line;
			 while ((line = bufferedReader.readLine()) != null) {
		             String details[]= line.split(";");
		             Reservation r = new Reservation(Integer.parseInt(details[0]),Integer.parseInt(details[1]),rech_voyage(Integer.parseInt(details[2]),ListeVoyage),Integer.parseInt(details[3]),Integer.parseInt(details[4]),Double.parseDouble(details[5]),Boolean.parseBoolean(details[6]),Integer.parseInt(details[7]));
		             LR.add(r);
		         }
		         bufferedReader.close();
			 }
			 catch (IOException e){
		         	 e.printStackTrace();
			 }
	  }
      
        public static Voyage rech_voyage(int id_voyage, ArrayList<Voyage> LV) {
    		for(int i=0;i<LV.size();i++) {
    			if(LV.get(i).getId()==id_voyage) {
    				return LV.get(i);
    			}
    		}
    		return null;
    	}
        
        public static void EcrireReservationsTXT(ArrayList<Reservation> arrayList) {
            try {
   			    String filePath = System.getProperty("user.dir") + "\\src\\reservations.txt";
                FileWriter fileWriter = new FileWriter(filePath);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                for (Reservation r : arrayList) {
                    bufferedWriter.write(r.reservationTXT());
                }
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        public static void EcrireVoyagesTXT(ArrayList<Voyage> arrayList) {
            try {
   			    String filePath = System.getProperty("user.dir") + "\\src\\voyages.txt";
                FileWriter fileWriter = new FileWriter(filePath);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                for (Voyage v : arrayList) {
                    bufferedWriter.write(v.voyageTXT());
                }
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        public static boolean isFileEmpty(String filePath) {
            File file = new File(filePath);
            return file.length() == 0;
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
