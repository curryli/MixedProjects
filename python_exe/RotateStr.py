# -*- coding: utf-8 -*-
#常见的翻转法  仔细观察规律可知翻转的分割点在从数组末尾数起的offset位置。先翻转前半部分，随后翻转后半部分，最后整体翻转。
def rotateString(A, offset):
    if A is None or len(A) == 0:
        return A

    offset %= len(A)
    before = A[:len(A) - offset]
    after = A[len(A) - offset:]
    # [::-1] means reverse in Python
    A = before[::-1] + after[::-1]
    A = A[::-1]

    return A

Str = "123456"
print rotateString(Str, 2)




s = " tian zhai  xing  "
print "Before s:",s
def reverseWords(s):
    L = s.split() #单词拆分成列表
    #print "字符串以单词分割成列表: %s" % L
    #L.reverse()#反转列表中单词  list 有reverse   str 没有reverse   但都可以用[::-1]   所以统一用[::-1] 最好
    L = L[::-1] # 反转列表中单词
    #print "反转列表中的单词: %s" % L
    s1 = ' '.join(L)#列表以空格分割单词，返回字符串
    #print "以空格分隔列表中反转后的单词: %s" % s1
    return s1
    #return s1.rstrip()#字符串删除首尾空格和回车
L = reverseWords(s)
print "Afer L  :",L

