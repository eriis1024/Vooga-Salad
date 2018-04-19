package engine.sprites.enemies;

import java.awt.Point;

import engine.path.Path;
import engine.physics.ImageIntersecter;
import engine.sprites.FrontEndSprite;
import engine.sprites.ShootingSprites;
import engine.sprites.Sprite;
import engine.sprites.properties.DamageProperty;
import engine.sprites.properties.HealthProperty;
import engine.sprites.properties.ValueProperty;
import engine.sprites.towers.launcher.Launcher;

import javafx.scene.Node;

/**
 * This is used for the Enemy object in the game. It will use composition to implement
 * intersectable methods.
 * 
 * @author ryanpond
 * @author Katherine Van Dyk
 * @date 4/8/18
 *
 */
public class Enemy extends ShootingSprites implements FrontEndSprite{

    private String myName; 
    private HealthProperty myHealth;
    private double myInitialHealth; 
    private DamageProperty myDamage;
    private double myHealthImpact; 
    private ValueProperty myValue;
    private ImageIntersecter myIntersecter;
    private double mySpeed;
    private double mySize;
    private double myKillReward;
    private String myImage;  
    private Path myPath;

    public Enemy(String name, String image, double speed, double size, Launcher launcher, HealthProperty health, DamageProperty damage, ValueProperty value) {
	super(name, image, size, launcher);
	myImage = image; 
	myName = name; 
	myHealth = health;
	myInitialHealth = myHealth.getProperty();
	myDamage = damage;
	myHealthImpact = myDamage.getProperty();
	myValue = value;
	myIntersecter = new ImageIntersecter(this); 
	mySpeed = speed; 
	myKillReward = value.getProperty();
    }

    /**
     * Copy constructor
     */
    public Enemy(Enemy copiedEnemy) {
	super("", copiedEnemy.getImageString(), copiedEnemy.mySize, copiedEnemy.getLauncher());
	myName = copiedEnemy.getName(); 
	setImage(copiedEnemy.getImageView().getImage()); 
	myIntersecter = copiedEnemy.getIntersecter(); 
	myHealth = copiedEnemy.getHealth(); 
	myDamage = copiedEnemy.getDamageProperty();
	myHealthImpact = myDamage.getProperty(); 
	myValue = copiedEnemy.getValue();
	mySpeed = copiedEnemy.getSpeed();
    }

    /**
     * Used for debugging/demo purposes, should not actually be used
     * @param string
     * @param string2
     * @param i
     */
    public Enemy(String name, String image, double size) {
	super(name, image, size, null);
	myHealth = new HealthProperty(10000,10000,100);
	myDamage = new DamageProperty(10000, 10000, 10000);
	myValue = new ValueProperty(900);
    }
   
    /**
     * Moves the enemy along the path according to how much time has passed
     * @param elapsedTime
     */
    public void move(Point newPosition) {
	this.getImageView().setX(newPosition.getX());
	this.getImageView().setY(newPosition.getY());
	System.out.println("image view "+this.getImageView().getX() + " " + this.getImageView().getY());
	System.out.println(" point" + newPosition.getX() + " " + newPosition.getY());
    }
    
    public Point currentPosition() {
	Point position = new Point();
	position.setLocation(this.getImageView().getX(), this.getImageView().getY());
	return position;
    }

    public String getName() {
	return myName; 
    }

    /**
     * Handles returning an enemy's damage after hitting a tower
     * 
     * @return Double: damage that Enemy incurs on the tower
     */
    @Override
    public double getDamage() {
	return myDamage.getProperty();
    }

    /**
     * Returns true if this Enemy is still alive
     */
    @Override
    public boolean handleCollision(Sprite collider) {
	myHealth.loseHealth(collider.getDamage());
	return myHealth.isAlive();
    }

    private ImageIntersecter getIntersecter() {
	return myIntersecter; 
    }

    private HealthProperty getHealth() {
	return myHealth; 
    }

    private DamageProperty getDamageProperty() {
	return myDamage; 
    }

    private ValueProperty getValue() {
	return myValue; 
    }
    
    @Override
    public int getPointValue() {
    	return (int) myValue.getProperty();
    }

    
    private double getSpeed() {
	return mySpeed; 
    }
    
    private String getImage() {
    	return myImage; 
    }
    @Override
    protected HealthProperty getHealthProp() {
    	return this.myHealth;
    }

}
