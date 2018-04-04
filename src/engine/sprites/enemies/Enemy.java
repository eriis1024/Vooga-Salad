package engine.sprites.enemies;

import engine.physics.Intersector;
import engine.sprites.Sprite;
import engine.sprites.properties.DamageProperty;
import engine.sprites.properties.HealthProperty;
import engine.sprites.properties.ValueProperty;
import engine.sprites.towers.projectiles.ProjectileInterface;
import javafx.scene.image.ImageView;

/**
 * This is used for the Enemy object in the game. It will use composition to implement
 * intersectable methods.
 * 
 * @author ryanpond
 *
 */
public class Enemy extends Sprite implements EnemyI{

    private HealthProperty myHealth;
    private DamageProperty myDamage;
    private ValueProperty myValue;
    private Intersector myIntersector;

    public Enemy(ImageView myImage, int health, int damage, int value) {
	super(myImage);
	myIntersector = new Intersector(myImage);
	myHealth = new HealthProperty(health);
	myDamage = new DamageProperty(damage);
	myValue = new ValueProperty(value);
    }

    @Override
    public boolean overlap(ImageView otherImage) {
	return myIntersector.overlap(otherImage);
    }

    @Override
    public boolean getHitBy(ProjectileInterface projectile) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public void followPath() {
	// TODO Auto-generated method stub
    }

    @Override
    public Double damage() {
	// TODO Auto-generated method stub
	return null;
    }


}
