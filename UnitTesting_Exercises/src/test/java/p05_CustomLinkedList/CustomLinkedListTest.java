package p05_CustomLinkedList;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomLinkedListTest {
    private CustomLinkedList<Integer> list;

    @Before
    public void setUp() {
        this.list = new CustomLinkedList<>();
        for (int i = 0; i < 10; i++) {
            this.list.add(i);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetShouldThrowWithIndexLessThanZero() {
        list.get(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetShouldThrowWithIndexGreaterOrEqualToElementCount() {
        list.get(10);
    }

    @Test
    public void testGetRetrievesCorrectValueFromGivenIndex() {
        int item = list.get(5);

        assertEquals(5, item);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetShouldThrowWithIndexLessThanZero() {
        list.set(-1, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetShouldThrowWithIndexGreaterOrEqualToElementCount() {
        list.set(10, 10);
    }

    @Test
    public void testSetSetsTheGivenValueAtTheSpecifiedIndex() {
        for (int i = 0; i < 10; i++) {
            list.set(i, i * i);
        }

        for (int i = 0; i < 10; i++) {
            int item = list.get(i);
            assertEquals(i * i, item);
        }
    }

    @Test
    public void testAddIfListIsEmptyShouldAddAtTheBeginning() {
        CustomLinkedList<Integer> list = new CustomLinkedList<>();
        list.add(15);
        assertEquals(15, (int) list.get(0));
    }

    @Test
    public void testAddForANonEmptyList() {
        list.add(10);
        assertEquals(10, (int) list.get(10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveAtShouldThrowWithIndexLessThanZero() {
        list.removeAt(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveAtShouldThrowWithIndexGreaterOrEqualToElementCount() {
        list.removeAt(10);
    }

    @Test
    public void testRemoveAtRemovesElementAtGivenIndex() {
        list.removeAt(3);

        assertEquals(Integer.valueOf(4), list.get(3));
    }

    @Test
    public void testRemoveAtReturnsCorrectValue() {
        Integer integer = list.removeAt(3);
        assertEquals(3, (int) integer);
    }

    @Test
    public void testRemoveReturnsNegativeOneWhenItemNotPresentInTheList() {
        int removed = list.remove(34);

        assertEquals(-1, removed);
    }

    @Test
    public void testRemoveRemovesGivenElement() {
        list.remove(8);

        assertEquals(9, (int) list.get(8));
    }

    @Test
    public void testRemoveReturnsCorrectIndexOfRemovedElement(){
        int removed = list.remove(8);

        assertEquals(8, removed);
    }

    @Test
    public void testIndexOfReturnsNegativeOneWhenItemNotPresentInTheList(){
        int index = list.indexOf(10);

        assertEquals(-1, index);
    }

    @Test
    public void testIndexOfReturnsCorrectIndexOfGivenItemInTheList(){
        int index = list.indexOf(5);

        assertEquals(5, index);
    }

    @Test
    public void testContainsReturnsFalseWhenItemNotPresent(){
        boolean present = list.contains(10);

        assertFalse(present);
    }

    @Test
    public void testContainsReturnsTrueWhenItemPresent(){
        boolean present = list.contains(9);

        assertTrue(present);
    }

}