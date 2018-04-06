package engine.managers;

/**
 * Enemy manager uses composite design pattern to handle updating all 
 * active Enemy objects in the game loop.
 * 
 * @author Miles Todzo
 */
public class EnemyManager extends ShootingSpriteManager {
    
	// this doesn't have its own lists like Tower manager does -bma
	
    /**
     * Constructor for Enemy manager
     */
    public EnemyManager() {
    		super();
    }
}
