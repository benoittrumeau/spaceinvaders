package fr.unilim.iut.spaceinvaders.metier;

public class Collision {

	public boolean detecterCollision(Sprite sprite1, Sprite sprite2) {
		if (spriteEstComprisDansLIntervalleDAbscisseDUnAutreSprite(sprite2, sprite1)
				&& spriteEstComprisDansLIntervalleDOrdonneeDUnAutreSprite(sprite2, sprite1)) {
			return true;
		}
		if (spriteEstComprisDansLIntervalleDAbscisseDUnAutreSprite(sprite1, sprite2)
				&& spriteEstComprisDansLIntervalleDOrdonneeDUnAutreSprite(sprite1, sprite2)) {
			return true;
		}
		return false;
	}

	private boolean spriteEstComprisDansLIntervalleDOrdonneeDUnAutreSprite(Sprite sprite1, Sprite sprite2) {
		return elementToucheParOrdonnee(sprite1, sprite2.ordonneeLaPlusBasse())
				|| elementToucheParOrdonnee(sprite1, sprite2.ordonneeLaPlusHaute());
	}

	private boolean spriteEstComprisDansLIntervalleDAbscisseDUnAutreSprite(Sprite sprite1, Sprite sprite2) {
		return elementToucheParAbscisse(sprite1, sprite2.abscisseLaPlusAGauche())
				|| elementToucheParAbscisse(sprite1, sprite2.abscisseLaPlusADroite());
	}

	public boolean elementToucheParAbscisse(Sprite sprite, int x) {
		if (sprite.estAbscisseCouverte(x)) {
			return true;
		}
		return false;
	}

	public boolean elementToucheParOrdonnee(Sprite sprite, int y) {
		if (sprite.estOrdonneeCouverte(y)) {
			return true;
		}
		return false;
	}
}