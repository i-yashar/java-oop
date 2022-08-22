package p02_ExtendedDatabase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;

import static org.junit.Assert.*;

public class DatabaseTest {
    private Database database;
    private static final Person[] EXPECTED = {
            new Person(1, "First"),
            new Person(2, "Second"),
            new Person(3, "Third"),
            new Person(4, "Fourth")};

    @Before
    public void setUp() throws OperationNotSupportedException {
        this.database = new Database(EXPECTED);
    }

    @Test
    public void testDatabaseConstructorSetsElementsCorrectly() {
        Assert.assertArrayEquals(EXPECTED, database.getElements());
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testDatabaseConstructorShouldThrowWithMoreThanSixteenElementsAsAnArgument() throws OperationNotSupportedException {
        new Database(new Person[17]);
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
        Person[] expectedArray = new Person[]{
                new Person(1, "First"),
                new Person(2, "Second"),
                new Person(3, "Third"),
                new Person(4, "Fourth"),
                new Person(5, "Fifth")};

        database.add(new Person(5, "Fifth"));

        Assert.assertArrayEquals(expectedArray, database.getElements());
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testRemoveShouldThrowForAnEmptyDatabase() throws OperationNotSupportedException {
        Database database = new Database(new Person(3, "Third"));
        database.remove();
        database.remove();
    }

    @Test
    public void testRemoveShouldRemoveTheLastElement() throws OperationNotSupportedException {
        Person[] expectedArray = new Person[]{
                new Person(1, "First"),
                new Person(2, "Second"),
                new Person(3, "Third"),
                };
        database.remove();
        Assert.assertArrayEquals(expectedArray, database.getElements());
    }

    @Test
    public void testGetElementsShouldReturnCorrectArray() {
        Person[] result = database.getElements();
        Assert.assertEquals(EXPECTED.length, result.length);

        for (int i = 0; i < result.length; i++) {
            Assert.assertEquals(EXPECTED[i], result[i]);
        }
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testNoUserWithExistingIDCanBeAdded() throws OperationNotSupportedException {
        database.add(new Person(2, "Some_Random_Name"));
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testNoUserWithNegativeIDCanBeAdded() throws OperationNotSupportedException {
        database.add(new Person(-3, "Invalid_ID"));
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testNullUsernameShouldThrow() throws OperationNotSupportedException {
        database.findByUsername(null);
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testFindByUsernameShouldFailIfNoSuchUserExists() throws OperationNotSupportedException {
        database.findByUsername("randUsr");
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testFindByUsernameShouldFailWhenMultiplePeopleWithTheGivenUsernameExist() throws OperationNotSupportedException {
        Database database = new Database(new Person(1, "First"),
                new Person(2, "Second"),
                new Person(3, "Third"),
                new Person(4, "Fourth"),
                new Person(5, "Fourth"));

        database.findByUsername("Fourth");
    }

    @Test
    public void testFindByUsernameShouldReturnCorrectPerson() throws OperationNotSupportedException {
        Person person = database.findByUsername("Third");
        Assert.assertEquals(new Person(3, "Third"), person);
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testFindByIDShouldFailIfNoSuchUserExists() throws OperationNotSupportedException {
        database.findById(56);
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testFindByIDShouldFailWhenMultiplePeopleWithTheGivenIDExist() throws OperationNotSupportedException {
        Database database = new Database(new Person(1, "First"),
                new Person(2, "Second"),
                new Person(3, "Third"),
                new Person(5, "Fourth"),
                new Person(5, "Fifth"));

        database.findById(5);
    }

    @Test
    public void testFindByIDShouldReturnCorrectPerson() throws OperationNotSupportedException {
        Person person = database.findById(1);
        Assert.assertEquals(new Person(1, "First"), person);
    }


}