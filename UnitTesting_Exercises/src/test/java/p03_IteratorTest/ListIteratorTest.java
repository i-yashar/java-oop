package p03_IteratorTest;

import org.junit.Before;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;

import static org.junit.Assert.*;

public class ListIteratorTest {
    public static final String[] ELEMENTS = {"A", "B", "C", "D"};
    public ListIterator listIterator;

    @Before
    public void setUp() throws OperationNotSupportedException {
        this.listIterator = new ListIterator(ELEMENTS);
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testCreatingConstructorWithNullPassedAsArgumentShouldThrow() throws OperationNotSupportedException {
        new ListIterator(null);
    }

    @Test
    public void testMoveShouldReturnCorrectBoolean() {
        assertTrue(listIterator.move());
        assertTrue(listIterator.move());
        assertTrue(listIterator.move());
        assertFalse(listIterator.move());
    }

    @Test
    public void testHasNextShouldReturnCorrectBoolean() {
        assertTrue(listIterator.hasNext());
        listIterator.move();
        assertTrue(listIterator.hasNext());
        listIterator.move();
        assertTrue(listIterator.hasNext());
        listIterator.move();
        assertFalse(listIterator.hasNext());
    }

    @Test(expected = IllegalStateException.class)
    public void testPrintShouldThrowIfEmptyListIteratorIsPresent() throws OperationNotSupportedException {
        new ListIterator().print();
    }

    @Test
    public void testPrintShouldPrintTheCorrectElement() {
        for (int index = 0; listIterator.hasNext(); listIterator.move()) {
            assertEquals(ELEMENTS[index++], listIterator.print());
        }
    }

}