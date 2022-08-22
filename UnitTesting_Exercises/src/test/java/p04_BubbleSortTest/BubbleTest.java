package p04_BubbleSortTest;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class BubbleTest {

    @Test
    public void testBubbleSortResultCorrectness(){
        int[] arr = new int[]{9, 5, -6, 5, 0, 1, 13, 8, -18, 19, 3, 2};
        int[] test = new int[]{9, 5, -6, 5, 0, 1, 13, 8, -18, 19, 3, 2};
        Bubble.sort(arr);
        Arrays.sort(test);
        Assert.assertArrayEquals(test, arr);
    }
}