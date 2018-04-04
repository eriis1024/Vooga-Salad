package engine.sprites.enemies;

import engine.sprites.towers.projectiles.ProjectileInterface;
import javafx.scene.image.ImageView;

/**
 * 
 * @author Ben Hodgson 3/28/18
 *
 * Interface for enemy functionality
 */
public interface EnemyI {
    
    /**
     * Handles when the Enemy is hit by a tower
     * 
     * @param projectile: the projectile that hit the enemy
     */
    public boolean getHitBy(ProjectileInterface projectile);
    
    /**
     * Handles updating the enemy position to follow the path
     */
    public void followPath();
    
    /**
     * Handles returning an enemy's damage after hitting a tower
     * 
     * @return Double: damage that Enemy incurs on the tower
     */
    public Double damage();
    
    /**
     * Updates the properties of the enemy. For example, health, position, etc.
     */
    public void update();

    boolean overlap(ImageView otherImage);

}
