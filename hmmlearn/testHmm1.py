# -*- coding: utf-8 -*-
#pip install hmmlearn

import numpy as np
from hmmlearn import hmm


states = ["box 1", "box 2", "box3"]
n_states = len(states)

observations = ["red", "white"]
n_observations = len(observations)

start_probability = np.array([0.2, 0.4, 0.4])

transition_probability = np.array([
  [0.5, 0.2, 0.3],
  [0.3, 0.5, 0.2],
  [0.2, 0.3, 0.5]
])

emission_probability = np.array([
  [0.5, 0.5],
  [0.4, 0.6],
  [0.7, 0.3]
])

model = hmm.MultinomialHMM(n_components=n_states)
model.startprob_=start_probability
model.transmat_=transition_probability
model.emissionprob_=emission_probability



#解码问题： 给定模型参数和观测序列，求隐藏状态序列
  #方法1 decode
seen = np.array([[0,1,0]]).T
logprob, box = model.decode(seen, algorithm="viterbi")
print("The ball picked:", ", ".join(map(lambda x: observations[x], seen)))  #给定的观测序列:
print("The hidden box:", ", ".join(map(lambda x: states[x], box)))    #最可能的隐藏状态序列

  #方法2 predict
box2 = model.predict(seen)
print("The ball picked:", ", ".join(map(lambda x: observations[x], seen)))
print("The hidden box", ", ".join(map(lambda x: states[x], box2)))

#评估观察序列概率  要注意的是score函数返回的是以自然对数为底的对数概率值
print model.score(seen)



#模型参数学习问题：给定观测序列，求最可能的参数
model2 = hmm.MultinomialHMM(n_components=n_states, n_iter=20, tol=0.01)
X2 = np.array([[0,1,0,1],[0,0,0,1],[1,0,1,1]])   #这边给了3个观测序列组成的数组  我们跑三次，选择一个比较优的模型参数
model2.fit(X2)
print model2.startprob_
print model2.transmat_
print model2.emissionprob_
print model2.score(X2)

model2.fit(X2)
print model2.startprob_
print model2.transmat_
print model2.emissionprob_
print model2.score(X2)
model2.fit(X2)
print model2.startprob_
print model2.transmat_
print model2.emissionprob_
print model2.score(X2)

# 如果是GaussianHMM，则对应4个参数
# model.startprob_
# model.transmat_
# model.means_
# model.covars_