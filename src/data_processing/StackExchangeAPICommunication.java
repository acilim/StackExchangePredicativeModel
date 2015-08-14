package data_processing;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import domain.Badge;
import domain.User;

/**
 * The class that is used for communicating with StackExchangeAPI
 * 
 * @author Milica
 * 
 */
public class StackExchangeAPICommunication {

	private String apiURL;
	private String access_data;
	
	private HttpClient client;
	private HttpGet request;
	private BufferedReader br;

	private Properties properties;
	
	static StackExchangeAPICommunication instance;

	private StackExchangeAPICommunication() {		
		properties = loadProperties("config/config.properties");
		apiURL = properties.getProperty("apiURL");
		access_data = "&access_token=" + properties.getProperty("access_token") + "&key=" + properties.getProperty("key");
	}

	public static StackExchangeAPICommunication getInstance() {
		if (instance == null) {
			instance = new StackExchangeAPICommunication();
		}
		return instance;
	}

	/**
	 * Calls StackExchangeAPI /users/{ids} method to get the data about user
	 * 
	 * @param id
	 *            id of the user
	 * @return User object with features
	 */
	public User getUserByID(int id) {

		User user = new User();

		String userByIDUrl = apiURL + "/users/" + id
				+ "?order=desc&sort=reputation&site=stackoverflow"
				+ access_data;

		String result = getResultByUrl(userByIDUrl);

		Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting()
				.create();
		JsonObject jsonResult = gson.fromJson(result.toString(),
				JsonObject.class);
		JsonArray userArray = (JsonArray) jsonResult.get("items");

		user = StackExchangeJsonParser.parseUser((JsonObject) userArray.get(0));

		return user;
	}

	/**
	 * Calls StackExchangeAPI /users/{ids}/badges method to get all the badges
	 * for the user
	 * 
	 * @param userID
	 *            id of the user
	 * @return list of Badge objects
	 */
	public List<Badge> getBadgesByUserID(int userID) {

		List<Badge> badges = new ArrayList<Badge>();

		String badgesByIDsURL = apiURL + "/users/" + userID
				+ "/badges?order=desc&sort=rank&site=stackoverflow"
				+ access_data;

		String result = getResultByUrl(badgesByIDsURL);

		Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting()
				.create();
		JsonObject jsonResult = gson.fromJson(result.toString(),
				JsonObject.class);
		JsonArray badgesArray = (JsonArray) jsonResult.get("items");

		badges = StackExchangeJsonParser.parseBadgesArray(badgesArray);

		return badges;

	}

	/**
	 * Calls StackExchangeAPI /badges/{ids} method to get the data about the badge
	 * 
	 * @param badgeID
	 *            id of the badge
	 * @return Badge object
	 */
	public Badge getBadgeByID(int badgeID) {

		String badgeByIdURL = apiURL + "/badges/"
				+ badgeID
				+ "?order=desc&sort=rank&site=stackoverflow" + access_data;
		String result = getResultByUrl(badgeByIdURL);

		Gson gson = new GsonBuilder().disableHtmlEscaping()
				.setPrettyPrinting().create();
		JsonObject jsonResult = gson.fromJson(result.toString(),
				JsonObject.class);
		JsonArray badgesArray = (JsonArray) jsonResult.get("items");

			return StackExchangeJsonParser
					.parseBadge((JsonObject) badgesArray.get(0));

	}

