package domain;

import java.util.List;

public class Question {

	private int question_id;
	private String title;
	private int view_count;
	private int score;
	private List<String> tags;
	private int answer_count;
	private String body;
	private long creation_date;
	private boolean is_answered;
	private long last_activity_date;
	private long last_edit_date;
	private String link;
	private User owner;
	private int accepted_answer_id;
	private long closed_date;
	private String closed_reason;

	// feature C1
	private int num_of_URLs;
	// feature C2
	private int num_of_stackOwerflow_URLs;

	// feature D1
	private int title_length;
	// feature D2
	private int body_length;
	// feature D3
	private int num_of_tags;
	// feature D4
	private int num_of_punctuation_marks;
	// feature D5
	private int num_of_short_words;
	// feature D6
	private int num_of_special_characters;
	// feature D7
	private int num_of_lower_case_characters;
	// feature D8
	private int num_of_upper_case_characters;
	// feature D10
	private int code_snippet_length;

	public int getQuestion_id() {
		return question_id;
	}

	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getView_count() {
		return view_count;
	}

	public void setView_count(int view_count) {
		this.view_count = view_count;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public int getAnswer_count() {
		return answer_count;
	}

	public void setAnswer_count(int answer_count) {
		this.answer_count = answer_count;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public long getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(long creation_date) {
		this.creation_date = creation_date;
	}

	public boolean isIs_answered() {
		return is_answered;
	}

	public void setIs_answered(boolean is_answered) {
		this.is_answered = is_answered;
	}

	public long getLast_activity_date() {
		return last_activity_date;
	}

	public void setLast_activity_date(long last_activity_date) {
		this.last_activity_date = last_activity_date;
	}

	public long getLast_edit_date() {
		return last_edit_date;
	}

	public void setLast_edit_date(long last_edit_date) {
		this.last_edit_date = last_edit_date;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public int getAccepted_answer_id() {
		return accepted_answer_id;
	}

	public void setAccepted_answer_id(int accepted_answer_id) {
		this.accepted_answer_id = accepted_answer_id;
	}

	public long getClosed_date() {
		return closed_date;
	}

	public void setClosed_date(long closed_date) {
		this.closed_date = closed_date;
	}

	public String getClosed_reason() {
		return closed_reason;
	}

	public void setClosed_reason(String closed_reason) {
		this.closed_reason = closed_reason;
	}

	public int getNum_of_URLs() {
		return num_of_URLs;
	}

	public void setNum_of_URLs(int number_of_URLs) {
		this.num_of_URLs = number_of_URLs;
	}

	public int getNum_of_stackOwerflow_URLs() {
		return num_of_stackOwerflow_URLs;
	}

	public void setNum_of_stackOwerflow_URLs(int number_of_stackOwerflow_URLs) {
		this.num_of_stackOwerflow_URLs = number_of_stackOwerflow_URLs;
	}

	public int getTitle_length() {
		return title_length;
	}

	public void setTitle_length(int title_length) {
		this.title_length = title_length;
	}

	public int getBody_length() {
		return body_length;
	}

	public void setBody_length(int body_length) {
		this.body_length = body_length;
	}

	public int getNum_of_tags() {
		return num_of_tags;
	}

	public void setNum_of_tags(int num_of_tags) {
		this.num_of_tags = num_of_tags;
	}

	public int getNum_of_punctuation_marks() {
		return num_of_punctuation_marks;
	}

	public void setNum_of_punctuation_marks(int num_of_punctuation_marks) {
		this.num_of_punctuation_marks = num_of_punctuation_marks;
	}

	public int getNum_of_short_words() {
		return num_of_short_words;
	}

	public void setNum_of_short_words(int num_of_short_words) {
		this.num_of_short_words = num_of_short_words;
	}

	public int getNum_of_special_characters() {
		return num_of_special_characters;
	}

	public void setNum_of_special_characters(int num_of_special_characters) {
		this.num_of_special_characters = num_of_special_characters;
	}

	public int getNum_of_lower_case_characters() {
		return num_of_lower_case_characters;
	}

	public void setNum_of_lower_case_characters(int num_of_lower_case_characters) {
		this.num_of_lower_case_characters = num_of_lower_case_characters;
	}

	public int getNum_of_upper_case_characters() {
		return num_of_upper_case_characters;
	}

	public void setNum_of_upper_case_characters(int num_of_upper_case_characters) {
		this.num_of_upper_case_characters = num_of_upper_case_characters;
	}

	public int getCode_snippet_length() {
		return code_snippet_length;
	}

	public void setCode_snippet_length(int code_snippet_length) {
		this.code_snippet_length = code_snippet_length;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + question_id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Question))
			return false;
		Question other = (Question) obj;
		if (question_id != other.question_id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Question \nquestion_id:" + question_id + ",\n title:" + title
				+ ",\n view_count:" + view_count + ",\n score:" + score
				+ ",\n tags:" + tags + ",\n answer_count:" + answer_count
				+ ",\n body:" + body + ",\n creation_date:" + creation_date
				+ ",\n is_answered:" + is_answered + ",\n last_activity_date:"
				+ last_activity_date + ",\n last_edit_date:" + last_edit_date
				+ ",\n link:" + link + ",\n owner:" + owner
				+ ",\n accepted_answer_id:" + accepted_answer_id
				+ ",\n closed_date:" + closed_date + ",\n closed_reason:"
				+ closed_reason + ",\n num_of_URLs:" + num_of_URLs
				+ "\n, num_of_stackOverflow_URLs: " + num_of_stackOwerflow_URLs
				+ ",\n title_length:" + title_length + ",\n,body_length: "
				+ body_length + ",\n num_of_tags: " + num_of_tags
				+ ",\n num_of_punctuation_marks: " + num_of_punctuation_marks
				+ ",\n num_of_short_words: " + num_of_short_words
				+ ",\n num_of_special_characters: " + num_of_special_characters
				+ ",\n num_of_lower_case_characters: "
				+ num_of_lower_case_characters
				+ ",\n num_of_upper_case_characters: "
				+ num_of_upper_case_characters + ",\n code_snippet_length: "
				+ code_snippet_length + "}";
	}

}
