package test;
import java.util.Scanner;

public class Reservation {
	private int id;
	private int cin;
	private Voyage v;
	private int nb_adulte;
	private int nb_enfant;
	private double somme;
	private Boolean paiement = false;
	private int nb_personne=nb_adulte+nb_enfant;
	
	
	public int getNb_personne() {
		return nb_personne;
	}
	public void setNb_personne(int nb_personne) {
		this.nb_personne = nb_personne;
	}
	public Boolean getPaiement() {
		return paiement;
	}
	public void setPaiement(Boolean paiement) {
		this.paiement = paiement;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCin() {
		return cin;
	}
	public void setCin(int cin) {
		this.cin = cin;
	}
	public Voyage getV() {
		return v;
	}
	public void setV(Voyage v) {
		this.v = v;
	}
	public int getNb_adulte() {
		return nb_adulte;
	}
	public void setNb_adulte(int nb_adulte) {
		this.nb_adulte = nb_adulte;
	}
	public int getNb_enfant() {
		return nb_enfant;
	}
	public void setNb_enfant(int nb_enfant) {
		this.nb_enfant = nb_enfant;
	}
	public double getSomme() {
		return somme;
	}
	public void setSomme(double somme) {
		this.somme = somme;
	}
	
	public Reservation(int id, int cin, Voyage v, int nb_adulte, int nb_enfant, int nb_personne) {
		super();
		this.id = id;
		this.cin = cin;
		this.v = v;
		this.nb_adulte = nb_adulte;
		this.nb_enfant = nb_enfant;
		this.nb_personne=nb_personne;
		this.somme = (this.nb_adulte * this.v.getPrix()) + (this.nb_enfant * 0.7 * this.v.getPrix());
	}
	
	
	
	public Reservation(int id, int cin, Voyage v, int nb_adulte, int nb_enfant, double somme, Boolean paiement,
			int nb_personne) {
		super();
		this.id = id;
		this.cin = cin;
		this.v = v;
		this.nb_adulte = nb_adulte;
		this.nb_enfant = nb_enfant;
		this.somme = somme;
		this.paiement = paiement;
		this.nb_personne = nb_personne;
	}
	
	
	public Reservation(int id, int cin, int nb_personne, Voyage v) {
		super();
		Scanner sc=new Scanner(System.in);
		this.id = id;
		this.cin = cin;
		this.v = v;
		System.out.println("Entrer le nombre des adultes");
		this.nb_adulte = sc.nextInt();
		System.out.println("Entrer le nombre des enfants");
		this.nb_enfant = sc.nextInt();
	}
	
	
	@Override
	public String toString() {
		return "Reservation [id=" + id + ", cin=" + cin + ", v=" + v.toString() + ", nb_adulte=" + nb_adulte + ", nb_enfant="
				+ nb_enfant + ", somme=" + somme + "Paiement="+paiement+ "]";
	}
	
	
	public String reservationTXT () {
		String line = this.id+";"+this.cin+";"+this.v.getId()+";"+this.nb_adulte+";"+this.nb_enfant+";"+this.somme+";"+this.paiement+";"+this.nb_personne+"\n";
		return line;
	}
	
}