	/**
	 * Calls StackExchangeApi /users/{ids}/questions method to get all questions
	 * posted by user and /users/{ids}/answers method to get all answers posted
	 * by user returns number of posts_with_negative_scores (feature A3),
	 * post_score(feature B1) and accepted_answer_score (feature B2)
	 * 
	 * @param userID
	 *            id of the user
	 * @return features - array of int; features[0] has the value of
	 *         posts_with_negative_scores, features[1]: post_score and
	 *         features[2]: accepted_answer_score
	 */
	public int[] getPostsData(int userID) {

		int[] features = new int[3];

		String questionsURL = apiURL + "/users/" + userID
				+ "/questions?order=desc&sort=activity&site=stackoverflow"
				+ access_data;
		String resultQuestions = getResultByUrl(questionsURL);

		String answersURL = apiURL + "/users/" + userID
				+ "/answers?order=desc&sort=activity&site=stackoverflow"
				+ access_data;
		String resultAnswers = getResultByUrl(answersURL);

		Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting()
				.create();

		JsonObject jsonResultQuestions = gson.fromJson(
				resultQuestions.toString(), JsonObject.class);
		JsonArray questionsArray = (JsonArray) jsonResultQuestions.get("items");
		int numOfNegQuestions = StackExchangeJsonParser
				.parseQuestionsNegScores(questionsArray);
		int sumOfQuestionScores = StackExchangeJsonParser
				.parseQuestionsScore(questionsArray);

		JsonObject jsonResultAnswers = gson.fromJson(resultAnswers.toString(),
				JsonObject.class);
		JsonArray answersArray = (JsonArray) jsonResultAnswers.get("items");
		int numOfNegAnswers = StackExchangeJsonParser
				.parseAnswersNegScores(answersArray);
		int sumOfAnswerScores = StackExchangeJsonParser
				.parseAnswersScore(answersArray);
		int acceptedAnswerScore = StackExchangeJsonParser
				.parseAcceptedAnswersScore(answersArray);

		// posts_with_negative_score
		features[0] = numOfNegQuestions + numOfNegAnswers;
		// post_score
		features[1] = sumOfQuestionScores + sumOfAnswerScores;
		// accepted_answer_score
		features[2] = acceptedAnswerScore;

		return features;

	}

	/**
	 * Calls StackExchangeApi /users/{ids}/comments method to get all comments
	 * posted by user and returns comment_score
	 * 
	 * @param userID
	 *            id of the user
	 * @return comment_score (sum of scores for all the user's comments)
	 */
	public int getCommentScore(int userID) {

		String commentsURL = apiURL + "/users/" + userID
				+ "/comments?order=desc&sort=creation&site=stackoverflow"
				+ access_data;
		String result = getResultByUrl(commentsURL);

		Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting()
				.create();

		JsonObject jsonResultComments = gson.fromJson(result.toString(),
				JsonObject.class);
		JsonArray commentsArray = (JsonArray) jsonResultComments.get("items");
		int commentScore = StackExchangeJsonParser
				.parseCommentScores(commentsArray);

		return commentScore;

	}

	/**
	 * Creates a HttpClient and sends a HttpRequest to a given url. If there is
	 * a connection timeout tries to send the request again for 7 times. If the response
	 * contains 'backoff' parameter, waits for the requested number of seconds
	 * before sending another request. Reads and returns the response from url.
	 * 
	 * @param url
	 *            url to send the request to
	 * @return result from url
	 */
	private String getResultByUrl(String url) {

		RequestConfig.Builder requestBuilder = RequestConfig.custom();
		requestBuilder = requestBuilder.setConnectTimeout(75000);
		requestBuilder = requestBuilder.setConnectionRequestTimeout(75000);
		requestBuilder.setSocketTimeout(75000);

		HttpClientBuilder builder = HttpClientBuilder.create();
		builder.setDefaultRequestConfig(requestBuilder.build());
		client = builder.build();

		request = new HttpGet(url);
		request.addHeader("Accept", "application/json");

		HttpResponse response = null;
		String r = "";

		try {
			for (int i = 0; i < 7; i++) {
				try {
					System.out.println("Sending request: " + url);
					response = client.execute(request);
					break;
				} catch (ConnectionPoolTimeoutException e) {
					System.out.println("Connection timeout. Trying again...");
					try {
						TimeUnit.SECONDS.sleep(10);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}
			}

			System.out.println("Response Code : "
					+ response.getStatusLine().getStatusCode());

			br = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = br.readLine()) != null) {
				result.append(line);
			}
			System.out.println(result + "\n");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			r = result.toString();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			JsonObject jsonResult = gson.fromJson(result.toString(),
					JsonObject.class);
			if (jsonResult.get("backoff") != null) {
				int backoff = (jsonResult.get("backoff")).getAsInt();
				try {
					System.out.println("Received backoff. Waiting for "
							+ backoff + "seconds...");
					TimeUnit.SECONDS.sleep(backoff + 5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return r;
	}
	
	/**
	 * Loads properties from a given file name
	 * @param fileName	name of the .properties file
	 * @return	Properties object
	 */
	private Properties loadProperties(String fileName){
		
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream(fileName);
			prop.load(input);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;	  
	}

}
