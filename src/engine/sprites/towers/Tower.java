package engine.sprites.towers;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import engine.sprites.ShootingSprites;
import engine.sprites.Sprite;
import engine.sprites.properties.HealthProperty;
import engine.sprites.properties.KillProperty;
import engine.sprites.properties.Property;
import engine.sprites.properties.UpgradeProperty;
import engine.sprites.properties.ValueProperty;
import engine.sprites.towers.launcher.Launcher;
import engine.sprites.towers.projectiles.Projectile;

/**
 * Class for tower object in game. Implements Sprite methods.
 * 
 * @author Katherine Van Dyk
 * @author Miles Todzo
 * @author Ryan Pond
 */
public class Tower extends ShootingSprites implements FrontEndTower {

    private int FAKE_X = 100000;
    private int FAKE_Y = 100000;
 
    private Launcher myLauncher;
    private HealthProperty myHealth;
    private double mySize;
    private List<Property> myProperties;

    /**
     * Constructor for a Tower object that accepts parameter properties.
     * 
     * @param image: Tower's image
     * @param launcher: Type of launcher that the Tower inherits 
     * @param health: Initial health of the tower
     * @param value: Value of the tower for selling
     */
    public Tower(String name, String image, double size, Launcher launcher, List<Property> properties) {
	super(name, image, size, launcher);
	mySize = size;
	myLauncher = launcher;
	myProperties = properties;
	myProperties.add(new KillProperty(0));
    }

    /**
     * Copy constructor
     */
    public Tower(Tower copiedTower) {
	super(copiedTower.getName(), copiedTower.getImageString(), copiedTower.mySize, copiedTower.getLauncher()); 
	myProperties = new ArrayList<Property>();
	for(Property p : copiedTower.getProperties()) {
	    myProperties.add(makeProperty(p));
	//    System.out.println("Make property" + makeProperty(p));
	}
	myProperties.add(new KillProperty(0));
    }

    /**
     * Copy constructor
     * @return 
     */
    public void move(Point point) {
	this.place(point.getX(), point.getY());
    }

    /**
     * Handles decrementing tower's damage when it gets hit by an enemy
     * 
     * @return boolean: True if tower is alive, false otherwise
     */
    @Override
    public boolean handleCollision(Sprite collider) {
    this.myHealth.loseHealth(collider.getDamage());
	return true;
    }

    /**
     * Handles selling a tower
     */
    public int sell() {
	removeAllProjectiles();
	return (int) getProperty("ValueProperty");
    }

    private void removeAllProjectiles() {
	for(Projectile projectile : this.getProjectiles()) {
	    projectile.place(FAKE_X, FAKE_Y);
	}
    }

    /**
     * Handles upgrading the health of a tower
     */
    public double upgradeProperty(String name, double balance) {
	for(Property property : myProperties) {
	    if(property.getName() == name) {
		return ((UpgradeProperty) property).upgrade(balance);
	    }
	}
	return balance;
    }

    /**
     * Upgrades all aspects of a tower
     */
    public double upgrade(double balance) {
	for(Property property : myProperties) {
	    balance -= ((UpgradeProperty) property).upgrade(balance);
	}
	return balance;
    }

    public Map<String, Integer> getTowerStats(){
	Map<String, Integer> propertyStats = new HashMap<String, Integer>();
	for(Property p : myProperties) {
	    propertyStats.put(p.getName(), (int) p.getProperty());
	}
	return propertyStats;
    }

    @Override
    public int purchase(int myResources) throws CannotAffordException {
	if (myResources < getProperty("ValueProperty")) {
	    throw new CannotAffordException("You do not have enough money to purchase this tower");
	}
	return (int) (myResources - getProperty("ValueProperty") );
    }

    @Override
    public int getPointValue() {
	// TODO Auto-generated method stub
	return 0;
    }

    public double getTowerRange() {
	return this.getLauncher().getProperty("RangeProperty");
    }

    public List<Property> getProperties(){
	return myProperties;
    }

    public void updateProperties() {
	updateImage(getImageString());
	updateLauncher(myLauncher); 
    }

    public void addProperty(Property property) {
	//System.out.println("Property: " + property);
	//System.out.println("Property Name: " + property.getName());
	Property toRemove = null;
	for(Property p : myProperties) {
	    if(property.getName().equals(p.getName())) {
		toRemove = p;
	    }
	}
	myProperties.remove(toRemove);
	myProperties.add(property);
    }
    
    public void addLauncherProperty(Property property) {
	myLauncher.addProperty(property);
    }
    
    public void addProjectileProperty(Property property) {
	myLauncher.addProjectileProperty(property);
    }
    
    public void setProjectileImage(String image) {
	myLauncher.setProjectileImage(image);
    }
    
    public double getProperty(String ID) {
	System.out.println(ID);
	for(Property property : myProperties) {
	    System.out.println("PROPERTY: " + property);
	    System.out.println("PROPERTY NAME:" + property.getName());
	    if(property != null && property.getName().equals(ID)) {
		return property.getProperty();
	    }
	}
	return 0;
    }

}

