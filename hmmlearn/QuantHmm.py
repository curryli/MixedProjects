# -*- coding: utf-8 -*-
#比如我们的股市，如果只是观测市场，我们只能知道当天的价格、成交量等信息，但是并不知道当前股市处于什么样的状态（牛市、熊市、震荡、反弹等等），在这种情况下我们有两个状态集合，一个可以观察到的状态集合（股市价格成交量状态等）和一个隐藏的状态集合（股市状况）。我们希望能找到一个算法可以根据股市价格成交量状况和马尔科夫假设来预测股市的状况。
#GaussianHMM和GMMHMM是连续观测状态的HMM模型，而MultinomialHMM是离散观测状态的模型  我们股票数据用GaussianHMM

from hmmlearn import hmm
from hmmlearn.hmm import GaussianHMM
import numpy as np
from matplotlib import cm, pyplot as plt
import matplotlib.dates as dates
import pandas as pd
import datetime

n = 6 #6个隐藏状态

#测试时间从2005年1月1日到2015年12月31日，拿到每日沪深300的各种交易数据。
data = pd.read_csv("data/Hmm_data", sep=" ", index_col = 'date')
print(data.columns)

#取出每日成交量和收盘价的数据
volume = data['volume']
close = data['close']

#计算每日最高最低价格的对数差值，作为特征状态的一个指标
logDel = np.log(np.array(data['high'])) - np.log(np.array(data['low']))

#计算每5日的指数对数收益差，作为特征状态的一个指标。
logRet_1 = np.array(np.diff(np.log(close)))#这个作为后面计算收益使用
logRet_5 = np.log(np.array(close[5:])) - np.log(np.array(close[:-5]))

#计算每5日的指数成交量的对数差，作为特征状态的一个指标。
logVol_5 = np.log(np.array(volume[5:])) - np.log(np.array(volume[:-5]))

#由于计算中出现了以5天为单位的计算，所以要调整特征指标的长度。 (前4天没有这些指标)
logDel = logDel[5:]
logRet_1 = logRet_1[4:]
close = close[5:]
Date = pd.to_datetime(data.index[5:])

#把我们的特征状态合并在一起。
A = np.column_stack([logDel,logRet_5,logVol_5])
print A.shape

#下面运用 hmmlearn 这个包中的 GaussianHMM 进行预测。
#模型参数学习问题：给定观测序列，求最可能的参数   A就是输入的观测序列，
model = GaussianHMM(n_components= n, covariance_type="full", n_iter=2000)

#为了稳定，多算几次，求概率最大的
model.fit(A)
best_startprob_ = model.startprob_
best_transmat = model.transmat_
best_means_ = model.means_
best_covars = model.covars_

max_prop = -999
for i in range(5):
    model.fit(A)
    temp_prop = model.score(A)
    if(temp_prop>max_prop):
        max_prop=temp_prop
        best_startprob_ = model.startprob_
        best_transmat = model.transmat_
        best_means_ = model.means_
        best_covars = model.covars_

model.startprob_ = best_startprob_
model.transmat_ = best_transmat
model.means_ = best_means_
model.covars_ = best_covars


#已知模型参数，根据观测序列，解码隐藏状态序列
hidden_states = model.predict(A)
print hidden_states

#我们把每个预测的状态用不同颜色标注在指数曲线上看一下结果。从图中可以比较明显的看出绿色的隐藏状态代表指数大幅上涨，浅蓝色和黄色的隐藏状态代表指数下跌。
plt.figure(figsize=(25, 18))
for i in range(model.n_components):   #n_components应该是隐藏状态的数组
    pos = (hidden_states==i)
    plt.plot_date(Date[pos],close[pos],'o',label='hidden state %d'%i,lw=2)
    plt.legend(loc="left")
plt.show()

#
# #为了更直观的表现不同的隐藏状态分别对应了什么，我们采取获得隐藏状态结果后第二天进行买入的操作，这样可以看出每种隐藏状态代表了什么。
# res = pd.DataFrame({'Date':Date,'logRet_1':logRet_1,'state':hidden_states}).set_index('Date')
# plt.figure(figsize=(25, 18))
# for i in range(model.n_components):
#     pos = (hidden_states==i)
#     pos = np.append(0,pos[:-1])#第二天进行买入操作
#     df = res.logRet_1
#     res['state_ret%s'%i] = df.multiply(pos)
#     plt.plot_date(Date,np.exp(res['state_ret%s'%i].cumsum()),'-',label='hidden state %d'%i)
#     plt.legend(loc="left")
# plt.show()
#
#
# #可以看到，隐藏状态1是一个明显的大牛市阶段，隐藏状态0是一个缓慢上涨的阶段(可能对应反弹)，隐藏状态3和5可以分别对应震荡下跌的大幅下跌。其他的两个隐藏状态并不是很明确。由于股指期货可以做空，我们可以进行如下操作：当处于状态0和1时第二天做多，当处于状态3和5第二天做空，其余状态则不持有。
# #不稳定，HMM求参数的时候建议多算几次求概率最大值
# long = (hidden_states==0) + (hidden_states == 1) #做多
# short = (hidden_states==3) + (hidden_states == 5)  #做空
# long = np.append(0,long[:-1]) #第二天才能操作
# short = np.append(0,short[:-1]) #第二天才能操作
#
# #收益曲线图如下：
# res['ret'] =  df.multiply(long) - df.multiply(short)
# plt.plot_date(Date,np.exp(res['ret'].cumsum()),'r-')
# plt.show()