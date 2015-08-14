package data_processing;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import domain.Badge;
import domain.Badge_counts;
import domain.Question;
import domain.User;

/**
 * The class used for parsing json objects and converting them to java objects
 * 
 * @author Milica
 * 
 */
public class StackExchangeJsonParser {

	/**
	 * Parses the JsonArray of questions by calling parseQuestion() method on
	 * each question
	 * 
	 * @param questionsJsonArray
	 *            array of questions to parse
	 * @return list of Question objects
	 */
	public static List<Question> parseQuestionsArray(
			JsonArray questionsJsonArray) {

		List<Question> questions = new ArrayList<Question>();

		for (int i = 0; i < questionsJsonArray.size(); i++) {
			questions
					.add(parseQuestion((JsonObject) questionsJsonArray.get(i)));

		}
		return questions;
	}

	/**
	 * Parses JsonObject and returns a Question
	 * D
	 * 
	 * @param qElement
	 *            JsonObject that contains a question
	 * @return Question object
	 */
	private static Question parseQuestion(JsonObject qElement) {

		Question q = new Question();

		q.setQuestion_id(qElement.get("question_id").getAsInt());
		if (qElement.get("accepted_answer_id") != null) {
			q.setAccepted_answer_id(qElement.get("accepted_answer_id")
					.getAsInt());
		}
		q.setAnswer_count(qElement.get("answer_count").getAsInt());
		q.setBody(qElement.get("body").toString().replaceAll("^\"|\"$", ""));
		if (qElement.get("closed_date") != null) {
			q.setClosed_date(qElement.get("closed_date").getAsLong());
		}
		if (qElement.get("closed_reason") != null) {
			q.setClosed_reason(qElement.get("closed_reason").toString()
					.replaceAll("^\"|\"$", ""));
		}
		q.setCreation_date(qElement.get("creation_date").getAsLong());
		q.setIs_answered(qElement.get("is_answered").getAsBoolean());
		q.setLast_activity_date(qElement.get("last_activity_date").getAsLong());
		if (qElement.get("last_edit_date") != null) {
			q.setLast_edit_date(qElement.get("last_edit_date").getAsLong());
		}
		q.setLink(qElement.get("link").toString().replaceAll("^\"|\"$", ""));
		q.setScore(qElement.get("score").getAsInt());
		q.setTitle(qElement.get("title").toString().replaceAll("^\"|\"$", ""));
		q.setView_count(qElement.get("view_count").getAsInt());

		User u = new User();
		u.setUser_id(((JsonObject) qElement.get("owner")).get("user_id")
				.getAsInt());
		q.setOwner(u);

		JsonArray tagsJsonArray = qElement.get("tags").getAsJsonArray();
		List<String> tags = new ArrayList<String>();
		for (int i = 0; i < tagsJsonArray.size(); i++) {
			tags.add((tagsJsonArray.get(i)).getAsJsonPrimitive().toString()
					.replaceAll("^\"|\"$", ""));
		}
		q.setTags(tags);

		return q;
	}	

	/**
	 * Parses JsonObject and returns a User
	 * account
	 * 
	 * @param userElement
	 *            JsonObject that contains user data
	 * @return User object
	 */
	public static User parseUser(JsonObject userElement) {

		User u = new User();

		u.setUser_id(userElement.get("user_id").getAsInt());
		u.setAccount_id(userElement.get("account_id").getAsInt());
		if (userElement.get("accept_rate") != null) {
			u.setAccept_rate(userElement.get("accept_rate").getAsInt());
		}
		u.setCreation_date(userElement.get("creation_date").getAsLong());
		u.setDisplay_name(userElement.get("display_name").toString()
				.replaceAll("^\"|\"$", ""));
		u.setIs_employee(userElement.get("is_employee").getAsBoolean());
		u.setLast_access_date(userElement.get("last_access_date").getAsLong());
		if (userElement.get("last_modified_date") != null) {
			u.setLast_access_date(userElement.get("last_modified_date")
					.getAsLong());
		}
		u.setLink(userElement.get("link").toString().replaceAll("^\"|\"$", ""));
		u.setReputation(userElement.get("reputation").getAsInt());
		u.setUser_type(userElement.get("user_type").getAsString()
				.replaceAll("^\"|\"$", ""));

		Badge_counts bc = new Badge_counts();
		bc.setGold(userElement.get("badge_counts").getAsJsonObject()
				.get("gold").getAsInt());
		bc.setSilver(userElement.get("badge_counts").getAsJsonObject()
				.get("silver").getAsInt());
		bc.setBronze(userElement.get("badge_counts").getAsJsonObject()
				.get("bronze").getAsInt());
		u.setBadge_counts(bc);		

		return u;
	}

	/**
	 * Parses JsonArray of badges by calling parseBadge() method on each badge
	 * 
	 * @param badgesArray
	 *            JsonArray of badges
	 * @return list of Badge objects
	 */
	public static List<Badge> parseBadgesArray(JsonArray badgesArray) {
		List<Badge> badges = new ArrayList<Badge>();

		for (int i = 0; i < badgesArray.size(); i++) {
			badges.add(parseBadge((JsonObject) badgesArray.get(i)));
		}

		return badges;
	}

	/**
	 * Parses JsonObject and returns a Badge
	 * 
	 * @param bElement
	 *            JsonObject that contains badge data
	 * @return Badge object
	 */
	public static Badge parseBadge(JsonObject bElement) {

		Badge b = new Badge();

		b.setBadge_id(bElement.get("badge_id").getAsInt());
		b.setAward_count(bElement.get("award_count").getAsInt());
		b.setBadge_type(bElement.get("badge_type").getAsString()
				.replaceAll("^\"|\"$", ""));
		b.setName(bElement.get("name").getAsString().replaceAll("^\"|\"$", ""));
		b.setRank(bElement.get("rank").getAsString().replaceAll("^\"|\"$", ""));
		b.setLink(bElement.get("link").getAsString().replaceAll("^\"|\"$", ""));

		return b;
	}

