# -*- coding: utf-8 -*-
#http://www.runoob.com/python/python-reg-expressions.html
#https://blog.csdn.net/dnxbjyj/article/details/70946508
import re

############################re.match 尝试从字符串的起始位置匹配一个模式，如果不是起始位置匹配成功的话，match()就返回none。###########
line = "Cats are smarter than dogs"

matchObj = re.match(r'are (.*?) .*', line)

if matchObj:
    print "matchObj.group() : ", matchObj.group()
    print "matchObj.group(1) : ", matchObj.group(1)
else:
    print "No match!!"

#########################################re.search 扫描整个字符串并返回 第一个 成功的匹配。#######################
line = "Cats are smarter than dogs"

matchObj = re.search(r'are (.*?) .*', line)     #(.*?) .*注意  .*? 后面有一个空格  ，非贪婪匹配，表示下一个空格之前的所有字符,
#matchObj = re.search( r'dogs', line, re.M|re.I)
if matchObj:
    print "matchObj.group() : ", matchObj.group()
    print "matchObj.group(1) : ", matchObj.group(1)
else:
    print "No match!!"

###################################re.sub#################################
phone = "2004-959-559 # 这是一个国外电话号码"

# 删除字符串中的 Python注释
num = re.sub(r'#.*$', "", phone)
print "电话号码是: ", num

# 删除非数字(-)的字符串
num = re.sub(r'\D', "", phone)
print "电话号码是 : ", num


#repl 参数是一个函数
# 将匹配的数字乘以2
def DBFun(matched):
    value = int(matched.group('value'))
    return str(value * 2)


Str = 'A23G4HFD567'
print(re.sub('(?P<value>\d+)', DBFun, Str))   #?P<value>  将匹配到的\d+  命令为value组


def DB(matched):
    #print matched.groups()
    value = int(matched.group(1))   #只有一个括号，都属于第一个group 1
    return str(value * 2)


Str = 'A23G4HFD567'
print(re.sub('(\d+)', DB, Str))


###################################compile 函数用于编译正则表达式，生成一个正则表达式（ Pattern ）对象，供 match() 和 search() 等函数使用。
pattern = re.compile(r'([a-z]+) ([a-z]+)', re.I)   # re.I 表示忽略大小写
m = pattern.search('Hello, World Wide Web')
print m.group(1)
print m.span(1)
print m.group(2)
print m.span(2)


################################### match 和 search 是匹配一次， findall 匹配所有。
pattern = re.compile(r'\d+')  # 查找数字
result1 = pattern.findall('runoob 123 google 456')
result2 = pattern.findall('run88oob123google456', 0, 10)  #findall(string, pos, endpos)  #待匹配的起始字符  可选

print(result1)
print(result2)


########################################零宽断言

#?=  右边要匹配exp    ing
s = "I'm singing while you're dancing."
p = re.compile(r'\w+(?=ing)')
print re.findall(p,s)

#?<=do  左边要匹配exp  do
s = "doing done do todo"
p = re.compile(r'(?<=do)\w+')
print re.findall(p,s)

#?!  右边不匹配exp    ing   负向断言不支持匹配不定长的表达式，所以只能\w{2}  不能\w+
s = 'done run going'
p = re.compile(r'\b\w{2}(?!ing\b)')
print re.findall(p,s)