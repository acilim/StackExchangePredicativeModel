package main;

import classifiers.QuestionClassifier;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//get features for closed questions - done and written to closedQuestionsWithFeatures.json
		//StackExchangeQuestionProcessor.getInstance().processClosedQuestions();
		
		//get features for not closed questions - done and written to notClosedQuestionsWithFeatures.json
		//StackExchangeQuestionProcessor.getInstance().processNotClosedQuestions();

		//create Instances and save to .arff file - done and written to dataSet.arff 
		//StackExchangeArffDataSaver.getInstance().saveToArffFile();
		
		//build and evaluate classifiers
		QuestionClassifier qClassifier = new QuestionClassifier();
		try {
			qClassifier.buildAndEvaluateNaiveBayesClassifier();
			qClassifier.buildAndEvaluateSupportVectorMachinesClassifier();
			qClassifier.buildAndEvaluateLogisticRegressionClassifier();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	

}
