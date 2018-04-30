package engine.sprites.towers.projectiles;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import engine.sprites.FrontEndSprite;
import engine.sprites.ShootingSprites;
import engine.sprites.Sprite;
import engine.sprites.properties.BoomerangProperty;
import engine.sprites.properties.HeatSeekingProperty;
import engine.sprites.properties.MovingProperty;
import engine.sprites.properties.Property;
import engine.sprites.properties.SpeedProperty;
import engine.sprites.enemies.Enemy;

/**
 * Projectile class is a sprite that is launched from the tower
 * and can intersect with enemies to destroy them. 
 * 
 * @author Katherine Van Dyk
 * @author Ryan Pond
 *
 */
public class Projectile extends Sprite implements FrontEndSprite{

    private static final double mySpeedFactor = 1.5;
    private double mySize; 
    private ShootingSprites myTarget;
    private List<Sprite> hitTargets;
    private int myHits = 1;
    private MovingProperty myMovingProperty;
    private Point targetDestination;

    /**
     * Constructor that takes in a damage value and image, and creates a projectile
     * class.
     * 
     * @param damage: Damage property objects that illustrates how much damage a projectile exerts on enemy
     * @param image: image of projectile
     */
    public Projectile(String name, double size, String image, List<Property> properties) {
	super(name, image, size, properties);
	mySize = size; 
	hitTargets = new ArrayList<>();
	for (Property p: properties) {
	    System.out.println(p.getName() + " in projectile property list");
	}
    }

    /**
     * Moves projectile and accelerates according to enemy speed
     * 
     * @param myProjectile
     * @param target
     * @param shooterX
     * @param shooterY
     */
    public Projectile(Projectile myProjectile, ShootingSprites target, double shooterX, double shooterY) {
	super(myProjectile.getName(),myProjectile.getImageString(), myProjectile.getSize(), myProjectile.getProperties());
	myTarget = target;
	if (target instanceof Enemy) {
	    Enemy myEnemy = (Enemy) target;
	    Double speed = myEnemy.getProperty("SpeedProperty").getProperty();
	    this.addProperty(new SpeedProperty(0,0, mySpeedFactor*speed));
	}
	this.place(shooterX, shooterY);
	this.rotateImage();
	hitTargets = new ArrayList<>();
	myMovingProperty = (MovingProperty) this.getPropertySuperclassType("MovingProperty");
	myMovingProperty.setProjectileOrigin(shooterX, shooterY);
	targetDestination = new Point();
	targetDestination.setLocation(target.getX(), target.getY());
    }
    
    public Projectile(Projectile myProjectile, double startX, double startY, double targetX, double targetY) {
	super(myProjectile.getName(),myProjectile.getImageString(), myProjectile.getSize(), myProjectile.getProperties());
	this.place(startX, startY);
//	System.out.println(" testing " + projectile.getPropertySuperclassType("MovingProperty"));
//	System.out.println(" testing again " + projectile.getProperty("MovingProperty"));
	this.myMovingProperty = (MovingProperty) this.getPropertySuperclassType("MovingProperty");
	//System.out.println("SPEED PROPERTY "+ this.getProperty("Constant") + " " +this.getProperty("MovingProperty")+ " " + this.getProperty("HeatSeekingProperty"));//this.getProperty("SpeedProperty").getProperty());
	myMovingProperty.setProjectileOrigin(startX, startY);
	targetDestination = new Point();
	targetDestination.setLocation(targetX, targetY);
	this.addProperty(new SpeedProperty(0,0,myProjectile.getProperty("ConstantSpeedProperty").getProperty()));
    }

    /**
     * Moves image in direction of it's orientation
     */
    public boolean move(double elapsedTime) {
	try {
	    boolean bool = myMovingProperty.move(this, elapsedTime);
	    return bool;
	}catch(NullPointerException e) {
	    //this means there is not movement property defined for the projectile, so don't move them
	    System.out.println("no movement property  "+myMovingProperty);
	    e.printStackTrace();
	    return false;
	}
	
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
     * 
     * @return : the amount of damage this Projectile does
     */
    @Override
    public double getDamage() {
	return getValue("DamageProperty");
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
    
    public void setImage(String image) {
	super.updateImage(image);
    }
    
    public boolean isTargetAlive() {
	return myTarget == null || myTarget.isAlive();
    }

    public double getSpeed() {
	return this.getProperty("SpeedProperty").getProperty();
    }
    public void setProjectileTarget(Point newTargetDestination) {
	targetDestination = newTargetDestination;
    }

    public Point getTargetDestination() {
	return targetDestination;
    }
}