	/**
	 * Parses jsonArray of question and counts the number of questions that have negative score
	 * 
	 * @param questionsJsonArray
	 *            JsonArray of questions
	 * @return number of questions with negative score
	 */
	public static int parseQuestionsNegScores(JsonArray questionsJsonArray) {
		int num = 0;

		for (int i = 0; i < questionsJsonArray.size(); i++) {
			int score = ((JsonObject) questionsJsonArray.get(i)).get("score")
					.getAsInt();
			if (score < 0) {
				num++;
			}
		}
		return num;
	}

	/**
	 * Parses jsonArray of answers and returns the number of answers that have negative score
	 * 
	 * @param answersJsonArray
	 *            JsonArray of answers
	 * @return number of answers with negative score
	 */
	public static int parseAnswersNegScores(JsonArray answersJsonArray) {
		int num = 0;

		for (int i = 0; i < answersJsonArray.size(); i++) {
			int score = ((JsonObject) answersJsonArray.get(i)).get("score")
					.getAsInt();
			if (score < 0) {
				num++;
			}
		}
		return num;
	}

	/**
	 * Parses jsonArray of questions and returns the sum of questions' scores
	 * 
	 * @param questionsArray
	 *            JsonArray of questions
	 * @return sum of questions' scores
	 */
	public static int parseQuestionsScore(JsonArray questionsArray) {
		int sum = 0;

		for (int i = 0; i < questionsArray.size(); i++) {
			int score = ((JsonObject) questionsArray.get(i)).get("score")
					.getAsInt();
			sum += score;
		}
		return sum;
	}

	/**
	 * Parses jsonArray of answers and returns the sum of answers' scores
	 * 
	 * @param answersJsonArray
	 *            JsonArray of answers
	 * @return sum of answers' scores
	 */
	public static int parseAnswersScore(JsonArray answersJsonArray) {
		int sum = 0;

		for (int i = 0; i < answersJsonArray.size(); i++) {
			int score = ((JsonObject) answersJsonArray.get(i)).get("score")
					.getAsInt();
			sum += score;
		}
		return sum;
	}

	/**
	 * Parses jsonArray of comments and returns the sum of comments' scores
	 * 
	 * @param commentsArray
	 *            JsonArray of comments
	 * @return sum of comments' scores
	 */
	public static int parseCommentScores(JsonArray commentsArray) {
		int sum = 0;

		for (int i = 0; i < commentsArray.size(); i++) {
			int score = ((JsonObject) commentsArray.get(i)).get("score")
					.getAsInt();
			sum += score;
		}
		return sum;
	}

	/**
	 * Parses jsonArray of answers and calculates the accepted_answer_score feature's value
	 * 
	 * @param answersArray
	 *            JsonArray of answers
	 * @return accepted_answers_score (15*(number_of_accepted_answers))
	 */
	public static int parseAcceptedAnswersScore(JsonArray answersArray) {
		int sum = 0;

		for (int i = 0; i < answersArray.size(); i++) {
			if (((JsonObject) answersArray.get(i)).get("is_accepted")
					.getAsBoolean()) {
				sum += 15;
			}
		}
		return sum;
	}

	/**
	 * Parses JsonObject and returns a Question object with all the features
	 * 
	 * @param qElement
	 *            JsonObject with Question data
	 * @return Question object with all the features
	 */
	public static Question parseQuestionFeatures(JsonObject qElement) {

		Question q = new Question();

		q.setNum_of_URLs(qElement.get("num_of_URLs").getAsInt());
		q.setNum_of_stackOwerflow_URLs(qElement
				.get("num_of_stackOwerflow_URLs").getAsInt());

		q.setTitle_length(qElement.get("title_length").getAsInt());
		q.setBody_length(qElement.get("body_length").getAsInt());
		q.setNum_of_tags(qElement.get("num_of_tags").getAsInt());
		q.setNum_of_punctuation_marks(qElement.get("num_of_punctuation_marks")
				.getAsInt());
		q.setNum_of_short_words(qElement.get("num_of_short_words").getAsInt());
		q.setNum_of_special_characters(qElement
				.get("num_of_special_characters").getAsInt());
		q.setNum_of_lower_case_characters(qElement.get(
				"num_of_lower_case_characters").getAsInt());
		q.setNum_of_upper_case_characters(qElement.get(
				"num_of_upper_case_characters").getAsInt());
		q.setCode_snippet_length(qElement.get("code_snippet_length").getAsInt());

		User u = new User();

		u.setAge_of_account(((JsonObject) qElement.get("owner")).get(
				"age_of_account").getAsLong());
		u.setBadge_score(((JsonObject) qElement.get("owner"))
				.get("badge_score").getAsDouble() * 1000);
		u.setPosts_with_negative_scores(((JsonObject) qElement.get("owner"))
				.get("posts_with_negative_scores").getAsInt());

		u.setPost_score(((JsonObject) qElement.get("owner")).get("post_score")
				.getAsInt());
		u.setAccepted_answer_score(((JsonObject) qElement.get("owner")).get(
				"accepted_answer_score").getAsInt());
		u.setComment_score(((JsonObject) qElement.get("owner")).get(
				"comment_score").getAsInt());

		q.setOwner(u);

		return q;
	}

}
