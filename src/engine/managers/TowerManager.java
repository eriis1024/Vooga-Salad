package engine.managers;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import engine.sprites.ShootingSprites;
import engine.sprites.towers.FrontEndTower;
import engine.sprites.towers.Tower;
import engine.sprites.towers.projectiles.Projectile;


/**
 * Tower manager uses composite design pattern to handle updating all 
 * active Tower objects in the game loop.
 *
 * @author Miles Todzo
 * @author Katie Van Dyk
*/


public class TowerManager extends ShootingSpriteManager {

	//not sure exactly where this should be implemented/how the info for it will be passed in
	Map<String, Tower> myTowerTypeToInstance;
	
    /**
     * Constructor for super class
     */
    public TowerManager(Map<String, Tower> towerTypeToInstance) {
	super();
	myTowerTypeToInstance = towerTypeToInstance;
    }

    /**
     * Sets the AVAILABLE field in the TowerManager to @param towers. 
     * 
     * @param towers: towers taken from the AuthoringModel that are available in the game
     */
    public void setAvailableTowers(Collection<Tower> towers) {
	for(Tower tower : towers) {
	    addToAvailableList(tower);
	}
    }

    
    /**
     * Moves towers on every step of the GameLoop
     */
    public void moveTowers() {
	// TODO Auto-generated method stub
    }
    
    public FrontEndTower place(Point location, String type) {
	System.out.println(type);
	for (Entry<String, Tower> entry : myTowerTypeToInstance.entrySet()) {
	    System.out.println(entry.getKey());
	    System.out.println(entry.getValue());
	}
    		Tower newTower = new Tower(myTowerTypeToInstance.get(type),location);
    		this.addToActiveList(newTower);
    		newTower.place(location.getX(), location.getY());
    		return newTower;
    }

    /**
     * Removes the tower from the list of active towers
     * @param tower : front end tower
     */
    public int sell(FrontEndTower tower) {
	if(this.getListOfActive().remove(tower)) {
	    return tower.sell();
	}
	return 0;
    }
    
    /**
     * Called from PlayState, tower is to be upgraded by the type specified in upgradeName
     * @param tower
     * @param upgradeName
     */
    public double upgrade(FrontEndTower tower, String upgradeName, double balance) {
	for(ShootingSprites realTower : this.getListOfActive()) {
	    if(realTower.hashCode() == tower.hashCode()) {
		 return realTower.upgrade(upgradeName, balance);
	    }
	}
	return balance;
    }

    /**
     * Removes all of the projectiles from the tower manager
     * @return
     */
    public Collection<Projectile> removeAllProjectiles() {
	List<Projectile> toBeRemoved = new ArrayList<>();
	for(ShootingSprites tower : this.getListOfActive()) {
	    toBeRemoved.addAll(tower.getLauncher().getListOfActive());
	    tower.getLauncher().getListOfActive().clear();
	}
	return toBeRemoved;
    }

}
