package data_processing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import domain.Question;

public class StackExchangeArffDataSaver {
	
	private static StackExchangeArffDataSaver instance;
		
	private StackExchangeArffDataSaver(){
		
	}
	
	public static StackExchangeArffDataSaver getInstance(){
		if(instance==null){
			instance = new StackExchangeArffDataSaver();
		}
		return instance;
	}

	/**
	 * Reads questions from .json files and converts them to java objects.
	 * Creates Instance objects for all the questions and adds values of the
	 * attributes. Adds each instance to the Instances dataset. Saves Instances
	 * to an .arff file.
	 */
	public void saveToArffFile() {

		List<Question> closedQuestions = getQuestionsFeaturesFromFile("data/closedQuestionsWithFeatures.json");
		List<Question> notClosedQuestions = getQuestionsFeaturesFromFile("data/notClosedQuestionsWithFeatures.json");

		FastVector attributes = new FastVector();

		// feature A1
		Attribute ageOfAccountAttr = new Attribute("age_of_account");
		// feature A2
		Attribute badgeScoreAttr = new Attribute("badge_score");
		// feature A3
		Attribute postsWithNegScoreAttr = new Attribute("posts_with_neg_score");
		// feature B1
		Attribute postScoreAttr = new Attribute("post_score");
		// feature B2
		Attribute acceptedAnswerScoreAttr = new Attribute(
				"accepted_answer_score");
		// feature B3
		Attribute commentScoreAttr = new Attribute("comment_score");
		// feature C1
		Attribute numOfURLsAttr = new Attribute("num_of_URLs");
		// feature C2
		Attribute numOfStackOverflowURLsAttr = new Attribute(
				"num_of_stackOverflow_URLs");
		// feature D1
		Attribute titleLengthAttr = new Attribute("title_length");
		// feature D2
		Attribute bodyLengthAttr = new Attribute("body_length");
		// feature D3
		Attribute numOfTagsAttr = new Attribute("num_of_tags");
		// feature D4
		Attribute numOfPunctuationMarksAttr = new Attribute(
				"num_of_punctuation_marks");
		// feature D5
		Attribute numOfShortWordsAttr = new Attribute("num_of_short_words");
		// feature D6
		Attribute numOfSpecialCharactersAttr = new Attribute(
				"num_of_special_characters");
		// feature D7
		Attribute numOfLowerCaseCharactersAttr = new Attribute(
				"num_of_lower_case_characters");
		// feature D8
		Attribute numOfUpperCaseCharactersAttr = new Attribute(
				"num_of_upper_case_characters");
		// feature D10
		Attribute codeSnippetLengthAttr = new Attribute("code_snippet_length");
		// class attribute
		FastVector classAttributeVector = new FastVector(2);
		classAttributeVector.addElement("closed");
		classAttributeVector.addElement("not_closed");
		Attribute classAttribute = new Attribute("class", classAttributeVector);

		attributes.addElement(ageOfAccountAttr);
		attributes.addElement(badgeScoreAttr);
		attributes.addElement(postsWithNegScoreAttr);
		attributes.addElement(postScoreAttr);
		attributes.addElement(acceptedAnswerScoreAttr);
		attributes.addElement(commentScoreAttr);
		attributes.addElement(numOfURLsAttr);
		attributes.addElement(numOfStackOverflowURLsAttr);
		attributes.addElement(titleLengthAttr);
		attributes.addElement(bodyLengthAttr);
		attributes.addElement(numOfTagsAttr);
		attributes.addElement(numOfPunctuationMarksAttr);
		attributes.addElement(numOfShortWordsAttr);
		attributes.addElement(numOfSpecialCharactersAttr);
		attributes.addElement(numOfLowerCaseCharactersAttr);
		attributes.addElement(numOfUpperCaseCharactersAttr);
		attributes.addElement(codeSnippetLengthAttr);
		attributes.addElement(classAttribute);

		Instances data = new Instances("questions", attributes, 1000);
		data.setClassIndex(data.numAttributes() - 1);

		for (int i = 0; i < closedQuestions.size(); i++) {

			Question q = closedQuestions.get(i);

			Instance qInstance = new Instance(data.numAttributes());

			qInstance.setValue(ageOfAccountAttr, q.getOwner()
					.getAge_of_account());
			qInstance.setValue(badgeScoreAttr, q.getOwner().getBadge_score());
			qInstance.setValue(acceptedAnswerScoreAttr, q.getOwner()
					.getAccepted_answer_score());
			qInstance.setValue(commentScoreAttr, q.getOwner()
					.getComment_score());
			qInstance.setValue(postsWithNegScoreAttr, q.getOwner()
					.getPosts_with_negative_scores());
			qInstance.setValue(postScoreAttr, q.getOwner().getPost_score());
			qInstance.setValue(numOfURLsAttr, q.getNum_of_URLs());
			qInstance.setValue(numOfStackOverflowURLsAttr,
					q.getNum_of_stackOwerflow_URLs());
			qInstance.setValue(titleLengthAttr, q.getTitle_length());
			qInstance.setValue(bodyLengthAttr, q.getBody_length());
			qInstance.setValue(numOfTagsAttr, q.getNum_of_tags());
			qInstance.setValue(numOfPunctuationMarksAttr,
					q.getNum_of_punctuation_marks());
			qInstance.setValue(numOfShortWordsAttr, q.getNum_of_short_words());
			qInstance.setValue(numOfSpecialCharactersAttr,
					q.getNum_of_special_characters());
			qInstance.setValue(numOfLowerCaseCharactersAttr,
					q.getNum_of_lower_case_characters());
			qInstance.setValue(numOfUpperCaseCharactersAttr,
					q.getNum_of_upper_case_characters());
			qInstance.setValue(codeSnippetLengthAttr,
					q.getCode_snippet_length());
			qInstance.setValue(classAttribute, "closed");
			qInstance.setDataset(data);

			data.add(qInstance);
		}

		for (int i = 0; i < notClosedQuestions.size(); i++) {

			Question q = notClosedQuestions.get(i);

			Instance qInstance = new Instance(data.numAttributes());

			qInstance.setValue(ageOfAccountAttr, q.getOwner()
					.getAge_of_account());
			qInstance.setValue(badgeScoreAttr, q.getOwner().getBadge_score());
			qInstance.setValue(acceptedAnswerScoreAttr, q.getOwner()
					.getAccepted_answer_score());
			qInstance.setValue(commentScoreAttr, q.getOwner()
					.getComment_score());
			qInstance.setValue(postsWithNegScoreAttr, q.getOwner()
					.getPosts_with_negative_scores());
			qInstance.setValue(postScoreAttr, q.getOwner().getPost_score());
			qInstance.setValue(numOfURLsAttr, q.getNum_of_URLs());
			qInstance.setValue(numOfStackOverflowURLsAttr,
					q.getNum_of_stackOwerflow_URLs());
			qInstance.setValue(titleLengthAttr, q.getTitle_length());
			qInstance.setValue(bodyLengthAttr, q.getBody_length());
			qInstance.setValue(numOfTagsAttr, q.getNum_of_tags());
			qInstance.setValue(numOfPunctuationMarksAttr,
					q.getNum_of_punctuation_marks());
			qInstance.setValue(numOfShortWordsAttr, q.getNum_of_short_words());
			qInstance.setValue(numOfSpecialCharactersAttr,
					q.getNum_of_special_characters());
			qInstance.setValue(numOfLowerCaseCharactersAttr,
					q.getNum_of_lower_case_characters());
			qInstance.setValue(numOfUpperCaseCharactersAttr,
					q.getNum_of_upper_case_characters());
			qInstance.setValue(codeSnippetLengthAttr,
					q.getCode_snippet_length());
			qInstance.setValue(classAttribute, "not_closed");
			qInstance.setDataset(data);

			data.add(qInstance);
		}
		ArffSaver saver = new ArffSaver();
		saver.setInstances(data);
		try {
			saver.setFile(new File("data/dataSet.arff"));
			saver.writeBatch();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Data saved to dataSet.arff");
	}
	
	/**
	 * Gets questions with features from .json file and transforms them to java
	 * objects.
	 * 
	 * @param fileName
	 *            name of the file to read from
	 * @return list of Question objects with features
	 */
	public List<Question> getQuestionsFeaturesFromFile(String fileName) {
		List<Question> questions = new ArrayList<Question>();

		try {
			FileReader reader = new FileReader(fileName);

			Gson gson = new GsonBuilder().disableHtmlEscaping()
					.setPrettyPrinting().create();

			JsonObject jsonResult = gson.fromJson(reader, JsonObject.class);

			JsonArray questionsArray = (JsonArray) jsonResult.get("items");

			for (int i = 0; i < questionsArray.size(); i++) {
				questions.add(StackExchangeJsonParser
						.parseQuestionFeatures((JsonObject) questionsArray
								.get(i)));

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return questions;
	}

}
