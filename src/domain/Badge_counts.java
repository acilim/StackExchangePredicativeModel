package domain;

public class Badge_counts {
	
	private int bronze;
	private int silver;
	private int gold;

	public int getBronze() {
		return bronze;
	}

	public void setBronze(int bronze) {
		this.bronze = bronze;
	}

	public int getSilver() {
		return silver;
	}

	public void setSilver(int silver) {
		this.silver = silver;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	@Override
	public String toString() {
		return " bronze:" + bronze + ", silver:" + silver
				+ ", gold:" + gold + " }";
	}
	

}
