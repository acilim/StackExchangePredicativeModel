package domain;

public class Badge {

	private int badge_id;
	private int award_count;
	private String badge_type;
	private String rank;
	private String link;
	private String name;


	public int getBadge_id() {
		return badge_id;
	}

	public void setBadge_id(int badge_id) {
		this.badge_id = badge_id;
	}

	public int getAward_count() {
		return award_count;
	}

	public void setAward_count(int award_count) {
		this.award_count = award_count;
	}

	public String getBadge_type() {
		return badge_type;
	}

	public void setBadge_type(String badge_type) {
		this.badge_type = badge_type;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + badge_id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Badge))
			return false;
		Badge other = (Badge) obj;
		if (badge_id != other.badge_id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Badge {badge_id:" + badge_id + ",\n award_count:" + award_count
				+ ",\n badge_type:" + badge_type + ",\n rank:" + rank
				+ ",\n link:" + link + ",\n name:" + name + "}";
	}

}
