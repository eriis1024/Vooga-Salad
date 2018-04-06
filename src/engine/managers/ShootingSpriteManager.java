package engine.managers;

import java.util.ArrayList;
import java.util.List;

import engine.sprites.ShootingSprites;
import engine.sprites.Sprite;

/**
 * 
 * @author Miles Todzo
 *
 */

public class ShootingSpriteManager extends Manager{
    /**
     * Checks for collisions between between the list of active actors held by the Manager the method
     * was called on and the list of active actors passed as a parameter
     * @param passedSprites
     */
    public void checkForCollisions(List<ShootingSprites> passedSprites) {
    		for (Sprite activeSprite: this.getObservableListOfActive()) {
    			for (ShootingSprites passedActor: passedSprites) {
    				activeSprite.checkForCollision(passedActor, passedActor.getProjectiles());
    			}
    		}
    }
}
