package engine.sprites.towers.launcher;

import engine.managers.Manager;
import engine.sprites.ShootingSprites;
import engine.sprites.properties.DamageProperty;
import engine.sprites.properties.FireRateProperty;
import engine.sprites.properties.RangeProperty;
import engine.sprites.towers.projectiles.Projectile;
import javafx.scene.image.Image;

/**
 * Class to manage different projectiles. Takes a defined range and trigger to determine when to
 * fire projectiles
 * 
 * @author Ben Hodgson 
 * @author Ryan Pond
 * @author Katherine Van Dyk
 * @author Miles Todzo 4/9
 * @date 3/28/18
 */
public class Launcher extends Manager<Projectile>{

    private RangeProperty myRange;
    private FireRateProperty myFireRate;
    private Projectile myProjectile;
    private long timeLastFired;

    public Launcher(FireRateProperty fireRate, Projectile projectile, RangeProperty range) {
	myFireRate = fireRate;
	myProjectile = projectile;
	myRange = range;
	timeLastFired = System.nanoTime();
    }

    /**
     * Sets the current projectile type managed by the ProjectileManager
     */
    public void setProjectile(Projectile projectile) {
	myProjectile = projectile;
    }

    /**
     * Updates the tower's projectile fire rate
     * 
     * @param rate: the new fire rate 
     * @return double: the user's remaining balance
     */
    public double upgradeFireRate(double balance) {
	return myFireRate.upgrade(balance);
    }


    /**
     * Launch method will make sure that enough time has passed since last shot and then fire a new projectile
     * 
     */
    //TODO need to let frontend know that this projectile was added?
    public void launch() {
    		this.addToActiveList(myProjectile);
    }
    
    public boolean hasReloaded() {
    		long currTime = System.nanoTime();
     	long timeSinceLastShot = currTime - timeLastFired;
     	if(timeSinceLastShot >= myFireRate.getProperty()) {
     		timeSinceLastShot = currTime;
     		return true;
     	}
		return false;
	}

    public double upgradeDamage(double balance) {
	return myProjectile.upgradeDamage(balance);
    }
    
    public double getRange() {
    	return myRange.getProperty(); 
    }
    
    public double getFireRate() {
    	return myFireRate.getProperty(); 
    }
    
    public Image getProjectileImage() {
    	return myProjectile.getImage().getImage(); 
    }
    
    public double getProjectileDamage() {
    	return myProjectile.getDamage(); 
    }
    
    public double getProjectileSpeed() {
    	return myProjectile.getSpeed(); 
    }

    public DamageProperty getDamageProperty() {
    	return myProjectile.getDamageProperty();
    }
    public FireRateProperty getFireRateProperty() {
    	return myFireRate;
    }
    public RangeProperty getRangeProperty() {
    	return myRange;
    }
    
    public double getDamage() {
    	return myProjectile.getDamage();
    }
    public double getFireRate() {
    	return myFireRate.getProperty();
    }
    public double getRange() {
    	return myRange.getProperty();
    }

}
