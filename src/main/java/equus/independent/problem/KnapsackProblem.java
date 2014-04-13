package equus.independent.problem;

public class KnapsackProblem {

    public static int solve(int maxWeight, int[] prices, int[] weights) {
        int itemNumber = prices.length;
        int[][] dp = new int[itemNumber + 1][maxWeight + 1];
        // dp[i][w] i番目までのitemからweightの総和がw以下となるように選んだ時のprice合計の最大値

        for (int weightSum = 1; weightSum <= maxWeight; weightSum++) {
            dp[1][weightSum] = weightSum < weights[0] ? 0 : prices[0];
        }
        for (int includingItem = 2; includingItem <= itemNumber; includingItem++) {
            for (int weightSum = 1; weightSum <= maxWeight; weightSum++) {
                if (weightSum < weights[includingItem - 1]) {
                    dp[includingItem][weightSum] = dp[includingItem - 1][weightSum];
                } else {
                    dp[includingItem][weightSum] = Math.max(dp[includingItem - 1][weightSum],
                                    dp[includingItem - 1][weightSum - weights[includingItem - 1]]
                                                    + prices[includingItem - 1]);
                }
            }
        }
        return dp[itemNumber][maxWeight];
    }
}
