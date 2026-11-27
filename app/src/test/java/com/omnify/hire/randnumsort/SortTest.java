package com.omnify.hire.randnumsort;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SortTest {
    private static ArrayList<Integer> list(Integer... values) {
        return new ArrayList<>(Arrays.asList(values));
    }

    private static void assertBothSort(List<Integer> input) {
        ArrayList<Integer> expected = new ArrayList<>(input);
        Collections.sort(expected);

        ArrayList<Integer> merged = new ArrayList<>(input);
        MergeSort.sort(merged);
        assertEquals("merge sort of " + input, expected, merged);

        ArrayList<Integer> quick = new ArrayList<>(input);
        new QuickSort().sort(quick);
        assertEquals("quick sort of " + input, expected, quick);
    }

    @Test
    public void emptyAndSingleElementLists() {
        assertBothSort(list());
        assertBothSort(list(42));
    }

    @Test
    public void smallLists() {
        assertBothSort(list(2, 1));
        assertBothSort(list(3, 1, 2));
        assertBothSort(list(5, 4, 3, 2, 1));
        assertBothSort(list(1, 2, 3, 4, 5));
    }

    @Test
    public void duplicates() {
        assertBothSort(list(7, 7, 7, 7));
        assertBothSort(list(3, 1, 3, 2, 1, 2, 3));
    }

    @Test
    public void randomListsLikeTheApp() {
        Random random = new Random(2017);
        for (int round = 0; round < 200; round++) {
            ArrayList<Integer> input = new ArrayList<>();
            for (int i = 0; i < 40; i++) {
                input.add(10 + random.nextInt(90));
            }
            assertBothSort(input);
        }
    }

    @Test
    public void randomListsOfVaryingLength() {
        Random random = new Random(1);
        for (int size = 0; size < 100; size++) {
            ArrayList<Integer> input = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                input.add(random.nextInt(1000) - 500);
            }
            assertBothSort(input);
        }
    }
}
