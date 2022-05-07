import csv
import random

from sklearn import svm
from sklearn.linear_model import Perceptron
from sklearn.linear_model import LogisticRegression
from sklearn.naive_bayes import GaussianNB
from sklearn.neighbors import KNeighborsClassifier
from sklearn.tree import DecisionTreeClassifier

# Initialize the algorithm to train

# Logistic Regression (with weith tuning)
model = LogisticRegression(class_weight ={
    "true" : .6,
    "false" : 1
})
# Configuration for the PMML export: multi_class can be "multinomial" or "ovr"
model.multi_class = "ovr"

# In order to test other algorithms commenting the previous lines and uncommenting one of the following

# model = KNeighborsClassifier(n_neighbors=3)
# model = svm.SVC()
# model = GaussianNB()
# model = DecisionTreeClassifier()
# model = Perceptron()

# Conversion utility to map the csv values in numbers for the ML algorithms 
def convert(str):
    if str.isdecimal():
        return int(str)
    elif str == "optional":
        return 0
    elif str == "basic":
        return 1
    elif str == "low":
        return 0
    elif str == "medium":
        return 1
    elif str == "high":
        return 2
    else:
        return str

# Read data in from file
with open("python/application-approval.csv") as f:
    reader = csv.reader(f)
    next(reader)

    data = []
    for row in reader:
        data.append({
            "evidence": [convert(cell) for cell in row[:4]],
            "label": row[4]
        })

# Separate data into training and testing groups
random.shuffle(data)
holdout = int(0.40 * len(data))
testing = data[:holdout]
training = data[holdout:]

# Train model on training set
X_training = [row["evidence"] for row in training]
y_training = [row["label"] for row in training]

model.fit(X_training, y_training)

# Make predictions on the testing set
X_testing = [row["evidence"] for row in testing]
y_testing = [row["label"] for row in testing]
predictions = model.predict(X_testing)

# Compute how well we performed
correct = 0
incorrect = 0
total = 0
truePositive = 0
trueNegative = 0
falsePositive = 0
falseNegative = 0

for actual, predicted in zip(y_testing, predictions):
    total += 1
    if actual == predicted:
        correct += 1
        if predicted == "true":
            truePositive += 1
        else:
            trueNegative += 1
    else:
        incorrect += 1
        if predicted == "true":
            falsePositive += 1
        else:
            falseNegative += 1

sensitivity = truePositive / (truePositive + falseNegative)
specificity = trueNegative / (trueNegative + falsePositive)

# Print results
print(f"Results for model {type(model).__name__}")
print(f"Correct: {correct}")
print(f"Incorrect: {incorrect}")
print(f"Accuracy: {100 * correct / total:.2f}%")

print(f"True Positive Rate: {100 * sensitivity:.2f}%")
print(f"True Negative Rate: {100 * specificity:.2f}%")


from sklearn2pmml import sklearn2pmml
from sklearn2pmml import make_pmml_pipeline

# Export the trained model in PMML

pipeline = make_pmml_pipeline(
    model,
    active_fields= ["category", "urgency", "targetPrice", "price"],
    target_fields= ["approval"]
)
sklearn2pmml(pipeline, "application-approval.pmml")