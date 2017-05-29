package fr.unilim.iut.spaceinvaders.metier;

public class Envahisseur extends Sprite{
	
	private static Direction sens = Direction.DROITE;

	public Envahisseur(Dimension dimensionEnvahisseur, Position positionEnvahisseur, int vitesseEnvahisseur){
		super(dimensionEnvahisseur, positionEnvahisseur, vitesseEnvahisseur);
	}

	public static Direction getSens() {
		return sens;
	}

	public static void setSens(Direction sens) {
		Envahisseur.sens = sens;
	}
}
