package fr.unilim.iut.spaceinvaders.metier;

public class Envahisseur extends Sprite{
	
	public Direction sens;

	public Envahisseur(Dimension dimensionEnvahisseur, Position positionEnvahisseur, int vitesseEnvahisseur){
		super(dimensionEnvahisseur, positionEnvahisseur, vitesseEnvahisseur);
		this.sens=Direction.DROITE;
	}
}
