package engine.builders;


import java.util.ArrayList;
import java.util.List;

import engine.sprites.enemies.Enemy;
import engine.sprites.properties.DamageProperty;
import engine.sprites.properties.FireRateProperty;
import engine.sprites.properties.HealthProperty;
import engine.sprites.properties.Property;
import engine.sprites.properties.RangeProperty;
import engine.sprites.properties.ValueProperty;
import engine.sprites.towers.launcher.Launcher;

/**
 * 
 * 
 * @author Miles Todzo
 */
public class EnemyBuilder {

    public Enemy construct(String name, String image, double speed, double initialHealth, double healthImpact,
	    double killReward, double killUpgradeCost, double killUpgradeValue) {
	List<Property> properties = new ArrayList<>();
	properties.add(new HealthProperty(0, 0, initialHealth));
	properties.add(new DamageProperty(0 , 0 , healthImpact));
	properties.add(new ValueProperty(killReward));
	Enemy newEnemy = new Enemy(name, image, speed, 50, new Launcher(null, null), properties);
	return newEnemy;
    }

}
