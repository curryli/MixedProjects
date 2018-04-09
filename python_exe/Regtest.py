# -*- coding: utf-8 -*-
import re
# def countAndSay(seq):
#     """
# 1, 11, 21, 1211, 111221, ...
#
# 1 is read off as "one 1" or 11.
# 11 is read off as "two 1s" or 21.
# 21 is read off as "one 2, then one 1" or 1211.
#     """
# #re.sub  第二个参数：repl   可以是字符串，也可以是函数。
#     for item in seq:
#         item = re.sub(r'(.)\1*', lambda m: str(len(m.group(0))) + m.group(1), item)   #\1 表示第一次匹配到的字符串  *表示任意个贪婪匹配   group(0)就是匹配正则表达式整体结果，group(1)是匹配到的第一个
#         print item
#
# countAndSay(["1", "11", "21", "1211", "111221"])
#
#
#
# inputStr = "hello crifan, nihao crifan";
# replacedStr = re.sub(r"hello (\w+), nihao \1", "\g<1>", inputStr);
# print "replacedStr=",replacedStr; #crifan
#
# #对应的带命名的组（named group）的版本是：inputStr = "hello crifan, nihao crifan";
# replacedStr = re.sub(r"hello (?P<name>\w+), nihao (?P=name)", "\g<name>", inputStr);
# print "replacedStr=",replacedStr #crifan
#
print re.sub(r'(.)\1*', lambda m: str(len(m.group(0))) , "22211")


def _count(matched):
    print matched.group(0)

print re.sub(r'(.)\1*', _count, "22211")
