package fr.unilim.iut.spaceinvaders.metier;

import java.util.ArrayList;
import java.util.List;

import fr.unilim.iut.spaceinvaders.moteurjeu.Commande;
import fr.unilim.iut.spaceinvaders.moteurjeu.Jeu;
import fr.unilim.iut.spaceinvaders.utils.DebordementEspaceJeuException;
import fr.unilim.iut.spaceinvaders.utils.HorsEspaceJeuException;
import fr.unilim.iut.spaceinvaders.utils.MissileException;

public class SpaceInvaders implements Jeu {

	int longueur;
	int hauteur;
	Vaisseau vaisseau;
	List<Missile> missiles = new ArrayList<>();
	List<Envahisseur> envahisseurs = new ArrayList<>();
	Collision collision = new Collision();
	boolean estFini = false;

	public SpaceInvaders(int longueur, int hauteur) {
		this.longueur = longueur;
		this.hauteur = hauteur;
	}

	public void positionnerUnNouveauVaisseau(Dimension dimension, Position position, int vitesse) {

		int x = position.abscisse();
		int y = position.ordonnee();

		if (!estDansEspaceJeu(x, y))
			throw new HorsEspaceJeuException("La position du vaisseau est en dehors de l'espace jeu");

		int longueurVaisseau = dimension.longueur();
		int hauteurVaisseau = dimension.hauteur();

		if (!estDansEspaceJeu(x + longueurVaisseau - 1, y))
			throw new DebordementEspaceJeuException(
					"Le vaisseau déborde de l'espace jeu vers la droite à cause de sa longueur");
		if (!estDansEspaceJeu(x, y - hauteurVaisseau + 1))
			throw new DebordementEspaceJeuException(
					"Le vaisseau déborde de l'espace jeu vers le bas à cause de sa hauteur");

		vaisseau = new Vaisseau(dimension, position, vitesse);
	}

	public void positionnerUnNouvelEnvahisseur(Dimension dimension, Position position, int vitesse) {
		int x = position.abscisse();
		int y = position.ordonnee();

		if (!estDansEspaceJeu(x, y))
			throw new HorsEspaceJeuException("La position de l'envahisseur est en dehors de l'espace jeu");

		int longueurEnvahisseur = dimension.longueur();
		int hauteurEnvahisseur = dimension.hauteur();

		if (!estDansEspaceJeu(x + longueurEnvahisseur - 1, y))
			throw new DebordementEspaceJeuException(
					"L'envahisseur déborde de l'espace jeu vers la droite à cause de sa longueur");
		if (!estDansEspaceJeu(x, y - hauteurEnvahisseur + 1))
			throw new DebordementEspaceJeuException(
					"L'envahisseur déborde de l'espace jeu vers le bas à cause de sa hauteur");

		envahisseurs.add(new Envahisseur(dimension, position, vitesse));
	}

	public String recupererEspaceJeuDansChaineASCII() {
		StringBuilder espaceDeJeu = new StringBuilder();
		for (int y = 0; y < hauteur; y++) {
			for (int x = 0; x < longueur; x++) {
				espaceDeJeu.append(recupererMarqueDeLaPosition(x, y));
			}
			espaceDeJeu.append(Constante.MARQUE_FIN_LIGNE);
		}
		return espaceDeJeu.toString();
	}

	public void deplacerVaisseauVersLaDroite() {
		if (vaisseau.abscisseLaPlusADroite() < (longueur - 1)) {
			vaisseau.deplacerHorizontalementVers(Direction.DROITE);
			if (!estDansEspaceJeu(vaisseau.abscisseLaPlusADroite(), vaisseau.ordonneeLaPlusHaute())) {
				vaisseau.positionner(longueur - vaisseau.longueur(), vaisseau.ordonneeLaPlusHaute());
			}
		}
	}

	public void deplacerVaisseauVersLaGauche() {
		if (0 < vaisseau.abscisseLaPlusAGauche())
			vaisseau.deplacerHorizontalementVers(Direction.GAUCHE);
		if (!estDansEspaceJeu(vaisseau.abscisseLaPlusAGauche(), vaisseau.ordonneeLaPlusHaute())) {
			vaisseau.positionner(0, vaisseau.ordonneeLaPlusHaute());
		}
	}

