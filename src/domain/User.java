package domain;

import java.util.List;

public class User {

	private int user_id;
	private int account_id;
	private int accept_rate;
	private boolean is_employee;
	private long last_modified_date;
	private long last_access_date;
	private int reputation_change_year;
	private int reputation_change_quarter;
	private int reputation_change_month;
	private int reputation_change_week;
	private int reputation_change_day;
	private int reputation;
	private long creation_date;
	private String user_type;
	private String link;
	private String profile_image;
	private String display_name;
	private Badge_counts badge_counts;
	private List<Badge> badges;

	// feature A1
	private long age_of_account;

	// feature A2
	private double badge_score;

	// feature A3
	private int posts_with_negative_scores;

	// feature B1
	private int post_score;

	// feature B2
	private int accepted_answer_score;

	// feature B3
	private int comment_score;

	public int getAccepted_answer_score() {
		return accepted_answer_score;
	}

	public void setAccepted_answer_score(int accepted_answer_score) {
		this.accepted_answer_score = accepted_answer_score;
	}

	public int getPost_score() {
		return post_score;
	}

	public void setPost_score(int post_score) {
		this.post_score = post_score;
	}

	public int getComment_score() {
		return comment_score;
	}

	public void setComment_score(int comment_score) {
		this.comment_score = comment_score;
	}

	public int getPosts_with_negative_scores() {
		return posts_with_negative_scores;
	}

	public void setPosts_with_negative_scores(int posts_with_negative_scores) {
		this.posts_with_negative_scores = posts_with_negative_scores;
	}

	public double getBadge_score() {
		return badge_score;
	}

	public void setBadge_score(double badge_score) {
		this.badge_score = badge_score;
	}

	public long getAge_of_account() {
		return age_of_account;
	}

	public void setAge_of_account(long age_of_account) {
		this.age_of_account = age_of_account;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getAccount_id() {
		return account_id;
	}

	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}

	public boolean isIs_employee() {
		return is_employee;
	}

	public void setIs_employee(boolean is_employee) {
		this.is_employee = is_employee;
	}

	public long getLast_modified_date() {
		return last_modified_date;
	}

	public void setLast_modified_date(long last_modified_date) {
		this.last_modified_date = last_modified_date;
	}

	public long getLast_access_date() {
		return last_access_date;
	}

	public void setLast_access_date(long last_access_date) {
		this.last_access_date = last_access_date;
	}

	public int getReputation_change_year() {
		return reputation_change_year;
	}

	public void setReputation_change_year(int reputation_change_year) {
		this.reputation_change_year = reputation_change_year;
	}

	public int getReputation_change_quarter() {
		return reputation_change_quarter;
	}

	public void setReputation_change_quarter(int reputation_change_quarter) {
		this.reputation_change_quarter = reputation_change_quarter;
	}

	public int getReputation_change_month() {
		return reputation_change_month;
	}

	public void setReputation_change_month(int reputation_change_month) {
		this.reputation_change_month = reputation_change_month;
	}

	public int getReputation_change_week() {
		return reputation_change_week;
	}

	public void setReputation_change_week(int reputation_change_week) {
		this.reputation_change_week = reputation_change_week;
	}

	public int getReputation_change_day() {
		return reputation_change_day;
	}

	public void setReputation_change_day(int reputation_change_day) {
		this.reputation_change_day = reputation_change_day;
	}

	public int getReputation() {
		return reputation;
	}

	public void setReputation(int reputation) {
		this.reputation = reputation;
	}

	public long getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(long creation_date) {
		this.creation_date = creation_date;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getProfile_image() {
		return profile_image;
	}

	public void setProfile_image(String profile_image) {
		this.profile_image = profile_image;
	}

	public String getDisplay_name() {
		return display_name;
	}

	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}

	public int getAccept_rate() {
		return accept_rate;
	}

	public void setAccept_rate(int accept_rate) {
		this.accept_rate = accept_rate;
	}

	public Badge_counts getBadge_counts() {
		return badge_counts;
	}

	public void setBadge_counts(Badge_counts badge_counts) {
		this.badge_counts = badge_counts;
	}

	public List<Badge> getBadges() {
		return badges;
	}

	public void setBadges(List<Badge> badges) {
		this.badges = badges;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + user_id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (user_id != other.user_id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "\n user_id:" + user_id + ",\n account_id:" + account_id
				+ ",\n accept_rate:" + accept_rate + ",\n is_employee:"
				+ is_employee + ",\n last_modified_date:" + last_modified_date
				+ ",\n last_access_date:" + last_access_date
				+ ",\n reputation:" + reputation + ",\n creation_date:"
				+ creation_date + ",\n user_type:" + user_type + ",\n link:"
				+ link + ",\n profile_image:" + profile_image
				+ ",\n display_name:" + display_name + ",\n badge_counts:"
				+ badge_counts + ",\n age of account: " + age_of_account
				+ ",\n badge_score: " + badge_score
				+ ",\n posts_with_negative_scores:"
				+ posts_with_negative_scores + ",\n post_score: " + post_score
				+ ",\n comment_score: " + comment_score
				+ ",\n accepted_answer_score: " + accepted_answer_score + "}";
	}

}
