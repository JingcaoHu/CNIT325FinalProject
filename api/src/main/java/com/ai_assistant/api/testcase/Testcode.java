package com.ai_assistant.api.testcase;

import java.util.ArrayList;
import java.util.List;

public class Testcode {
    public int quickSelect(List<Integer> numList, int k){
        if (numList.size() == 1){
            return numList.get(0);
        }
        List<Integer> big = new ArrayList<Integer>();
        List<Integer> equal = new ArrayList<Integer>();
        List<Integer> small = new ArrayList<Integer>();
        int pivot = numList.get(0);

        for (int num : numList){
            if (num > pivot){
                big.add(num);
            }
            else if (num == pivot){
                equal.add(num);
            }
            else {
                small.add(num);
            }
        }

        if (k <= big.size()) {
            return quickSelect(big, k);
        } else if (k == big.size() + equal.size()) {
            return pivot;
        } else {
            return quickSelect(small, k - big.size() - equal.size());
        }
    }
    
    
    
    
    public int findKthLargest(int[] nums, int k) {
        List<Integer> numList = new ArrayList<Integer>();
        for (int num : nums){
            numList.add(num);
        }
        return quickSelect(numList, k);
    }

    public static void main(String[] args) {
        int[] input = {99,99};
        int k = 1;
        Testcode test = new Testcode();
        int result = test.findKthLargest(input, k);
        System.out.println(result);
    }
}

