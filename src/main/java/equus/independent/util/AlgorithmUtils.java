package equus.independent.util;

public final class AlgorithmUtils {
  private AlgorithmUtils() {}

  public static int[] cumsumNew(int[] a) {
    int[] sum = new int[a.length];
    cumsum(a, sum);
    return sum;
  }

  public static void cumsum(int[] a) {
    cumsum(a, a);
  }

  private static void cumsum(int[] a, int[] sum) {
    sum[0] = a[0];
    int x = a.length;
    for (int i = 1; i < x; i++) {
      sum[i] = sum[i - 1] + a[i];
    }
  }

  public static int[][] cumsumNew(int[][] a) {
    if (a.length == 0) {
      return new int[0][0];
    }
    int[][] sum = new int[a.length][a[0].length];
    cumsum(a, sum);
    return sum;
  }

  public static void cumsum(int[][] a) {
    cumsum(a, a);
  }

  private static void cumsum(int[][] a, int[][] sum) {
    if (a.length == 0) {
      return;
    }
    int x = a.length;
    int y = a[0].length;
    for (int xi = 0; xi < x; xi++) {
      sum[xi][0] = a[xi][0];
      for (int yi = 1; yi < y; yi++) {
        sum[xi][yi] = sum[xi][yi - 1] + a[xi][yi];
      }
    }
    for (int yi = 0; yi < y; yi++) {
      for (int xi = 1; xi < x; xi++) {
        sum[xi][yi] = sum[xi - 1][yi] + sum[xi][yi];
      }
    }
  }
}
