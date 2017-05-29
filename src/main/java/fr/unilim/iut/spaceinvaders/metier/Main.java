package fr.unilim.iut.spaceinvaders.metier;

import javax.swing.JOptionPane;

import fr.unilim.iut.spaceinvaders.moteurjeu.MoteurGraphique;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		int rep;

		do {
			creationJeu();
			rep = JOptionPane.showConfirmDialog(null, "Félicitations, vous avez gagné !\nVoulez-vous rejouer ?",
					"GAGNE !", JOptionPane.YES_NO_OPTION);
			if (rep == 1) {
				System.exit(0);
			}
		} while (rep == 0);
	}

	private static void creationJeu() throws InterruptedException {
		SpaceInvaders jeu = new SpaceInvaders(Constante.ESPACEJEU_LONGUEUR, Constante.ESPACEJEU_HAUTEUR);
		jeu.initialiserJeu();
		DessinSpaceInvaders afficheur = new DessinSpaceInvaders(jeu);

		MoteurGraphique moteur = new MoteurGraphique(jeu, afficheur);
		moteur.lancerJeu(Constante.ESPACEJEU_LONGUEUR, Constante.ESPACEJEU_HAUTEUR);

	}
}