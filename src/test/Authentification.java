package test;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
public class Authentification {

  static File folder = new File("Authentification");
  static File authClient = new File(folder, "authClient.txt");
  static File authAdmin = new File(folder, "authAdmin.txt");
  static File historyFile = new File(folder, "AuthHistory.txt");

  static {
    createFolder();
    createFileIfNotExists(authClient);
    createFileIfNotExists(authAdmin);
    createFileIfNotExists(historyFile);
  }

  public static void createFolder() {
    if (!folder.exists()) {
      folder.mkdirs();
    }
  }

  public static void createFileIfNotExists(File file) {
    try {
      if (!file.exists()) {
        file.createNewFile();
        System.out.println(file.getName() + " created");
      }
    } catch (IOException ex) {
      Logger.getLogger(Authentification.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public static boolean registerUser(String newCIN, String newPassword, String newNom) {
    addData(authClient, newCIN, newPassword, newNom);
    addToHistory(historyFile, "Registered user: " + newCIN + " At  " + obtenirTempsReel());
    return true;
}

  public static boolean loginClient(String loginUsername, String loginPassword) {
    addToHistory(historyFile, "Login attempt for Client: " + loginUsername + " At: " + obtenirTempsReel());
    return checkData(authClient, loginUsername, loginPassword);
  }
    public static boolean loginAdmin(String loginUsername, String loginPassword) {
    addToHistory(historyFile, "Login attempt for Admin: " + loginUsername + " At: " + obtenirTempsReel());
    return checkData(authAdmin, loginUsername, loginPassword);
  }

  static void addData(File file, String usr, String pswd, String name) {
    try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
        raf.seek(raf.length());
        if (raf.length() > 0) {
            raf.writeBytes("\r");
        }
        raf.writeBytes("CIN:" + usr + "\r\n");
        raf.writeBytes("Password:" + pswd + "\r\n");
        raf.writeBytes("Name:" + name +"\r\n");
    } catch (IOException ex) {
        Logger.getLogger(Authentification.class.getName()).log(Level.SEVERE, null, ex);
    }
}

static boolean checkData(File file, String cin, String pswd) {
    try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
        while (raf.getFilePointer() < raf.length()) {
            String line = raf.readLine();
            String[] parts = line.split(":");
            if (parts.length == 2 && "CIN".equals(parts[0].trim()) && cin.equals(parts[1].trim())) {
                line = raf.readLine(); // Move to the next line
                parts = line.split(":");
                if (parts.length == 2 && "Password".equals(parts[0].trim())) {
                    String storedPassword = parts[1].trim();
                    if (pswd.equals(storedPassword)) {
                        return true;
                    }
                }
            }
            raf.readLine();
        }
        return false;
    } catch (IOException ex) {
        Logger.getLogger(Authentification.class.getName()).log(Level.SEVERE, null, ex);
        return false;
    }
}



  static void addToHistory(File file, String data) {
    try (FileWriter fw = new FileWriter(file, true)) {
      fw.write(data + "\r\n");
    } catch (IOException ex) {
      Logger.getLogger(Authentification.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  public static String obtenirTempsReel() {
    Date date = new Date();
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    String tempsReel = format.format(date);

    return tempsReel;
}



 public static void displayClients() {
    try (BufferedReader reader = new BufferedReader(new FileReader(authClient))) {
        String line;

		System.out.println("**LES INFORMATIONS DES CLIENTS**\n");
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("CIN:")) {
                String cin = line.substring(4).trim();
                System.out.println("\tClient CIN: " + cin);

                // Read and display Password
                line = reader.readLine();
                String password = line.substring(9).trim();
                System.out.println("\tMot de Passe: " + password);

                // Read and display Name
                line = reader.readLine();
                String name = line.substring(5).trim();
                System.out.println("\tNom: " + name + "\n");
               
            }
        }
    } catch (IOException ex) {
        Logger.getLogger(Authentification.class.getName()).log(Level.SEVERE, null, ex);
    }
}
//-------Supprimer Client
public static boolean deleteClient(String cinToDelete) {
	
    File tempFile = new File(folder, "tempAuthClient.txt");

    try (BufferedReader reader = new BufferedReader(new FileReader(authClient));
         BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

        String line;
        String previousLine = null;
        boolean clientFound = false;

        while ((line = reader.readLine()) != null) {
            if (line.startsWith("CIN:") && line.substring(4).trim().equals(cinToDelete)) {
                // Skip the lines for the client to delete
                clientFound = true;
                reader.readLine(); // Skip the current line with CIN to delete
                reader.readLine(); // Skip the line after CIN
                reader.readLine(); // Skip the line with Name:rami
            } else {
                // Write the line to the temporary file
                if (previousLine != null) {
                    writer.write(previousLine + "\n");
                }
                previousLine = line;
            }
        }

        if (!clientFound) {
            System.out.println("Client with CIN " + cinToDelete + " not found.");
            return false;
        }

        // Write the last line to the temporary file
        if (previousLine != null) {
            writer.write(previousLine + "\n");
        }

    } catch (IOException ex) {
        Logger.getLogger(Authentification.class.getName()).log(Level.SEVERE, null, ex);
        return false;
    }

    // Supprimer l'ancien fichier authClient
    if (!authClient.delete()) {
        System.out.println("Failed to delete the file " + authClient.getName());
        return false;
    }

    // Renommer le fichier temporaire en authClient
    if (!tempFile.renameTo(authClient)) {
        System.out.println("Failed to rename the file to " + authClient.getName());
        return false;
    }

    System.out.println("Client with CIN " + cinToDelete + "has been deleted.");
    return true;
}


}

