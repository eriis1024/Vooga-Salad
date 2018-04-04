package engine.sprites.enemies;

import engine.physics.Intersector;
import engine.sprites.Sprite;
import engine.sprites.properties.DamageProperty;
import engine.sprites.properties.HealthProperty;
import engine.sprites.properties.ValueProperty;
import engine.sprites.towers.projectiles.Projectile;
import javafx.scene.image.ImageView;

/**
 * This is used for the Enemy object in the game. It will use composition to implement moveable
 * and intersectable methods.
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
		myHealth = new HealthProperty(health);
		myDamage = new DamageProperty(damage);
		myValue = new ValueProperty(value);
		myIntersector = new Intersector(myImage);
	}

	@Override
	public boolean overlap(ImageView otherImage) {
		return myIntersector.overlap(otherImage);
	}

	@Override
	public boolean getHitBy(Projectile projectile) {
		myHealth.change(projectile.inflictDamage());
		return myHealth.isAlive();
	}

	@Override
	public void followPath() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public Double damage() {
		return myDamage.getProperty();
	}


}
