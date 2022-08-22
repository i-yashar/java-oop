package p01_Database;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;

public class DatabaseTest {

    private Database database;
    private static final Integer[] EXPECTED = {13, 42, 64, 73};

    @Before
    public void setUp() throws OperationNotSupportedException {
        this.database = new Database(EXPECTED);
    }

    @Test
    public void testDatabaseConstructorSetsElementsCorrectly(){
        Assert.assertArrayEquals(EXPECTED, database.getElements());
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testDatabaseConstructorShouldThrowWithMoreThanSixteenElementsAsAnArgument() throws OperationNotSupportedException {
        new Database(new Integer[17]);
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testDatabaseConstructorShouldThrowWithZeroElementsAsAnArgument() throws OperationNotSupportedException {
        new Database();
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testAddShouldThrowWhenCalledWithNull() throws OperationNotSupportedException {
        database.add(null);
    }

    @Test
    public void testAddShouldAddGivenElementAtFirstEmptyCell() throws OperationNotSupportedException {
        Integer[] expectedArray = new Integer[]{13, 42, 64, 73, 12};

        database.add(12);

        Assert.assertArrayEquals(expectedArray, database.getElements());
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testRemoveShouldThrowForAnEmptyDatabase() throws OperationNotSupportedException {
        Database database = new Database(13);
        database.remove();
        database.remove();
    }

    @Test
    public void testRemoveShouldRemoveTheLastElement() throws OperationNotSupportedException {
        Integer[] expected = new Integer[]{13, 42, 64};
        database.remove();
        Assert.assertArrayEquals(expected, database.getElements());
    }

    @Test
    public void testGetElementsShouldReturnCorrectArray(){
        Integer[] result = database.getElements();
        Assert.assertEquals(EXPECTED.length, result.length);

        for (int i = 0; i < result.length; i++) {
            Assert.assertEquals(EXPECTED[i], result[i]);
        }
    }

}