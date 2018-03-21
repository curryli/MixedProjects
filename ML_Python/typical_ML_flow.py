# -*-coding:utf-8-*-

#CHAS：CHAS虚拟变量，用于回归分析。

import numpy as np
from numpy import arange
from matplotlib import pyplot
from pandas import read_csv
from pandas import  set_option
#from pandas.plotting import scatter_matrix
from sklearn.preprocessing import StandardScaler
from sklearn.model_selection import train_test_split
from sklearn.model_selection import KFold
from sklearn.model_selection import cross_val_score
from sklearn.model_selection import GridSearchCV
from sklearn.linear_model import LinearRegression
from sklearn.linear_model import Lasso
from sklearn.linear_model import ElasticNet
from sklearn.tree import DecisionTreeRegressor
from sklearn.neighbors import KNeighborsRegressor
from sklearn.svm import SVR
from sklearn.pipeline import Pipeline
from sklearn.ensemble import RandomForestRegressor
from sklearn.ensemble import GradientBoostingRegressor
from sklearn.ensemble import ExtraTreesRegressor
from sklearn.ensemble import AdaBoostRegressor
from sklearn.metrics import mean_squared_error
import pandas as pd
from sklearn.cross_validation import train_test_split
from sklearn.metrics import recall_score, precision_score

# 导入随机森林算法库
from sklearn.ensemble import RandomForestClassifier
from sklearn.linear_model import LogisticRegression
from sklearn.grid_search import GridSearchCV
from sklearn.grid_search import RandomizedSearchCV
from sklearn.metrics import confusion_matrix
from sklearn.utils import shuffle
from sklearn.ensemble import GradientBoostingClassifier
import datetime
# from sklearn.model_selection import train_test_split
# from sklearn.model_selection import GridSearchCV
from sklearn.metrics import classification_report
from sklearn import metrics


from sklearn.metrics import confusion_matrix

import matplotlib.pyplot as plt

labels = [1, 2, 3, 4]

def plot_confusion_matrix(cm, title='Confusion Matrix', cmap = plt.cm.Blues):
    plt.imshow(cm, interpolation='nearest', cmap=cmap)
    plt.title(title)
    plt.colorbar()
    xlocations = np.array(range(len(labels)))
    plt.xticks(xlocations, labels, rotation=90)
    plt.yticks(xlocations, labels)
    plt.ylabel('True label')
    plt.xlabel('Predicted label')
    plt.show()


filename = r'../data/car_data.csv'

df_All = read_csv(filename,  sep=',', low_memory=False)
df_All = df_All.fillna(-1)
df_All = shuffle(df_All)


df_X = df_All[['buying','maint','doors','persons','lug_boot']]

df_X = pd.get_dummies(df_X)

df_y =  pd.get_dummies(df_All['safety'])
print df_X.shape, df_y.shape

validation_size = 0.1
seed = 7
X_train, X_test, y_train, y_test = train_test_split(df_X, df_y,test_size=validation_size, random_state=seed)

# n_estimators树的数量一般大一点。 max_features 对于分类的话一般特征束的sqrt，auto自动
clf = RandomForestClassifier(n_estimators=100, max_depth=None, min_samples_split=2, max_features="auto",
                             max_leaf_nodes=None, bootstrap=True)

clf = clf.fit(X_train, y_train)

pred = clf.predict(X_test)
print metrics.f1_score(y_test, pred, average='weighted')

print classification_report(y_test, pred)

print confusion_matrix(y_test.values.argmax(axis=1), pred.argmax(axis=1))

plot_confusion_matrix(confusion_matrix(y_test.values.argmax(axis=1), pred.argmax(axis=1)), title='Normalized confusion matrix')


