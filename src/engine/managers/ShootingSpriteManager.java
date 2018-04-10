package engine.managers;

import java.util.ArrayList;
import java.util.List;
import engine.sprites.ShootingSprites;
import engine.sprites.Sprite;
import engine.sprites.towers.Tower;
import engine.sprites.towers.projectiles.Projectile;

/**
 * 
 * @author Miles Todzo
 *
 */

public class ShootingSpriteManager extends Manager<ShootingSprites>{
    /**
     * Checks for collisions between between the list of active actors held by the Manager the method
     * was called on and the list of active actors passed as a parameter
     * @param passedSprites
     */
    public List<Sprite> checkForCollisions(List<ShootingSprites> passedSprites) {
	List<Sprite> spritesToBeRemoved = new ArrayList<>();
    		for (ShootingSprites activeSprite: this.getObservableListOfActive()) {
    			for (ShootingSprites passedActor: passedSprites) {
    			    List<Sprite> deadSprites = activeSprite.checkForCollision(passedActor);
    			    spritesToBeRemoved.addAll(deadSprites);
    			}
    		}
    		return spritesToBeRemoved;
    }
    
    /**
     * Called every update on any shooting sprites, will shoot if the rate of fire says it can
     * @param passedSprites
     */
    public void shoot(List<ShootingSprites> targets) {
    		for (ShootingSprites shootingSprite: this.getObservableListOfActive()) {
    			shootingSprite.shoot
    		}
    }
    
	public void moveProjectiles() {
		for (ShootingSprites shootingSprite: this.getObservableListOfActive()) {
			for (Projectile projectile: shootingSprite.getProjectiles()) {
				projectile.move();
			}
		}
	}
   
}
