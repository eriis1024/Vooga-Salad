package engine.builders;


import engine.sprites.enemies.Enemy;
import engine.sprites.properties.DamageProperty;
import engine.sprites.properties.FireRateProperty;
import engine.sprites.properties.HealthProperty;
import engine.sprites.properties.RangeProperty;
import engine.sprites.properties.ValueProperty;
import engine.sprites.towers.launcher.Launcher;
import javafx.scene.image.Image;

/**
 * 
 * 
 * @author Miles Todzo
 */
public class EnemyBuilder {

    public Enemy construct(String name, Image image, double speed, double initialHealth, double healthImpact,
	    double killReward, double killUpgradeCost, double killUpgradeValue) {
	Enemy newEnemy = new Enemy(name, image, speed, image.getWidth(), new Launcher(new FireRateProperty(0,0,0), null, new RangeProperty(0,0,0)), new HealthProperty(0, 0, initialHealth), new DamageProperty(0 , 0 , healthImpact), new ValueProperty(killReward), null);
	return newEnemy;
    }

}
