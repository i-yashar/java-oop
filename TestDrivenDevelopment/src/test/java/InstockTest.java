import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class InstockTest {

    private ProductStock stock;
    private static final Product DEFAULT_PRODUCT = new Product("def_label", 2.99, 1);

    @Before
    public void setUp() {
        this.stock = new Instock();
    }

    @Test
    public void testAddShouldAddTheProductIfProductNotPresent() {
        stock.add(DEFAULT_PRODUCT);

        assertTrue(stock.contains(DEFAULT_PRODUCT));
    }

    @Test
    public void testContainsShouldReturnFalseWhenProductNotPresentAndTrueWhenAdded() {
        assertFalse(stock.contains(DEFAULT_PRODUCT));
        stock.add(DEFAULT_PRODUCT);
        assertTrue(stock.contains(DEFAULT_PRODUCT));
    }

    @Test
    public void testAddShouldNotAddProductThatHasAlreadyBeenAdded() {
        stock.add(DEFAULT_PRODUCT);
        stock.add(DEFAULT_PRODUCT);
        assertEquals(1, stock.getCount());
    }

    @Test
    public void testCountShouldReturnTheCorrectCountOfProducts() {
        addSomeProducts();
        assertEquals(10, stock.getCount());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testFindByIndexShouldThrowWhenIndexExceedsMaxIndex() {
        addSomeProducts();
        stock.find(10);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testFindByIndexShouldThrowWhenIndexIsNegative() {
        addSomeProducts();
        stock.find(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testFindByIndexShouldThrowWhenNoProductsArePresent() {
        stock.find(0);
    }

    @Test
    public void testFindByIndexShouldReturnTheCorrectProductWhenOnlyOneProductPresent() {
        stock.add(DEFAULT_PRODUCT);
        Product product = stock.find(0);

        assertNotNull(product);
        assertEquals(DEFAULT_PRODUCT.label, product.label);
    }

    @Test
    public void testFindByIndexShouldReturnTheCorrectProductWhenProductIsInTheMiddle() {
        addSomeProducts();
        Product product = stock.find(7);

        assertNotNull(product);
        assertEquals("test_label_7", product.label);
    }

    @Test
    public void testFindByIndexShouldReturnTheCorrectProductWhenProductIsLastOne() {
        addSomeProducts();
        Product product = stock.find(9);

        assertNotNull(product);
        assertEquals("test_label_9", product.label);
    }

    @Test
    public void testChangeQuantityShouldUpdateTheQuantityOfTheGivenProductByTheCorrectAmount() {
        addSomeProducts();
        stock.add(DEFAULT_PRODUCT);

        assertEquals(1, DEFAULT_PRODUCT.quantity);

        stock.changeQuantity(DEFAULT_PRODUCT.label, 9);

        assertEquals(9, DEFAULT_PRODUCT.quantity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChangeQuantityShouldThrowIfGivenProductNotInStock() {
        addSomeProducts();
        stock.changeQuantity(DEFAULT_PRODUCT.label, 5);
    }

    @Test
    public void testFindByLabelShouldFindTheCorrectProductWithTheGivenLabel() {
        addSomeProducts();
        Product byLabel = stock.findByLabel("test_label_3");
        assertNotNull(byLabel);
        assertEquals("test_label_3", byLabel.label);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindByLabelShouldThrowWhenNoProductWithTheGivenLabelIsInStock() {
        addSomeProducts();
        stock.findByLabel(DEFAULT_PRODUCT.label);
    }

    @Test
    public void testFindFirstByAlphabeticalOrderShouldReturnEmptyCollectionWhenStockIsEmpty() {
        Iterable<Product> iterable = stock.findFirstByAlphabeticalOrder(0);
        List<Product> listOfProducts = createListFromIterable(iterable);
        assertTrue(listOfProducts.isEmpty());
    }

    @Test
    public void testFindFirstByAlphabeticalOrderShouldReturnEmptyCollectionWhenParameterIsTooLarge() {
        addSomeProducts();
        Iterable<Product> iterable = stock.findFirstByAlphabeticalOrder(11);
        List<Product> listOfProducts = createListFromIterable(iterable);
        assertTrue(listOfProducts.isEmpty());
    }

    @Test
    public void testFirstByAlphabeticalOrderShouldReturnTheCorrectNumberOfProducts() {
        addSomeProducts();
        Iterable<Product> iterable = stock.findFirstByAlphabeticalOrder(10);

        List<Product> list = createListFromIterable(iterable);

        assertEquals(10, list.size());
    }

    @Test
    public void testFirstByAlphabeticalOrderShouldReturnTheObjectsSorted() {
        addSomeProducts();
        Iterable<Product> iterable = stock.findFirstByAlphabeticalOrder(10);
        List<Product> list = createListFromIterable(iterable);
        List<String> testList = list.stream().map(p -> p.label).sorted(String::compareTo).collect(Collectors.toList());

        int index = 0;
        for (String expected : testList) {
            assertEquals(expected, list.get(index++).label);
        }
    }

    @Test
    public void testFindAllInPriceRangeShouldReturnEmptyCollectionIfNoElementsArePresentWithinTheGivenRange() {
        addSomeProducts();
        Iterable<Product> iterable = stock.findAllInRange(18.99, 25);
        List<Product> products = createListFromIterable(iterable);

        assertTrue(products.isEmpty());
    }

    @Test
    public void testFindAllInPriceRangeShouldReturnAllElementsWithinTheGivenRangeInDescendingOrder() {
        List<Product> expected = addSomeProducts().stream()
                .filter(p -> p.getPrice() > 18.99 && p.getPrice() <= 25)
                .sorted(Comparator.comparingDouble(Product::getPrice).reversed())
                .collect(Collectors.toList());
        List<Product> products = createListFromIterable(stock.findAllInRange(18.99, 25));

        assertEquals(expected, products);
    }

    @Test
    public void testFindAllByPriceShouldReturnEmptyCollectionIfNoProductHasTheGivenPrice() {
        addSomeProducts();
        List<Product> products = createListFromIterable(stock.findAllByPrice(19.00));
        List<Product> products1 = createListFromIterable(stock.findAllByPrice(18.98));

        assertTrue(products.isEmpty());
        assertTrue(products1.isEmpty());
    }

    @Test
    public void testFindAllByPriceShouldReturnAllProductsWithTheGivenPrice() {
        List<Product> expected = addSomeProducts().stream()
                .filter(p -> p.getPrice() == 2.99)
                .collect(Collectors.toList());
        expected.add(DEFAULT_PRODUCT);
        stock.add(DEFAULT_PRODUCT);
        List<Product> products = createListFromIterable(stock.findAllByPrice(2.99));

        assertEquals(expected, products);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindFirstMostExpensiveProductsShouldThrowIfCountIsGreaterThanNumberOfAvailableProducts(){
        addSomeProducts();
        stock.findFirstMostExpensiveProducts(11);
    }

    @Test
    public void testFindFirstMostExpensiveProductsShouldReturnFirstCountNumberOfProductsWhichHaveTheHighestPrice(){
        List<Product> expected = addSomeProducts().stream()
                .sorted(Comparator.comparingDouble(Product::getPrice).reversed())
                .limit(5)
                .collect(Collectors.toList());
        List<Product> products = createListFromIterable(stock.findFirstMostExpensiveProducts(5));

        assertEquals(expected, products);
    }

    @Test
    public void testFindAllByQuantityShouldReturnEmptyCollectionIfNoProductWithTheGivenQuantityIsPresent(){
        addSomeProducts();
        List<Product> products = createListFromIterable(stock.findAllByQuantity(9));
        assertTrue(products.isEmpty());
    }

    @Test
    public void testFindAllByQuantityShouldReturnAllProductsWithTheGivenQuantity(){
        List<Product> expected = addSomeProducts().stream().filter(p -> p.getQuantity() == 7).collect(Collectors.toList());
        List<Product> products = createListFromIterable(stock.findAllByQuantity(7));

        assertEquals(expected, products);
    }

    @Test
    public void testIteratorShouldReturnAllItemsThatAreInStock(){
        List<Product> expected = addSomeProducts();
        Iterator<Product> productIterator = stock.iterator();

        assertNotNull(productIterator);

        int index = 0;
        while(productIterator.hasNext()){
            Product product = productIterator.next();
            assertEquals(expected.get(index++).getLabel(), product.getLabel());
        }
    }

    private List<Product> createListFromIterable(Iterable<Product> iterable) {
        assertNotNull(iterable);
        List<Product> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    private List<Product> addSomeProducts() {
        List<Product> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Product product = new Product(("test_label_" + i), 2 * i + 0.99, (i + 7) / 2);
            stock.add(product);
            list.add(product);
        }

        return list;
    }

}