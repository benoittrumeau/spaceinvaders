package fr.unilim.iut.spaceinvaders;

public class Envahisseur extends Sprite{
	
	public Direction sens;

	public Envahisseur(Dimension dimensionEnvahisseur, Position positionEnvahisseur, int vitesseEnvahisseur){
		this.dimension=dimensionEnvahisseur;
		this.origine=positionEnvahisseur;
		this.vitesse=vitesseEnvahisseur;
		this.sens=Direction.DROITE;
	}
}
