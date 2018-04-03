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

def reverseWords(s):
    return ' '.join(reversed(s.strip().split()))

Str = "123456"
print rotateString(Str, 2)


s = "the sky is blue"
print reverseWords(s)