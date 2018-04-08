package engine.sprites;

import java.util.List;

import engine.managers.ProjectileManager;
import engine.sprites.towers.projectiles.Projectile;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;

/**
 * This class is a more specific Sprite that applies to just shooting objects (Enemy and Tower).
 * @author Miles Todzo
 * @param image
 * @param projectileManager
 */

public class ShootingSprites extends Sprite{
	
	private ProjectileManager myProjectileManager;
	private int hitCount;

	public ShootingSprites(ImageView image) {
		super(image);
		myProjectileManager = new ProjectileManager();
	}
	
	public ObservableList<Projectile> getProjectiles(){
		return myProjectileManager.getObservableListOfActive();
	}
	
	public void increaseHitCount(int increaseAmount) {
		hitCount+=increaseAmount;
	}
	
	public void checkForCollision(ShootingSprites shooter, List<Projectile> projectiles) {
		this.checkTowerEnemyCollision(shooter);
		for (Projectile projectile: projectiles) {
			ImageView projectileImageView = projectile.getImage();
			if(this.getImage().intersects(projectileImageView.getX(), projectileImageView.getY(), projectileImageView.getFitWidth(), projectileImageView.getFitHeight())){
				this.handleCollision();
				projectile.handleCollision();
			}
		}
	}

	public void checkTowerEnemyCollision(ShootingSprites shooter) {
		if (this.getImage().intersects(shooter.getImage().getX(), shooter.getImage().getY(), shooter.getImage().getFitWidth(), shooter.getImage().getFitHeight())) {
			this.handleCollision();
			shooter.handleCollision();
		}
	}

}
