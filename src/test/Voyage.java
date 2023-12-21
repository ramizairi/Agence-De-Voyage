package test;
import java.util.Scanner;

public class Voyage {

    private int id;
    private String destination;
    private String hotel;
    private int nb_jour;
    private String date_depart;
    private int nb_place;
    private double prix;

    public Voyage(int id) {
        Scanner sc = new Scanner(System.in);
        System.out.println("****************AJOUT VOYAGE**************");
        this.id = id;
        System.out.print("Saisir Destination : ");
        this.destination = sc.next();
        System.out.print("Saisir Hotel : ");
        this.hotel = sc.next();
        System.out.print("Saisir nombre de jours : ");
        this.nb_jour = sc.nextInt();
        System.out.print("Saisir date de départ : ");
        this.date_depart = sc.next();
        System.out.print("Saisir nombre de places : ");
        this.nb_place = sc.nextInt();
        System.out.print("Saisir prix : ");
        this.prix = sc.nextDouble();
    }

    public Voyage(int id, String dest, String hotel, int nbj, String DateDepart, int nbp, double p) {
        this.id = id;
        this.destination = dest;
        this.hotel = hotel;
        this.nb_jour = nbj;
        this.date_depart = DateDepart;
        this.nb_place = nbp;
        this.prix = p;
    }

    public void AfficherDetails() {
        System.out.printf("|%5d", this.id);
        System.out.printf("|%20s", this.destination);
        System.out.printf("|%20s", this.hotel);
        System.out.printf("|%5d", this.nb_jour);
        System.out.printf("|%15s", this.date_depart);
        System.out.printf("|%6d", this.nb_place);
        System.out.printf("|%10.3f|\n", this.prix);
        System.out.println("+-----+--------------------+--------------------+-----+---------------+------+----------+");
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getHotel() {
		return hotel;
	}

	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

	public int getNb_jour() {
		return nb_jour;
	}

	public void setNb_jour(int nb_jour) {
		this.nb_jour = nb_jour;
	}

	public String getDate_depart() {
		return date_depart;
	}

	public void setDate_depart(String date_depart) {
		this.date_depart = date_depart;
	}

	public int getNb_place() {
		return nb_place;
	}

	public void setNb_place(int nb_place) {
		this.nb_place = nb_place;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	@Override
	public String toString() {
		return "Voyage [id=" + id + ", destination=" + destination + ", hotel=" + hotel + ", nb_jour=" + nb_jour
				+ ", date_depart=" + date_depart + ", nb_place=" + nb_place + ", prix=" + prix + "]";
	}

	public String voyageTXT () {
		String line = this.id+";"+this.destination+";"+this.hotel+";"+this.nb_jour+";"+this.date_depart+";"+this.nb_place+";"+this.prix+"\n";
		return line;
	}
		
}
