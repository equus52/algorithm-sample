package equus.independent.util;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class AlgorithmUtilsTest {

  @Test
  public void test_cumsumNew() {
    int[] a = { 1, 0, -1, 1, 0, 0 };
    int[] expected = { 1, 1, 0, 1, 1, 1 };

    int[] sum = AlgorithmUtils.cumsumNew(a);

    System.out.println(toString(sum));
    assertThat(sum, is(expected));
  }

  @Test
  public void test_cumsum() {
    int[] a = { 1, 0, -1, 1, 0, 0 };
    int[] expected = { 1, 1, 0, 1, 1, 1 };

    AlgorithmUtils.cumsum(a);

    System.out.println(toString(a));
    assertThat(a, is(expected));
  }

  @Test
  public void test_cumsumNew_2() {
    int[][] a = { { 1, 0, -1, 1, 0, 0 }, //
        { 1, 0, -1, 1, 0, 0 }, //
        { 1, 0, -1, 1, 0, 0 }, //
    };

    int[][] expected = { { 1, 1, 0, 1, 1, 1 }, //
        { 2, 2, 0, 2, 2, 2 }, //
        { 3, 3, 0, 3, 3, 3 }, //
    };

    int[][] sum = AlgorithmUtils.cumsumNew(a);

    System.out.println(toString(sum));
    assertThat(sum, is(expected));
  }

  @Test
  public void test_cumsum_2() {
    int[][] a = { { 1, 0, -1, 1, 0, 0 }, //
        { 1, 0, -1, 1, 0, 0 }, //
        { 1, 0, -1, 1, 0, 0 }, //
    };

    int[][] expected = { { 1, 1, 0, 1, 1, 1 }, //
        { 2, 2, 0, 2, 2, 2 }, //
        { 3, 3, 0, 3, 3, 3 }, //
    };

    AlgorithmUtils.cumsum(a);

    System.out.println(toString(a));
    assertThat(a, is(expected));
  }

  private String toString(int[][] a) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < a.length; i++) {
      builder.append(toString(a[i]));
      builder.append(System.lineSeparator());
    }
    return builder.toString();
  }

  private String toString(int[] a) {
    StringBuilder builder = new StringBuilder();
    builder.append("[");
    if (a.length > 0) {
      builder.append(a[0]);
    }
    for (int i = 1; i < a.length; i++) {
      builder.append(", ");
      builder.append(a[i]);
    }
    builder.append("]");
    return builder.toString();
  }
}
