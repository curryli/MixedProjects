# -*- coding: utf-8 -*-

#http://blog.csdn.net/TiffanyRabbit/article/details/76445909
#LDA模型学习时的训练数据并不是一篇篇文本，而是Document-word matrix，它可以是array也可以是稀疏矩阵，维数是n_samples*n_features，其中n_features为词(term)的个数。
#n_sampes 的每篇文章(或者每个句子（如果是句子主题分析的话）)是一个sample， matrix 每一行分析该sample中对应的n_features个词对应的词频
#加载数据
from sklearn.datasets import fetch_20newsgroups
dataset = fetch_20newsgroups(shuffle=True, random_state=1,
                             remove=('headers', 'footers', 'quotes'))
data_samples = dataset.data[:2000] #截取需要的量，n_samples=2000

#文本预处理, 可选项
import nltk
import string
from nltk.corpus import stopwords
from nltk.stem.porter import PorterStemmer
def textPrecessing(text):
    #小写化
    text = text.lower()
    #去除特殊标点
    for c in string.punctuation:
        text = text.replace(c, ' ')
    #分词
    wordLst = nltk.word_tokenize(text)
    #去除停用词
    filtered = [w for w in wordLst if w not in stopwords.words('english')]
    #仅保留名词或特定POS   
    refiltered =nltk.pos_tag(filtered)
    filtered = [w for w, pos in refiltered if pos.startswith('NN')]
    #词干化
    ps = PorterStemmer()
    filtered = [ps.stem(w) for w in filtered]

    return " ".join(filtered)


textPre_FilePath = "textPre.txt"
tf_ModelPath = "tf_Model"
n_features=2500

#该区域仅首次运行，进行文本预处理，第二次运行起注释掉
docLst = []
for desc in data_samples :
    docLst.append(textPrecessing(desc).encode('utf-8'))
with open(textPre_FilePath, 'w') as f:
    for line in docLst:
        f.write(line+'\n')

#==============================================================================
#从第二次运行起，直接获取预处理过的docLst，前面load数据、预处理均注释掉
#docLst = []
#with open(textPre_FilePath, 'r') as f:
#    for line in f.readlines():
#        if line != '':
#            docLst.append(line.strip())
#==============================================================================


from sklearn.feature_extraction.text import CountVectorizer
from sklearn.externals import joblib  #也可以选择pickle等保存模型，请随意

#构建词汇统计向量并保存，仅运行首次
tf_vectorizer = CountVectorizer(max_df=0.95, min_df=2,
                                max_features=n_features,
                                stop_words='english')
tf = tf_vectorizer.fit_transform(docLst)
joblib.dump(tf_vectorizer,tf_ModelPath )
#==============================================================================
# #得到存储的tf_vectorizer,节省预处理时间
tf_vectorizer = joblib.load(tf_ModelPath)
tf = tf_vectorizer.fit_transform(docLst)
#==============================================================================

from sklearn.decomposition import LatentDirichletAllocation
n_topics = 30
lda = LatentDirichletAllocation(n_topics=n_topics, 
                                max_iter=50,
                                learning_method='batch')
lda.fit(tf) #tf即为Document_word Sparse Matrix         

       
       
def print_top_words(model, feature_names, n_top_words):
    #打印每个主题下权重较高的term
    for topic_idx, topic in enumerate(model.components_):
        print "Topic #%d:" % topic_idx
        print " ".join([feature_names[i]
                        for i in topic.argsort()[:-n_top_words - 1:-1]])
    print
    #打印主题-词语分布矩阵
    print model.components_

n_top_words=20
tf_feature_names = tf_vectorizer.get_feature_names()
print_top_words(lda, tf_feature_names, n_top_words)



doc_topic_dist = lda.transform(tf)