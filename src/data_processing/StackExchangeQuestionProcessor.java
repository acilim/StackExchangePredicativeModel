package data_processing;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import domain.Badge;
import domain.Question;
import domain.User;

/**
 * The class that adds the features to the questions
 * @author Milica
 *
 */
public class StackExchangeQuestionProcessor {

	private static StackExchangeQuestionProcessor instance;

	private StackExchangeQuestionProcessor() {

	}

	public static StackExchangeQuestionProcessor getInstance() {
		if (instance == null) {
			instance = new StackExchangeQuestionProcessor();
		}
		return instance;
	}

	/**
	 * Gets closed questions from .json file, sets the values of features for
	 * them and writes them to a new .json file.
	 * 
	 */
	public void processClosedQuestions() {

		Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting()
				.create();

		try {
			System.out.println("Getting closed questions from file...\n");
			List<Question> closedQuestions = getClosedQuestionsFromFile();

			for (int i = 0; i < closedQuestions.size(); i++) {

				System.out.println("\nParsing question " + (i + 1) + "...");
				Question q = closedQuestions.get(i);

				System.out.println("Setting the values of question content and textual style features...\n");
				// features group C:
				q.setNum_of_URLs(calculateNum_of_URLs(q.getBody()));
				q.setNum_of_stackOwerflow_URLs(calculateNum_of_stackOverlow_URLs(q
						.getBody()));

				//features group D:
				q.setTitle_length(calculateTitle_length(q.getTitle()));
				q.setBody_length(calculateBody_length(q.getBody()));
				q.setNum_of_tags(calculateNum_of_tags(q.getTags()));
				q.setNum_of_punctuation_marks(calculateNum_of_punctuation_marks(q
						.getBody()));
				q.setNum_of_short_words(calculateNum_of_short_words(q.getBody()));
				q.setNum_of_special_characters(calculateNum_of_special_characters(q
						.getBody()));
				q.setNum_of_lower_case_characters(calculateNum_of_lower_case_characters(q
						.getBody()));
				q.setNum_of_upper_case_characters(calculateNum_of_upper_case_characters(q
						.getBody()));
				q.setCode_snippet_length(calculate_code_snippet_length(q.getBody()));

				System.out
						.println("Getting data about user...");
				User u = StackExchangeAPICommunication.getInstance()
						.getUserByID(q.getOwner().getUser_id());
				
				System.out.println("Setting user's age of account...");
				// feture A1
				u.setAge_of_account(new Date().getTime() - u.getCreation_date());

				System.out.println("Getting the list of user's badges...");
				u.setBadges(StackExchangeAPICommunication.getInstance()
						.getBadgesByUserID(u.getUser_id()));

				System.out.println("Getting user's badge score...");
				// feature A2
				u.setBadge_score(calculateBadgeScore(u.getBadges()));

				System.out.println("Getting posts features...");
				int[] features = StackExchangeAPICommunication.getInstance()
						.getPostsData(u.getUser_id());

				System.out
						.println("Getting user's posts with negative scores...");
				// feature A3
				u.setPosts_with_negative_scores(features[0]);

				System.out.println("Getting user's post score... ");
				// feature B1
				u.setPost_score(features[1]);

				System.out.println("Getting user's accepted answer score...");
				// feature B2
				u.setAccepted_answer_score(features[2]);

				System.out.println("Getting user's comment score...");
				// feature B3
				u.setComment_score(StackExchangeAPICommunication.getInstance()
						.getCommentScore(u.getUser_id()));

				q.setOwner(u);

				System.out.println(q);
				System.out.println("Question " + (i + 1) + " parsed. \n");
			}
			System.out.println("All closed questions parsed.");
			FileWriter writer = new FileWriter(
					"data/closedQuestionsWithFeaturesAll.json");
			for (int i = 0; i < closedQuestions.size(); i++) {
				writer.write(gson.toJson(closedQuestions.get(i)) + ",\n");
			}
			writer.close();

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets not closed questions from .json file, sets the values of features
	 * for them and writes them to a new .json file.
	 * 
	 */
	public void processNotClosedQuestions() {
		Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting()
				.create();

		try {
			System.out.println("Getting not closed questions from file...\n");
			List<Question> notClosedQuestions = getNotClosedQuestionsFromFile();

			for (int i = 0; i < notClosedQuestions.size(); i++) {

				System.out.println("\nParsing question " + (i + 1) + "...");
				Question q = notClosedQuestions.get(i);
				
				System.out.println("Setting the values of question content and textual style features...\n");
				// features group C:
				q.setNum_of_URLs(calculateNum_of_URLs(q.getBody()));
				q.setNum_of_stackOwerflow_URLs(calculateNum_of_stackOverlow_URLs(q
						.getBody()));

				//features group D:
				q.setTitle_length(calculateTitle_length(q.getTitle()));
				q.setBody_length(calculateBody_length(q.getBody()));
				q.setNum_of_tags(calculateNum_of_tags(q.getTags()));
				q.setNum_of_punctuation_marks(calculateNum_of_punctuation_marks(q
						.getBody()));
				q.setNum_of_short_words(calculateNum_of_short_words(q.getBody()));
				q.setNum_of_special_characters(calculateNum_of_special_characters(q
						.getBody()));
				q.setNum_of_lower_case_characters(calculateNum_of_lower_case_characters(q
						.getBody()));
				q.setNum_of_upper_case_characters(calculateNum_of_upper_case_characters(q
						.getBody()));
				q.setCode_snippet_length(calculate_code_snippet_length(q.getBody()));

				System.out
						.println("Getting data about user...");
				User u = StackExchangeAPICommunication.getInstance()
						.getUserByID(q.getOwner().getUser_id());
				
				System.out.println("Setting user's age of account...");
				// feture A1
				u.setAge_of_account(new Date().getTime() - u.getCreation_date());

				System.out.println("Getting the list of user's badges...");
				u.setBadges(StackExchangeAPICommunication.getInstance()
						.getBadgesByUserID(u.getUser_id()));

				System.out.println("Getting user's badge score...");
				// feature A2
				u.setBadge_score(calculateBadgeScore(u.getBadges()));

				System.out.println("Getting posts features...");
				int[] features = StackExchangeAPICommunication.getInstance()
						.getPostsData(u.getUser_id());

				System.out
						.println("Getting user's posts with negative scores...");
				// feature A3
				u.setPosts_with_negative_scores(features[0]);

				System.out.println("Getting user's post score... ");
				// feature B1
				u.setPost_score(features[1]);

				System.out.println("Getting user's accepted answer score...");
				// feature B2
				u.setAccepted_answer_score(features[2]);

				System.out.println("Getting user's comment score...");
				// feature B3
				u.setComment_score(StackExchangeAPICommunication.getInstance()
						.getCommentScore(u.getUser_id()));

				q.setOwner(u);

				System.out.println(q);
				System.out.println("Question " + (i + 1) + " parsed. \n");
			}
			System.out.println("All not closed questions parsed.");
			FileWriter writer = new FileWriter(
					"data/notClosedQuestionsWithFeaturesAll.json");
			for (int i = 0; i < notClosedQuestions.size(); i++) {
				writer.write(gson.toJson(notClosedQuestions.get(i)) + ",\n");
			}
			writer.close();

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets closed questions from .json file and transforms them to java
	 * objects.
	 * 
	 * @return list of Question objects
	 */
	public List<Question> getClosedQuestionsFromFile() {

		List<Question> questions = new ArrayList<Question>();

		try {
			FileReader reader = new FileReader("data/closedQuestions.json");

			Gson gson = new GsonBuilder().disableHtmlEscaping()
					.setPrettyPrinting().create();
			JsonObject jsonResult = gson.fromJson(reader, JsonObject.class);
			JsonArray questionsArray = (JsonArray) jsonResult.get("items");

			questions = StackExchangeJsonParser
					.parseQuestionsArray(questionsArray);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return questions;
	}

	/**
	 * Gets not closed questions from .json file and transforms them to java
	 * objects.
	 * 
	 * @return list of Question objects
	 */
	public List<Question> getNotClosedQuestionsFromFile() {

		List<Question> questions = new ArrayList<Question>();

		try {
			FileReader reader = new FileReader("data/notClosedQuestions.json");

			Gson gson = new GsonBuilder().disableHtmlEscaping()
					.setPrettyPrinting().create();
			JsonObject jsonResult = gson.fromJson(reader, JsonObject.class);
			JsonArray questionsArray = (JsonArray) jsonResult.get("items");

			questions = StackExchangeJsonParser
					.parseQuestionsArray(questionsArray);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return questions;
	}
	
	/**
	 * Calculates the value of feature D9 - code_snippet_length
	 * 
	 * @param body
	 *            Body of the question
	 * @return code_snippet_length of the body
	 */
	private static int calculate_code_snippet_length(String body) {

		int len = 0;
		int p = 0;
		int startPos = 0;

		while (startPos >= 0 && p < body.length()) {
			startPos = body.indexOf("<code>", p);
			if (startPos > 0) {
				int endPos = body.indexOf("</code>", startPos);
				len += endPos - startPos;
				p = endPos;
			}
		}
		return len;
	}

	/**
	 * Calculates the value of feature D8 - num_of_upper_case_characters
	 * 
	 * @param body
	 *            Body of the question
	 * @return num_of_upper_case_characters in the body
	 */
	private static int calculateNum_of_upper_case_characters(String body) {

		int num = 0;

		for (int k = 0; k < body.length(); k++) {

			if (Character.isUpperCase(body.charAt(k)))
				num++;
		}

		return num;

	}

	/**
	 * Calculates the value of feature D7 - num_of_lower_case_characters
	 * 
	 * @param body
	 *            Body of the question
	 * @return num_of_lower_case_characters in the body
	 */
	private static int calculateNum_of_lower_case_characters(String body) {

		int num = 0;

		for (int k = 0; k < body.length(); k++) {

			if (Character.isLowerCase(body.charAt(k)))
				num++;
		}

		return num;
	}

	/**
	 * Calculates the value of feature D6 - num_of_special_characters
	 * 
	 * @param body
	 *            Body of the question
	 * @return num_of_special_characters in the body
	 */
	private static int calculateNum_of_special_characters(String body) {

		String specialCharsRegex = "[!@#$%^&*()\\[\\]|'./{}\\\\:\"<>?]";

		return body.split(specialCharsRegex, -1).length - 1;

	}

	/**
	 * Calculates the value of feature D5 - num_of_short_words
	 * 
	 * @param body
	 *            Body of the question
	 * @return num_of_short_words in the body
	 */
	private static int calculateNum_of_short_words(String body) {

		int num = 0;

		String[] words = body.replaceAll("[^a-zA-Z ]", "").split("\\s+");
		for (int i = 0; i < words.length; i++) {
			if (words[i].length() <= 3) {
				num++;
			}
		}

		return num;
	}

	/**
	 * Calculates the value of feature D4 - num_of_punctuation_marks
	 * 
	 * @param body
	 *            Body of the question
	 * @return num_of_punctuation_marks in the body
	 */
	private static int calculateNum_of_punctuation_marks(String body) {

		int num = 0;
		String punctuationMarks = ".,!?:;";

		for (int i = 0; i < body.length(); i++) {
			if (punctuationMarks.indexOf(body.charAt(i)) >= 0) {
				num++;
			}
		}
		return num;
	}

	/**
	 * Calculates the value of feature D3 - num_of_tags
	 * 
	 * @param tags
	 *            List of question's tags
	 * @return Size of the list of tags
	 */
	private static int calculateNum_of_tags(List<String> tags) {
		return tags.size();
	}

	/**
	 * Calculates the value of feature D2 - body_length
	 * 
	 * @param body
	 *            Body of the question
	 * @return length of the body
	 */
	private static int calculateBody_length(String body) {
		return body.length();
	}

	/**
	 * Calculates the value of feature D1 - title_length
	 * 
	 * @param title
	 *            Title of the question
	 * @return length of the title
	 */
	private static int calculateTitle_length(String title) {
		return title.length();
	}

	/**
	 * Calculates the value of feature C2 - num_of_stackOverflow_URLs
	 * 
	 * @param body
	 *            Body of the question
	 * @return num_of_stackOverflow_URLs in the question's body
	 */
	private static int calculateNum_of_stackOverlow_URLs(String body) {

		int num = 0;

		Pattern p = Pattern.compile("stackoverflow.com/");
		Matcher m = p.matcher(body);

		while (m.find()) {
			num++;
		}
		return num;
	}

	/**
	 * Calculates the value of feature C1 - num_of_URLs
	 * 
	 * @param body
	 *            Body of the question
	 * @return num_of_URLs in the question's body
	 */
	private static int calculateNum_of_URLs(String body) {

		int num = 0;

		Pattern p = Pattern.compile("<a href=", Pattern.DOTALL);
		Matcher m = p.matcher(body);

		while (m.find()) {
			num++;
		}
		return num;
	}
	

	/**
	 * Gets the data about each user's badge and calculates the value of feature A2 - badge_score
	 * @param badges
	 * @return
	 */
	private double calculateBadgeScore(List<Badge> badges) {
		
		double badgeScore = 0;
		for(int i=0; i<badges.size(); i++){
			Badge badge = StackExchangeAPICommunication.getInstance().getBadgeByID(badges.get(i).getBadge_id());
			badgeScore += 1.0 / badge.getAward_count();
		}
		return badgeScore;
		
	}
}
