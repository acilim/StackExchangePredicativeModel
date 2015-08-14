package classifiers;

import java.io.FileWriter;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.SMO;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.unsupervised.attribute.Discretize;

/**
 * The class used for data analysis; Loads data from an .arff file, discretizes
 * numerical data, builds the classifier and evaluates the classifier results on
 * both training and test data
 * 
 * @author Milica
 * 
 */
public class QuestionClassifier {

	private String trainingDataFileName = "data/trainingSet.arff";
	private String testDataFileName = "data/testSet.arff";

	private FilteredClassifier filteredClassifier;

	/**
	 * Loads the data from a given fileName and creates the dataset for
	 * classifying
	 * 
	 * @param fileName
	 *            name of the .arff file
	 * @return created Instances dataset
	 * @throws Exception
	 */
	public Instances loadDataSet(String fileName) throws Exception {

		DataSource loader = new DataSource(fileName);
		Instances loadedData = loader.getDataSet();
		loadedData.setClassIndex(loadedData.numAttributes() - 1);

		return loadedData;
	}

	/**
	 * Creates the Discretize filter that discretizes numeric data. The filter
	 * divides each numeric attribute into 8 bins, so that each bin contains
	 * approximately equal number of instances.
	 * 
	 * @param data
	 *            Data to discretize
	 * @return created Discretize filter
	 * @throws Exception
	 */
	private Discretize buildDiscretizeFilter(Instances data) throws Exception {

		Discretize discretizeFilter = new Discretize();
		discretizeFilter.setAttributeIndices("first-last");
		discretizeFilter.setInputFormat(data);
		discretizeFilter.setBins(8);
		discretizeFilter.setUseEqualFrequency(true);

		return discretizeFilter;
	}

	/**
	 * Loads the data from dataset and builds a FilteredClassifier using
	 * NaiveBayes as a classifier and Discretize as a filter.
	 * 
	 * @throws Exception
	 */
	public void buildAndEvaluateNaiveBayesClassifier() throws Exception {

		Instances data = loadDataSet(trainingDataFileName);

		Classifier nbClassifier = new NaiveBayes();

		filteredClassifier = new FilteredClassifier();
		filteredClassifier.setClassifier(nbClassifier);
		Discretize filter = buildDiscretizeFilter(data);
		filteredClassifier.setFilter(filter);

		filteredClassifier.buildClassifier(data);

		evaluateClassifier("NB");

	}

	/**
	 * Loads the data from dataset and builds a FilteredClassifier using SMO as
	 * a classifier and Discretize as a filter.
	 * 
	 * @throws Exception
	 */
	public void buildAndEvaluateSupportVectorMachinesClassifier()
			throws Exception {

		Instances data = loadDataSet(trainingDataFileName);

		Classifier smoClassifier = new SMO();

		filteredClassifier = new FilteredClassifier();
		filteredClassifier.setClassifier(smoClassifier);
		Discretize filter = buildDiscretizeFilter(data);
		filteredClassifier.setFilter(filter);

		filteredClassifier.buildClassifier(data);

		evaluateClassifier("SMO");
	}

	/**
	 * Loads the data from dataset and builds a FilteredClassifier using
	 * Logistic as a classifier and Discretize as a filter.
	 * 
	 * @throws Exception
	 */
	public void buildAndEvaluateLogisticRegressionClassifier() throws Exception {

		Instances data = loadDataSet(trainingDataFileName);

		Classifier logisticClassifier = new Logistic();

		filteredClassifier = new FilteredClassifier();
		filteredClassifier.setClassifier(logisticClassifier);
		Discretize filter = buildDiscretizeFilter(data);
		filteredClassifier.setFilter(filter);

		filteredClassifier.buildClassifier(data);

		evaluateClassifier("Logistic");
	}

	/**
	 * Loads the data and evaluates the classifier on both training and test
	 * dataset. Saves evaluation data to a text file.
	 * 
	 * @param name
	 *            Name of the classifier used for naming the text file
	 * @throws Exception
	 */
	private void evaluateClassifier(String name) throws Exception {

		Instances testData = loadDataSet(testDataFileName);
		Instances trainingData = loadDataSet(trainingDataFileName);

		System.out.println("Evaluating " + name
				+ " classifier on training data...");

		Evaluation eval = new Evaluation(trainingData);
		eval.evaluateModel(filteredClassifier, trainingData);

		String evalResults = "===TRAINING DATA==\n";
		evalResults += eval.toSummaryString() + "\n";
		evalResults += eval.toClassDetailsString() + "\n";
		evalResults += eval.toMatrixString() + "\n";

		System.out.println(eval.toSummaryString());
		System.out.println(eval.toClassDetailsString());
		System.out.println(eval.toMatrixString());

		System.out
				.println("Evaluating " + name + " classifier on test data...");

		eval = new Evaluation(testData);
		eval.evaluateModel(filteredClassifier, testData);

		evalResults += "\n===TEST DATA===\n";
		evalResults += eval.toSummaryString() + "\n";
		evalResults += eval.toClassDetailsString() + "\n";
		evalResults += eval.toMatrixString();

		System.out.println(eval.toSummaryString());
		System.out.println(eval.toClassDetailsString());
		System.out.println(eval.toMatrixString());

		FileWriter writer = new FileWriter("results/evaluationResults" + name
				+ ".txt");
		writer.write(evalResults);
		writer.close();

	}

}
