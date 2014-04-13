package equus.independent.problem;

public class LongestCommonSubsequence {

    public static int solve(char[] str1, char[] str2) {
        int length1 = str1.length;
        int length2 = str2.length;
        int[][] dp = new int[length1 + 1][length2 + 1];
        for (int i1 = 0; i1 < length1; i1++) {
            for (int i2 = 0; i2 < length2; i2++) {
                if (str1[i1] == str2[i2]) {
                    dp[i1 + 1][i2 + 1] = dp[i1][i2] + 1;
                } else {
                    dp[i1 + 1][i2 + 1] = max(dp[i1][i2 + 1], dp[i1 + 1][i2]);
                }
            }
        }
        return dp[length1][length2];
    }

    private static int max(int i, int j) {
        return i > j ? i : j;
    }

}
