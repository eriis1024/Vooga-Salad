package engine.towers.projectiles;

import engine.physics.Physics;

/**
 * 
 * @author Ben Hodgson 3/28/18
 *
 */
public interface ProjectileInterface extends Physics {
    
    /**
     * Specifies the effect of the projectile
     */
    public void launch();
    
    /**
     * Checks to see if the Projectile hits an Enemy
     */
    public void hitsEnemy();

}
