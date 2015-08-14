# StackExchangePredicativeModel

## About the project
====================

The aim of this project was to build a prediction model that would be able to predict whether the question posted on [stackoverflow.com] (http://stackoverflow.com/) was going to be closed or not.

Stackoverflow is is a website where users can ask questions on various topics in computer programming, answer other users’ questions, and earn points and badges by actively participating in the community. To prevent low quality questions stackoverflow introduced a closing questions mechanism in 2013, which allows experience community members to mark a question closed if they estimate it not to be fit for the website. A question can be marked closed for five reasons: 
* *duplicate*, 
* *off-topic*,
*  *unclear-what-you’re-asking*, 
* *too broad* and 
* *primarily-opinion-based*.

The project workflow consisted of the following steps:
  1. Collecting the data relevant for the project using [StackExchangeAPI] (https://api.stackexchange.com/)
  2. Processing collected data and adding features for classification
  3. Creating the dataset
  4. Applying machine learning techniques for classification
  5.  Evaluating the classification results

1. Collecting data
==========

500 closed questions and 500 not closed questions were collected for the purposes of the project through the StackExchangeAPI. The questions were collected using the [/search]( https://api.stackexchange.com/docs/advanced-search) method with the following parmeters:
* *fromDate*: 1404172800 (1/7/2014)
* *toDate*: 1419984000 (31/12/2014)
* *closed*: true for closed questions, false for not closed questions
* *filter*: withBody, in order to get bodies of the questions
* *accessToken* and *key* obtained by registering to the api, in order to increase the daily request quota.

The results were saved to files closedQuestions.json and notClosedQuestions.json.

2. Adding features
============ 
In the next step, each question was added features for classification. The features can be divided into four groups:

| Group  | Name | Features |
| ------------- | ------------- |  ------------- |
| **A**  | User Profile  | *age_of_account*, *badge_score*, *posts_with_negative_score*  |
| **B**  | Community Process| *post_score*, *accepted_answer_score*, *comment_score*|
| **C**  | Question Content  | *number_of_urls*, *number_of_stackoverflow_urls*|
| **D** | Textual Style| *title_length*, *body_length*, *number_of_tags*, *number_of_punctuation_marks*, *number_of_short_words*, *number_of_special_characters*, *number_of_lower_case_characters*, *number_of_upper_case_characters*, *code_snippet_length*|

Features of group **A** are related to user’s profile and participation activities in the community, whereas features of group **B** are based on contributions to the community in the form of votes, answers, etc.  Group **C** contains features related to question content, and group **D** features describe the textual style of the question title and body. Most of the features are self-describing, although some of them require further explanation:

* **Badge score**

 Let {b1 , … , bn} be the badges earned by the user. Then:

  ![equation](http://www.sciweavers.org/tex2img.php?eq=badgeScore%20%3D%20%5Csum_%7Bi%3D1%7D%5E%7Bn%7D%20%20%20%5Cfrac%7B1%7D%7BnumOfUsersWhoHave%20b_i%7D%20%20&bc=White&fc=Black&im=jpg&fs=12&ff=arev&edit=0)

* **Post score**

  Let {q1 , … , qn} be the set of questions asked by the user, and {a1 , … , am} the set of answers posted by the user. Then:

  ![equation](http://www.sciweavers.org/tex2img.php?eq=postScore%20%3D%20%20%5Csum_%7Bi%3D1%7D%5E%7Bn%7D%20score%28q_i%29%20%20%2B%20%20%5Csum_%7Bj%3D1%7D%5E%7Bm%7D%20score%28a_j%29%20%20%20&bc=White&fc=Black&im=jpg&fs=12&ff=arev&edit=0)

* **Comment score**

  Let {c1 , … , cn} be the comments posted by the user. Then:

  ![equation](http://www.sciweavers.org/tex2img.php?eq=%20commentScore%3D%20%5Csum_%7Bi%3D1%7D%5E%7Bn%7D%20%20score%28c_i%29&bc=White&fc=Black&im=jpg&fs=12&ff=arev&edit=0)

* **Accepted answer score**

  Let {a1 , … , am} be the set of answers posted by the user which have been accepted. Each acepted answer gains the score of 15, therefore:

  ![equation](http://www.sciweavers.org/tex2img.php?eq=acceptedAnswerScore%20%3D%20%5Csum_%7Bi%3D1%7D%5E%7Bn%7D%2015&bc=White&fc=Black&im=jpg&fs=12&ff=arev&edit=0)

The following api methods were used to collect the necessary data:

* [/users/{ids}] (http://api.stackexchange.com/docs/users-by-ids) – the method which returns data about user with the requested id
* [/users/{ids}/badges](http://api.stackexchange.com/docs/badges-on-users) – returns the badges owned by the user with the requested id
* [/badges/{ids}](http://api.stackexchange.com/docs/badges-by-ids) – returns data about badge with the requested id
* [/users/{ids}/questions](http://api.stackexchange.com/docs/questions-on-users) – returns the questions that the requested user posted
* [/users/{ids}/answers](http://api.stackexchange.com/docs/answers-on-users) – returns the answers that the requested user posted
* [/users/{ids}/comments](http://api.stackexchange.com/docs/comments-on-users) – returns the comments that the requested user posted

After adding the features, the questions were saved to files closedQuestionsWithFeatures.json and notClosedQuestionsWithFeatures.json.


3. Creating the dataset
=====================

The next step included creating the dataset from collected questions. The dataset contains 18 attributes: 17 are numeric (the features), and the 18th is the class attribute with possible values *closed* or *not_closed*, the one whose value the program should be able to predict. The dataset was saved to file dataSet.arff, and later divided into two datasets – one for training, with 80% of data (trainingSet.arff), and the other for testing, with 20% of  data (testSet.arff).


4. Applying machine learning techniques for classification
=======================

The dataset was first loaded from the .arff file, and since it contained numeric attributes it needed to be discretized. This was done using the weka Discretize filter. After that the FilteredClassifier was build with Discretize filter and the classifier. Three classifiers were used for classification:

* NaiveBayes
* Support Vector Machines
* Logistic Regression

5. Evaluation of the results
====================
All the clasiffiers were evaluated first on training dataset and later on the test dataset. Their results were as follows:

**Naive Bayes**

| DataSet | Correctly classified instances % | Precision | Recall | F1 |
| --- | ----------- | ------------- | ----------- | ---- |
| Training  | 82.875  | 0.829  | 0.829  | 0.829  |
| Test| 77 | 0.771  | 0.77 |0.77 | 0.77 |

Confusion matrix:

| a | b  | <-- classified as |
| --- | --- | --------- |
|74 | 26 | a (closed) |
| 20 | 80 | b (not_closed) |

**Support Vector Machines**

| DataSet | Correctly classified instances % | Precision | Recall | F1 |
| --- | ----------- | ------------- | ----------- | ---- |
| Training  | 96.875  | 0.969  | 0.969  | 0.969  |
| Test| 86.5 | 0.869  | 0.865 |0.865 | 0.865 |

Confusion matrix:

| a | b  | <-- classified as |
| --- | --- | ------------ |
|81 | 19 |  a (closed) |
|8 | 92 |  b (not_closed) |

**Logistic Regression**

| DataSet | Correctly classified instances % | Precision | Recall | F1 |
| --- | ----------- | ------------- | ----------- | ---- |
| Training  | 100 | 1  | 1  | 1  |
| Test| 82.5 | 0.825  | 0.825 |0.825 | 0.825 |

Confusion matrix:

| a | b  | <-- classified as |
| --- | --- | ------------ |
|82 | 18 |  a (closed) |
|17 | 83 |  b (not_closed) |

As one can notice, the Logistic Regression classifier had the best results on training data, with 100% correctly classified instances. On the test dataset, Support Vector Machines was the best with 86.5 % corectly classified instances. 

6. Technical realisation
========================


The application was written in Java programming language, using Eclipse Juno IDE. The following libraries were used:

  * [gson-2.2.4.jar](https://github.com/google/gson) – Java library used to convert Java objects into their JSON representation, and vice versa.
  * [weka-3.7.3.jar](http://sourceforge.net/projects/weka/files/weka-3-7-windows-x64/) - Java library with a collection of machine learning algorithms included in Weka used to create a predictive classification model
  * [httpclient-4.5] (https://hc.apache.org/downloads.cgi)
 
7. Acknowledgements 
===================


The project has been developed as a part of the project assignment for the course [Intelligent Systems](http://is.fon.rs) at the [Faculty of Organization Sciences](http://fon.rs), University of Belgrade, Serbia.
Ideas and guidelines for the project were found in the work [Fit or Unfit : Analysis and Prediction of ‘Closed Questions’] (http://arxiv.org/abs/1307.7291).


