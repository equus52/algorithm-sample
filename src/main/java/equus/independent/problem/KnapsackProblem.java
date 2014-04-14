package equus.independent.problem;

public class KnapsackProblem {

    public static int solve(int maxWeight, int[] weights, int[] prices) {
        int itemNumber = prices.length;
        int[][] dp = new int[itemNumber + 1][maxWeight + 1];
        // dp[i+1][w] i番目までのitemからweightの総和がw以下となるように選んだ時のprice合計の最大値

        for (int includingItem = 0; includingItem < itemNumber; includingItem++) {
            for (int weightSum = 0; weightSum <= maxWeight; weightSum++) {
                if (weightSum < weights[includingItem]) {
                    dp[includingItem + 1][weightSum] = dp[includingItem][weightSum];
                } else {
                    dp[includingItem + 1][weightSum] = Math.max(dp[includingItem][weightSum],
                                    dp[includingItem][weightSum - weights[includingItem]] + prices[includingItem]);
                }
            }
        }
        return dp[itemNumber][maxWeight];
    }

    public static int solveNoLimit(int maxWeight, int[] weights, int[] prices) {
        int itemNumber = prices.length;
        int[][] dp = new int[itemNumber + 1][maxWeight + 1];
        // dp[i+1][w] i番目までのitemからweightの総和がw以下となるように選んだ時のprice合計の最大値

        for (int includingItem = 0; includingItem < itemNumber; includingItem++) {
            for (int weightSum = 0; weightSum <= maxWeight; weightSum++) {
                if (weightSum < weights[includingItem]) {
                    dp[includingItem + 1][weightSum] = dp[includingItem][weightSum];
                } else {
                    dp[includingItem + 1][weightSum] = Math.max(dp[includingItem][weightSum],
                                    dp[includingItem + 1][weightSum - weights[includingItem]] + prices[includingItem]);
                }
            }
        }
        return dp[itemNumber][maxWeight];
    }
}
