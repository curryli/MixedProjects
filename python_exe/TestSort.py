# -*- coding: utf-8 -*-
def bubbleSort(alist):
    for i in xrange(len(alist)):
        for j in xrange(1, len(alist) - i):
            if alist[j - 1] > alist[j]:
                alist[j - 1], alist[j] = alist[j], alist[j - 1]

    return alist

def selectionSort(alist):
    for i in xrange(len(alist)):
        min_index = i
        for j in xrange(i + 1, len(alist)):
            if alist[j] < alist[min_index]:
                min_index = j
        alist[min_index], alist[i] = alist[i], alist[min_index]
    return alist


#########################################quick_sort 不稳定#############################################
def qsort3(alist, lower, upper):
    if lower >= upper:
        return

    left, right = lower + 1, upper
    pivot = alist[lower]

    while left <= right:
        while left <= right and alist[left] < pivot:
            left += 1
        while left <= right and alist[right] >= pivot:
            right -= 1
        if left > right:
            break
        # swap while left <= right
        alist[left], alist[right] = alist[right], alist[left]
    # swap the smaller with pivot
    alist[lower], alist[right] = alist[right], alist[lower]

    qsort3(alist, lower, right - 1)
    qsort3(alist, right + 1, upper)

#############################################归并排序 （稳定）############################################

class Sort:
    def mergeSort(self, alist):
        if len(alist) <= 1:
            return alist

        mid = len(alist) / 2
        left = self.mergeSort(alist[:mid])
        right = self.mergeSort(alist[mid:])
        return self.mergeSortedArray(left, right)

    #@param A and B: sorted integer array A and B.
    #@return: A new sorted integer array
    def mergeSortedArray(self, A, B):
        sortedArray = []
        l = 0
        r = 0
        while l < len(A) and r < len(B):
            if A[l] < B[r]:
                sortedArray.append(A[l])
                l += 1
            else:
                sortedArray.append(B[r])
                r += 1
        sortedArray += A[l:]
        sortedArray += B[r:]

        return sortedArray



unsorted_list = [6, 5, 3, 1, 8, 7, 2, 4]
print(bubbleSort(unsorted_list))



unsorted_list = [8, 5, 2, 6, 9, 3, 1, 4, 0, 7]
print(selectionSort(unsorted_list))




unsortedArray = [6, 5, 3, 1, 8, 7, 2, 4]
qsort3(unsortedArray, 0, len(unsortedArray) - 1)
print unsortedArray

unsortedArray = [6, 5, 3, 1, 8, 7, 2, 4]
ms = Sort()
print(ms.mergeSort(unsortedArray))