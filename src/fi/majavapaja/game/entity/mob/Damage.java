package fi.majavapaja.game.entity.mob;

public class Damage {
	public static final int ALLTYPE = -1;
	public static final int NOTYPE = 0;
	public static final int FALL = 1;
	public static final int OUTOFTHEWORLD = 2;
	public static final int LAVA = 3;

	private int amount;
	private int type;

	public Damage(int amount, int type) {
		this.amount = amount;
		this.type = type;
	}

	public int getAmount() {
		return amount;
	}

	public int getType() {
		return type;
	}

	public String getMessage() {
		if (type == NOTYPE) {
			return "Died.";
		}
		if (type == FALL) {
			return "fell to his death.";
		}
		if (type == OUTOFTHEWORLD){
			return "fell out of the world!";
		}
		if (type == LAVA){
			return "tried to swim in lava!";
		}
		return null;
	}
	
	public void reduce(Damage dmg){
		amount -= dmg.getAmount();
		if(amount < 0) amount = 0;
	}
}
