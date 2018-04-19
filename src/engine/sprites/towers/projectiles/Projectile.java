package engine.sprites.towers.projectiles;

import java.util.ArrayList;
import java.util.List;

import engine.sprites.FrontEndSprite;
import engine.sprites.ShootingSprites;
import engine.sprites.Sprite;
import engine.sprites.properties.DamageProperty;

/**
 * Projectile class is a sprite that is launched from the tower
 * and can intersect with enemies to destroy them. 
 * 
 * @author Katherine Van Dyk
 *
 */
public class Projectile extends Sprite implements FrontEndSprite{

	private DamageProperty myDamage;
	private double mySpeed;
	private double mySize; 
	private ShootingSprites myTarget;
	private List<Sprite> hitTargets;
	private int myHits = 1;
	
	/**
	 * Constructor that takes in a damage value and image, and creates a projectile
	 * class.
	 * 
	 * @param damage: Damage property objects that illustrates how much damage a projectile exerts on enemy
	 * @param image: image of projectile
	 */
	public Projectile(String name, DamageProperty damage, double size, String image, double speed) {
	    	super(name, image, size);
		myDamage = damage;
		mySpeed = speed;
		mySize = size; 
		hitTargets = new ArrayList<>();
	}
	
	public Projectile(Projectile myProjectile, ShootingSprites target, double shooterX, double shooterY) {
	    super(myProjectile.getName(),myProjectile.getImageString(), myProjectile.getSize());
	    myTarget = target;
	    mySpeed = myProjectile.getSpeed();
	    myDamage = myProjectile.getDamageProperty();
	    this.place(shooterX, shooterY);
	    this.rotateImage();
	    hitTargets = new ArrayList<>();
	}

	/**
	 * Moves image in direction of it's orientation
	 */
	public void move(double elapsedTime) {
		if (this.myTarget.isAlive()) {
			rotateImage();
		}
			double totalDistanceToMove = this.mySpeed*elapsedTime;
			double xMove = Math.sin(Math.toRadians(this.getRotate()))*totalDistanceToMove;
			double yMove = Math.cos(Math.toRadians(this.getRotate()))*totalDistanceToMove;
			this.getImageView().setX(this.getX()+xMove);
			this.getImageView().setY(this.getY()+yMove);
			System.out.println("PROJECTLIE " + this.getImageView().getX() + " " + this.getImageView().getY());
	}

	/**
	 * Rotates the image to face the target
	 */
	private void rotateImage() {

	    	double xDifference = myTarget.getX() - this.getX();
	    	double yDifference = myTarget.getY() - this.getY();
	    	double angleToRotateRads = Math.atan2(xDifference,yDifference);
	    	this.setRotate(Math.toDegrees(angleToRotateRads));
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
	@Override
	public double getDamage() {
	    return myDamage.getProperty();
	}
	
	public DamageProperty getDamageProperty() {
	    return myDamage;
	}
	
	public double getSpeed() {
	    return mySpeed;
	}
	
	
	public String getDamageName() {
		return myDamage.getName();
	}
	
	public double getSize() {
		return mySize; 
	}
	
	/**
	 * @return true if should be removed
	 */
	@Override
	public boolean handleCollision(Sprite sprite) {
		this.hitTargets.add(sprite);
		this.myHits--;
		return !(myHits > 0);
	}

	/**
	 * This method prevents the same projectile from colliding with the same tower/enemy more than once
	 * Important since projectiles will be able to hit multiple enemies/towers before being removed
	 * @param target
	 * @return
	 */
	public boolean hasHit(ShootingSprites target) {
		return this.hitTargets.contains(target);
	}
	
}
