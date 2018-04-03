# -*- coding: utf-8 -*-
#反转list
class ListNode:
    def __init__(self, x):
        self.val = x
        self.next = None


def reverse(head):
    prev = None
    while head:
        temp = head.next
        head.next = prev
        prev = head
        head = temp
    return prev


head = ListNode(1)  # 测试代码
p1 = ListNode(2)  # 建立链表1->2->3->4->None
p2 = ListNode(3)
p3 = ListNode(4)
head.next = p1
p1.next = p2
p2.next = p3
p =  reverse(head)  # 输出链表 4->3->2->1->None
while p:
    print p.val
    p = p.next



queue = []  # same as list()
size = len(queue)
queue.append(1)
queue.append(2)
print queue.pop() # return 1
#print queue[0] # return 2 examine the first element

V =5
g = [[0 for _ in range(V)] for _ in range(V)]
print g