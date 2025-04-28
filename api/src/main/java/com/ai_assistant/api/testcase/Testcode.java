package com.ai_assistant.api.testcase;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

class Solution {
    public int[][] merge(int[][] intervals) {
        int length = intervals.length;
        if (length == 1){
            return intervals;
        }
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        Deque<Integer> leftStack = new LinkedList<>();
        Deque<Integer> rightStack = new LinkedList<>();
        leftStack.push(intervals[0][0]);
        rightStack.push(intervals[0][1]);
        int size = 1;
        for (int i = 1; i < length; i++){
            if (intervals[i][0] <= rightStack.peek()){
                int temp = rightStack.peek();
                if (intervals[i][1] > temp){ //New right is greater, replace old right
                    rightStack.pop();
                    rightStack.push(intervals[i][1]);
                }
            }
            else{
                leftStack.push(intervals[i][0]);
                rightStack.push(intervals[i][1]);
                size++;
            }   
        }

        int[][] result = new int[size][2];
        for (int i = 0; i < size; i++){
            result[i][0] = leftStack.pop();
            result[i][1] = rightStack.pop();
        }
        Arrays.sort(result, (a, b) -> Integer.compare(a[0], b[0]));
        return result;
    }
}