	public void deplacerEnvahisseurVersLaDroite() {
		for (int i = 0; i < envahisseurs.size(); i++) {
			Envahisseur envahisseur = envahisseurs.get(i);
			if (envahisseur.abscisseLaPlusADroite() < (longueur - 1)) {
				envahisseur.deplacerHorizontalementVers(Direction.DROITE);
				if (!estDansEspaceJeu(envahisseur.abscisseLaPlusADroite(), envahisseur.ordonneeLaPlusHaute())) {
					envahisseur.positionner(longueur - envahisseur.longueur(), envahisseur.ordonneeLaPlusHaute());
				}
			}
		}
	}

	public void deplacerEnvahisseurVersLaGauche() {
		for (int i = 0; i < envahisseurs.size(); i++) {
			Envahisseur envahisseur = envahisseurs.get(i);
			if (0 < envahisseur.abscisseLaPlusAGauche())
				envahisseur.deplacerHorizontalementVers(Direction.GAUCHE);
			if (!estDansEspaceJeu(envahisseur.abscisseLaPlusAGauche(), envahisseur.ordonneeLaPlusHaute())) {
				envahisseur.positionner(0, envahisseur.ordonneeLaPlusHaute());
			}
		}
	}

	public void deplacerEnvahisseurDansLeSens() {
		for (int i = 0; i < envahisseurs.size(); i++) {
			Envahisseur envahisseur = envahisseurs.get(i);
			if (envahisseur.abscisseLaPlusADroite() == Constante.ESPACEJEU_LONGUEUR - 1) {
				Envahisseur.setSens(Direction.GAUCHE);
			}
			if (envahisseur.abscisseLaPlusAGauche() == 0) {
				Envahisseur.setSens(Direction.DROITE);
			}
		}
		if (Envahisseur.getSens() == Direction.GAUCHE) {
			this.deplacerEnvahisseurVersLaGauche();
		} else {
			this.deplacerEnvahisseurVersLaDroite();
		}
	}

	private boolean estDansEspaceJeu(int x, int y) {
		return ((x >= 0) && (x < longueur)) && ((y >= 0) && (y < hauteur));
	}

	public boolean aUnVaisseau() {
		return vaisseau != null;
	}

	private boolean aUnVaisseauQuiOccupeLaPosition(int x, int y) {
		return this.aUnVaisseau() && vaisseau.occupeLaPosition(x, y);
	}

	private char recupererMarqueDeLaPosition(int x, int y) {
		char marque;
		if (this.aUnVaisseauQuiOccupeLaPosition(x, y))
			marque = Constante.MARQUE_VAISSEAU;
		else if (this.aUnMissileQuiOccupeLaPosition(x, y))
			marque = Constante.MARQUE_MISSILE;
		else if (this.aUnEnvahisseurQuiOccupeLaPosition(x, y))
			marque = Constante.MARQUE_ENVAHISSEUR;
		else
			marque = Constante.MARQUE_VIDE;
		return marque;
	}

	public boolean aUnMissile() {
		if (!this.missiles.isEmpty()) {
			return true;
		}
		return false;
	}

