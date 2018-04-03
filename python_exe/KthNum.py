# -*- coding: utf-8 -*-
def median(nums):
    if not nums:
        return -1
    return kth(nums, 0, len(nums) - 1, (1 + len(nums)) / 2)

#在数组长度确定后，我们可以直接套用 K 大数的模板来解，即 K 为 (length + 1) / 2.
def kth(nums, left, right, k):
    m = left
    for i in xrange(left + 1, right + 1):
        if nums[i] < nums[left]:
            m += 1
            nums[m], nums[i] = nums[i], nums[m]

    # swap between m and l after partition, important!
    nums[m], nums[left] = nums[left], nums[m]

    if m + 1 == k:
        return nums[m]
    elif m + 1 > k:
        return kth(nums, left, m - 1, k)
    else:
        return kth(nums, m + 1, right, k)


#由于调用一次快排后基准元素的最终位置是知道的，故递归的终止条件即为当基准元素的位置(索引)满足中位数的条件时
# (左半部分长度为原数组长度一半，无论奇偶均是如此)即返回最终结果。
arr = [6, 5, 3, 1, 8, 7, 2, 4]
print median(arr)