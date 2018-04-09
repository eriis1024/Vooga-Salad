package engine.sprites.towers.projectiles;

import engine.sprites.Sprite;
import engine.sprites.properties.DamageProperty;
import javafx.scene.image.Image;

/**
 * Projectile class is a sprite that is launched from the tower
 * and can intersect with enemies to destroy them. 
 * 
 * @author Katherine Van Dyk
 *
 */
public class Projectile extends Sprite {

	private DamageProperty myDamage;
	
	/**
	 * Constructor that takes in a damage value and image, and creates a projectile
	 * class.
	 * 
	 * @param damage: Damage property objects that illustrates how much damage a projectile exerts on enemy
	 * @param image: image of projectile
	 */
	public Projectile(String name, DamageProperty damage, Image image, double speed) {
	    	super(name, image);
		myDamage = damage;
	}
	
	/**
	 * Moves image along a curve on the screen
	 */
	public void move() {
		// TODO fill this out with delegation to a mover
	}
	
	/**
	 * Upgrades damage value of projectile
	 * 
	 * @param balance: New user's balance of money
	 * @return double representing new balance of user
	 */
	public double upgradeDamage(double balance) {
	    return myDamage.upgrade(balance);
	}
	
	/**
	 * 
	 * @return : the amount of damage this Projectile does
	 */
	public double getDamage() {
	    return myDamage.getProperty();
	}
	
	public DamageProperty getDamageProperty() {
		return myDamage;
	}


}
