package equus.independent.data;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Test;

public class LinkGroupBuilderTest {

    @Test
    public void test_1() {
        LinkGroupBuilder<Integer> builder = new LinkGroupBuilder<>();
        builder.addLink(0, 1);
        builder.addLink(0, 2);
        builder.addLink(3, 4);
        builder.addLink(0, 4);
        Collection<Collection<Integer>> result = builder.build();
        assertEquals(1, result.size());
        assertEquals(5, result.iterator().next().size());
    }

}
