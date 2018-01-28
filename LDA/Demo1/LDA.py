# -*- coding: utf-8 -*-
#从文件导入停用词表
stpwrdpath = "stop_words.txt"
stpwrd_dic = open(stpwrdpath, 'rb')
stpwrd_content = stpwrd_dic.read()
#将停用词表转换为list  
stpwrdlst = stpwrd_content.splitlines()
stpwrd_dic.close()
#print stpwrdlst


from sklearn.feature_extraction.text import CountVectorizer
from sklearn.decomposition import LatentDirichletAllocation
with open('nlp_test1.txt') as f3:
    res1 = f3.read()
#print res1
with open('nlp_test3.txt') as f4:
    res2 = f4.read()
#print res2
with open('nlp_test5.txt') as f5:
    res3 = f5.read()
#print res3


corpus = [res1,res2,res3]
cntVector = CountVectorizer(stop_words=stpwrdlst) 
cntTf = cntVector.fit_transform(corpus)   #将词转化为TF  

#print cntVector.vocabulary_
print cntVector.get_feature_names()
#print cntTf  #稀疏矩阵
#print cntTf.toarray()
print cntTf.todense()

lda = LatentDirichletAllocation(n_topics=2,
                                learning_offset=50.,
                                random_state=0)
docres = lda.fit_transform(cntTf)

print docres
#print lda.components_