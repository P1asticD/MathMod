package mathmod.misc;

import java.util.ArrayList;
import java.util.List;

public class Calculate24 {

    private static final double TARGET = 24;
    private static final double EPSILON = 0.001;

    public static boolean judgePoint24(List<Integer> numbers) {
        List<Double> numList = new ArrayList<>();
        for (int num : numbers) {
            numList.add((double) num);
        }
        return backtrack(numList);
    }

    private static boolean backtrack(List<Double> nums) {
        if (nums.size() == 1) {
            return Math.abs(nums.get(0) - TARGET) < EPSILON;
        }

        for (int i = 0; i < nums.size(); i++) {
            for (int j = 0; j < nums.size(); j++) {
                if (i == j) continue;

                List<Double> nextNums = new ArrayList<>();
                for (int k = 0; k < nums.size(); k++) {
                    if (k != i && k != j) {
                        nextNums.add(nums.get(k));
                    }
                }

                for (int op = 0; op < 4; op++) {
                    if (op == 0) {
                        nextNums.add(nums.get(i) + nums.get(j));
                    } else if (op == 1) {
                        nextNums.add(nums.get(i) - nums.get(j));
                    } else if (op == 2) {
                        nextNums.add(nums.get(i) * nums.get(j));
                    } else if (op == 3) {
                        if (Math.abs(nums.get(j)) < EPSILON) {
                            continue;
                        }
                        nextNums.add(nums.get(i) / nums.get(j));
                    }

                    if (backtrack(nextNums)) {
                        return true;
                    }

                    nextNums.remove(nextNums.size() - 1);
                }
            }
        }
        return false;
    }
}
