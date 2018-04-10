package engine.sprites;

import java.util.ArrayList;
import java.util.List;

import engine.physics.ImageIntersecter;
import engine.sprites.towers.launcher.Launcher;
import engine.sprites.towers.projectiles.Projectile;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class is a more specific Sprite that applies to just shooting objects (Enemy and Tower).
 * @author Miles Todzo
 * @author Katherine Van Dyk
 * @author Ryan Pond 4/9
 * @param image
 * @param projectileManager
 */

public abstract class ShootingSprites extends Sprite{
	
	private Launcher myLauncher;
	private int hitCount;
	private int roundScore;
	private ImageIntersecter intersector;

	public ShootingSprites(String name, Image image, double size, Launcher launcher) {
		super(name, image);
		intersector = new ImageIntersecter(new ImageView(image));
		this.getImageView().setFitHeight(size);
		this.getImageView().setFitWidth(size);
		myLauncher = launcher;
		roundScore = 0;
	}
	
	public ObservableList<Projectile> getProjectiles(){
		return myLauncher.getObservableListOfActive();
	}
	
	public void increaseHitCount(int increaseAmount) {
		hitCount+=increaseAmount;
	}
	
	/**
	 * This checks for collisions between the shooter's projectiles and this ShootingSprite
	 * @param shooter : Input shooter that is shooting projectiles
	 * @return : a list of all sprites to be removed from screen (dead)
	 */
	public List<Sprite> checkForCollision(ShootingSprites target) {
	    	List<Sprite> toBeRemoved = new ArrayList<>();
	    	List<Projectile> projectiles = this.getProjectiles();
		this.checkTowerEnemyCollision(target);
		for (Projectile projectile: projectiles) {
			if(target.intersects(projectile)){
				toBeRemoved = target.objectCollision(projectile);
			}
		}
		return toBeRemoved;
	}
	
	private List<Sprite> objectCollision(Sprite collider) {
	    List<Sprite> deadSprites = new ArrayList<>();
	    hitCount++;
	    if(!this.handleCollision(collider)) {
	    		deadSprites.add(this);
	    		roundScore += this.getPointValue();
	    }
	    if(!collider.handleCollision(this)) {
		deadSprites.add(collider);
	    }
	    return deadSprites;
	}

	/**
	 * Checks to see if the shooter itself overlaps with this ShootingSprite object
	 * @param shooter
	 * @return
	 */
	public List<Sprite> checkTowerEnemyCollision(ShootingSprites shooter) {
	    	List<Sprite> toBeRemoved = new ArrayList<>();
		if (intersector.overlaps(shooter.getImageView())) {
			toBeRemoved = objectCollision(shooter);
		}
		return toBeRemoved;
	}
	
	public boolean hasInRange(ShootingSprites passedSprite) {
		double distanceBetween = Math.sqrt(Math.pow(passedSprite.getX()-this.getX(),2)+Math.pow(passedSprite.getY()-this.getY(), 2));
		return (distanceBetween <= myLauncher.getRange());
	}

	public boolean hasReloaded() {
		return myLauncher.hasReloaded();
	}
	
	public Projectile launch(Sprite target, double shooterX, double shooterY) {
		return myLauncher.launch(target, shooterX, shooterY);
	}

	/**
	 * Checks if there is an intersection between a projectile (fired from tower) and this enemy
	 * @param projectile
	 * @return intersect or not
	 */
	public boolean intersects(Projectile projectile) {
	    return intersector.overlaps(projectile.getImageView());
	}

	public Launcher getLauncher() {
		return myLauncher;
	}
	public int getRoundScore() {
		return roundScore;
	}
	public abstract int getPointValue();
}
