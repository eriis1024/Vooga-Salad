package engine.sprites.properties;

/**
 * Damage Property represents how much damage an object can exert on another
 * 
 * @author Katherine Van Dyk
 *
 */
public class DamageProperty extends UpgradeProperty {
    public double myDamage;
	
	public DamageProperty(double cost, double value, double damage) {
	    	super(cost, value, damage);
	}
	
	public double getDamage() {
		return myDamage; 
	}
}
