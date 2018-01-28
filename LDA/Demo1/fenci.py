# -*- coding: utf-8 -*-

import jieba
jieba.suggest_freq('沙瑞金', True)
jieba.suggest_freq('易学习', True)
jieba.suggest_freq('王大路', True)
jieba.suggest_freq('京州', True)
#第一个文档分词#
with open('./nlp_test0.txt') as f:
    document = f.read()
    
    document_decode = document.decode('GBK')
    document_cut = jieba.cut(document_decode)
    #print  ' '.join(jieba_cut)
    result = ' '.join(document_cut)
    result = result.encode('utf-8')
    with open('./nlp_test1.txt', 'w') as f2:
        f2.write(result)
f.close()
f2.close()  

#第二个文档分词#
with open('./nlp_test2.txt') as f:
    document2 = f.read()
    
    document2_decode = document2.decode('GBK')
    document2_cut = jieba.cut(document2_decode)
    #print  ' '.join(jieba_cut)
    result = ' '.join(document2_cut)
    result = result.encode('utf-8')
    with open('./nlp_test3.txt', 'w') as f2:
        f2.write(result)
f.close()
f2.close() 

#第三个文档分词#
jieba.suggest_freq('桓温', True)
with open('./nlp_test4.txt') as f:
    document3 = f.read()
    
    document3_decode = document3.decode('GBK')
    document3_cut = jieba.cut(document3_decode)
    #print  ' '.join(jieba_cut)
    result = ' '.join(document3_cut)
    result = result.encode('utf-8')
    with open('./nlp_test5.txt', 'w') as f3:
        f3.write(result)
f.close()
f3.close()