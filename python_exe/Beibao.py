# -*- coding: utf-8 -*-
#https://www.jianshu.com/p/25f4a183ede5
import numpy as np


def solve(vlist,wlist,totalWeight,totalLength):
    resArr = np.zeros((totalLength+1,totalWeight+1),dtype=np.int32)
    for i in range(1,totalLength+1):
        for j in range(1,totalWeight+1):
            if wlist[i] <= j:  # resArr[i,j] 是从 resArr[i-1,...]演化而来，这分两种情况，那么这个最优解要么包含有i这个物品，要么不包含，肯定是这两种情况中的一种。如果我们选择了第i个物品，那么实际上这个最优解是c[i - 1, w-wi] + vi。而如果我们没有选择第i个物品，这个最优解是c[i-1, w]。
                resArr[i,j] = max(resArr[i-1,j-wlist[i]]+vlist[i],resArr[i-1,j])
            else:
                resArr[i,j] = resArr[i-1,j]
    return resArr[-1,-1]




if __name__ == '__main__':
    v = [0,60,100,120]
    w = [0,10,20,30]
    weight = 50  # 重量限制
    n = 3  #个数
    result = solve(v,w,weight,n)
    print(result)

