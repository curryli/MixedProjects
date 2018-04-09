# -*- coding: utf-8 -*-
#埃拉托斯特尼筛法:   给出要筛数值的范围n，找出\sqrt{n}以内的素数p_{1},p_{2},\dots,p_{k}。先用2去筛，即把2留下，把2的倍数剔除掉；再用下一个质数，也就是3筛，把3留下，把3的倍数剔除掉；接下去用下一个质数5筛，把5留下，把5的倍数剔除掉；不断重复下去.....
def findprime(L):
	i = 0
	count = 0
	while L[i] ** 2 < L[-1]:
		#print i, L[i]
		for j in range(i + 1, len(L)):
			if L[j] % L[i] == 0:
				L[j] = 0
				count += 1
		L.sort()
		L = L[count:]
		count = 0
		i += 1
	return L

L = range(2,10000)
findprime(L)