	private boolean aUnMissileQuiOccupeLaPosition(int x, int y) {
		if (this.aUnMissile()) {
			for (int i = 0; i < missiles.size(); i++) {
				Missile missile = missiles.get(i);
				if (missile.occupeLaPosition(x, y)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean aUnEnvahisseur() {
		if (!this.envahisseurs.isEmpty()) {
			return true;
		}
		return false;
	}

	private boolean aUnEnvahisseurQuiOccupeLaPosition(int x, int y) {
		if (this.aUnEnvahisseur()) {
			for (int i = 0; i < envahisseurs.size(); i++) {
				Envahisseur envahisseur = envahisseurs.get(i);
				if (envahisseur.occupeLaPosition(x, y)) {
					return true;
				}
			}
		}
		return false;
	}

	public Vaisseau recupererVaisseau() {
		return this.vaisseau;
	}

	public List<Envahisseur> recupererEnvahisseur() {
		return this.envahisseurs;
	}

	public List<Missile> recupererMissile() {
		return this.missiles;
	}

	@Override
	public void evoluer(Commande commandeUser) {

		if (commandeUser.gauche) {
			deplacerVaisseauVersLaGauche();
		}

		if (commandeUser.droite) {
			deplacerVaisseauVersLaDroite();
		}

		if (commandeUser.espace) {
			tirerUnMissile(new Dimension(Constante.MISSILE_LONGUEUR, Constante.MISSILE_HAUTEUR),
					Constante.MISSILE_VITESSE);
		}
		if (aUnMissile()) {
			deplacerMissile();
		}

		if (aUnEnvahisseur()) {
			deplacerEnvahisseurDansLeSens();
		}
	}

	@Override
	public boolean etreFini() {
		return estFini;
	}

	public void initialiserJeu() {
		Position positionVaisseau = new Position(this.longueur / 2, this.hauteur - 1);
		Dimension dimensionVaisseau = new Dimension(Constante.VAISSEAU_LONGUEUR, Constante.VAISSEAU_HAUTEUR);
		positionnerUnNouveauVaisseau(dimensionVaisseau, positionVaisseau, Constante.VAISSEAU_VITESSE);

		for (int i = 100; i <= Constante.ESPACEJEU_LONGUEUR; i = i + 100) {
			Position positionEnvahisseur = new Position(i - Constante.ENVAHISSEUR_LONGUEUR * 2,
					Constante.ESPACEJEU_HAUTEUR / 10 + Constante.ENVAHISSEUR_HAUTEUR);
			Dimension dimensionEnvahisseur = new Dimension(Constante.ENVAHISSEUR_LONGUEUR,
					Constante.ENVAHISSEUR_HAUTEUR);
			positionnerUnNouvelEnvahisseur(dimensionEnvahisseur, positionEnvahisseur, Constante.ENVAHISSEUR_VITESSE);
		}
		this.estFini = false;
	}

	public void tirerUnMissile(Dimension dimensionMissile, int vitesseMissile) {

		if ((vaisseau.hauteur() + dimensionMissile.hauteur()) > this.hauteur)
			throw new MissileException(
					"Pas assez de hauteur libre entre le vaisseau et le haut de l'espace jeu pour tirer le missile");

		Missile missilePasEncoreTire = this.vaisseau.tirerUnMissile(dimensionMissile, vitesseMissile);
		if (this.aUnMissile()) {
			Missile missileTire = missiles.get(missiles.size() - 1);
			Missile cadenceTir = new Missile(
					new Dimension(missilePasEncoreTire.longueur(), missilePasEncoreTire.hauteur() * 4),
					new Position(missilePasEncoreTire.abscisseLaPlusAGauche(),
							missilePasEncoreTire.ordonneeLaPlusHaute()),
					0);
			if (cadenceTir.estOrdonneeCouverte(missileTire.ordonneeLaPlusHaute())) {
				return;
			}
		}
		this.missiles.add(missilePasEncoreTire);
	}

	public void deplacerMissile() {
		for (int i = 0; i < missiles.size(); i++) {
			Missile missile = missiles.get(i);
			if (0 < missile.ordonneeLaPlusHaute())
				missile.deplacerVerticalementVers(Direction.HAUT_ECRAN);
			if (aUnEnvahisseur()) {
				detectionCollisionMissilesEnvahisseurs(i, missile);
				if (envahisseurs.isEmpty()) {
					this.estFini = true;
					return;
				}
			}
			if (!estDansEspaceJeu(missile.abscisseLaPlusAGauche(), missile.ordonneeLaPlusBasse())) {
				missiles.remove(i);
			}
		}
	}

	private void detectionCollisionMissilesEnvahisseurs(int i, Missile missile) {
		for (int cpt = 0; cpt < envahisseurs.size(); cpt++) {
			Envahisseur envahisseur = envahisseurs.get(cpt);
			if (collision.detecterCollision(missile, envahisseur)) {
				missiles.remove(i);
				envahisseurs.remove(cpt);
			}
		}
	}
}