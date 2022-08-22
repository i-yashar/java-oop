import org.junit.Before;
import org.junit.Test;

import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ChainblockImplTest {
    private ChainblockImpl chainblock;
    private Transaction transaction;
    private List<Transaction> transactionList;

    @Before
    public void setUp(){
        this.chainblock = new ChainblockImpl();
        this.transaction = new TransactionImpl(1, TransactionStatus.SUCCESSFUL, "def_sender", "def_receiver", 56.98);
        createRandomTransactions();
    }

    private void createRandomTransactions() {
        transactionList = new ArrayList<>();
        TransactionStatus[] statuses = TransactionStatus.values();
        for (int i = 2; i < 22; i++) {
            String from = Character.getName(new Random().nextInt(58) + 67);
            Transaction t = new TransactionImpl(i, statuses[i % statuses.length], "FROM_" + from, "TO_RECEIVER" + i, new Random().nextInt(8 * i) + i / 33.0);
            transactionList.add(t);
        }
    }

    @Test
    public void testAddShouldAddTransactionToTheRecord(){
        chainblock.add(transaction);
        assertEquals(1, chainblock.getCount());
    }

    @Test
    public void testAddShouldNotAddATransactionThatHasAlreadyBeenAdded(){
        chainblock.add(transaction);
        chainblock.add(transaction);
        assertEquals(1, chainblock.getCount());
    }

    @Test
    public void testContainsShouldReturnTheCorrectBoolean(){
        assertFalse(chainblock.contains(transaction));
        chainblock.add(transaction);
        assertTrue(chainblock.contains(transaction));
    }

    @Test
    public void testChangeTransactionStatusShouldChangeTheStatusOfTheTransactionWithTheGivenID(){
        addTransactionsToTheChainBlock();
        chainblock.add(transaction);

        chainblock.changeTransactionStatus(transaction.getId(), TransactionStatus.ABORTED);
        assertEquals(TransactionStatus.ABORTED, transaction.getStatus());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChangeTransactionStatusShouldThrowIfNoTransactionWithTheGivenIDExists(){
        addTransactionsToTheChainBlock();

        chainblock.changeTransactionStatus(transaction.getId(), TransactionStatus.ABORTED);
    }

    @Test
    public void testRemoveTransactionByIDShouldRemoveTheCorrectTransactionWithTheGivenID(){
        addTransactionsToTheChainBlock();
        chainblock.add(transaction);

        chainblock.removeTransactionById(9);

        assertFalse(chainblock.contains(9));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveTransactionByIDShouldThrowIfNoTransactionWithTheGivenIDExists(){
        addTransactionsToTheChainBlock();

        chainblock.removeTransactionById(22);
    }

    @Test
    public void testGetByIDShouldReturnTheCorrectTransactionWithTheGivenID(){
        addTransactionsToTheChainBlock();

        Transaction transaction = chainblock.getById(13);

        assertNotNull(transaction);

        assertEquals(13, transaction.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByIDShouldThrowIfNoTransactionWithTheGivenIDExists(){
        addTransactionsToTheChainBlock();

        chainblock.getById(22);
    }

    @Test
    public void testGetByTransactionStatusShouldReturnAllTransactionsWithTheGivenStatus(){
        addTransactionsToTheChainBlock();

        List<Transaction> expected = transactionList.stream().filter(t -> t.getStatus().equals(TransactionStatus.FAILED)).collect(Collectors.toList());
        List<Transaction> actual = createListFromIterable(chainblock.getByTransactionStatus(TransactionStatus.FAILED));

        assertEquals(expected.size(), actual.size());

        for (Transaction t : actual) {
            assertEquals(TransactionStatus.FAILED, t.getStatus());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByTransactionStatusShouldThrowIfNoTransactionsWithTheGivenStatusExist(){
        transactionList.stream().filter(t -> !t.getStatus().equals(TransactionStatus.FAILED)).forEach(chainblock::add);
        chainblock.getByTransactionStatus(TransactionStatus.FAILED);
    }

    @Test
    public void testGetByTransactionStatusShouldReturnAllTransactionsWithTheGivenStatusInDescendingOrder(){
        addTransactionsToTheChainBlock();

        List<Transaction> expected = transactionList.stream()
                .filter(t -> t.getStatus().equals(TransactionStatus.FAILED))
                .sorted(Comparator.comparing(Transaction::getAmount).reversed())
                .collect(Collectors.toList());
        List<Transaction> actual = createListFromIterable(chainblock.getByTransactionStatus(TransactionStatus.FAILED));

        assertEquals(expected, actual);
    }

    @Test
    public void testGetSendersWithTransactionStatusShouldReturnCorrectSenders(){
        addTransactionsToTheChainBlock();

        List<String> expected = transactionList.stream()
                .filter(t -> t.getStatus().equals(TransactionStatus.SUCCESSFUL))
                .map(Transaction::getFrom).collect(Collectors.toList());

        Iterable<String> iterable = chainblock.getAllSendersWithTransactionStatus(TransactionStatus.SUCCESSFUL);

        assertNotNull(iterable);

        List<String> actual = new ArrayList<>();

        iterable.forEach(actual::add);

        assertEquals(expected.size(), actual.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSendersWithTransactionStatusShouldThrowIfNoTransactionWithTheGivenStatusExists(){
        transactionList.stream().filter(t -> !t.getStatus().equals(TransactionStatus.SUCCESSFUL)).forEach(chainblock::add);

        chainblock.getAllSendersWithTransactionStatus(TransactionStatus.SUCCESSFUL);
    }

    @Test
    public void testGetSendersWithTransactionStatusShouldReturnSendersInCorrectOrder(){
        addTransactionsToTheChainBlock();

        List<String> expected = transactionList.stream().filter(t -> t.getStatus().equals(TransactionStatus.UNAUTHORIZED))
                .sorted(Comparator.comparing(Transaction::getAmount).reversed())
                .map(Transaction::getFrom)
                .collect(Collectors.toList());

        Iterable<String> iterable = chainblock.getAllSendersWithTransactionStatus(TransactionStatus.UNAUTHORIZED);
        assertNotNull(iterable);

        List<String> actual = new ArrayList<>();

        iterable.forEach(actual::add);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetSendersWithTransactionStatusShouldReturnMultipleDuplicatedSendersInCorrectOrder(){
        addTransactionsToTheChainBlock();

        for (int i = 0; i < 10; i++) {
            Transaction t = new TransactionImpl(199 + i, TransactionStatus.UNAUTHORIZED, "DUPLICATED_SENDER", "tst", 10000.99 + i);
            chainblock.add(t);
            transactionList.add(t);
        }

        List<String> expected = transactionList.stream()
                .filter(t -> t.getStatus().equals(TransactionStatus.UNAUTHORIZED))
                .sorted(Comparator.comparing(Transaction::getAmount).reversed())
                .map(Transaction::getFrom)
                .collect(Collectors.toList());

        Iterable<String> iterable = chainblock.getAllSendersWithTransactionStatus(TransactionStatus.UNAUTHORIZED);
        assertNotNull(iterable);

        List<String> actual = new ArrayList<>();

        iterable.forEach(actual::add);

        assertEquals(expected, actual);

        for (int i = 0; i < 10; i++) {
            assertEquals("DUPLICATED_SENDER", actual.get(i));
        }

    }

    @Test
    public void testGetReceiversWithTransactionStatusShouldReturnCorrectReceivers(){
        addTransactionsToTheChainBlock();

        List<String> expected = transactionList.stream()
                .filter(t -> t.getStatus().equals(TransactionStatus.SUCCESSFUL))
                .map(Transaction::getTo).collect(Collectors.toList());

        Iterable<String> iterable = chainblock.getAllReceiversWithTransactionStatus(TransactionStatus.SUCCESSFUL);

        assertNotNull(iterable);

        List<String> actual = new ArrayList<>();

        iterable.forEach(actual::add);

        assertEquals(expected.size(), actual.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetReceiversWithTransactionStatusShouldThrowIfNoTransactionWithTheGivenStatusExists(){
        transactionList.stream().filter(t -> !t.getStatus().equals(TransactionStatus.SUCCESSFUL)).forEach(chainblock::add);

        chainblock.getAllReceiversWithTransactionStatus(TransactionStatus.SUCCESSFUL);
    }

    @Test
    public void testGetReceiversWithTransactionStatusShouldReturnReceiversInCorrectOrder(){
        addTransactionsToTheChainBlock();

        List<String> expected = transactionList.stream().filter(t -> t.getStatus().equals(TransactionStatus.UNAUTHORIZED))
                .sorted(Comparator.comparing(Transaction::getAmount).reversed())
                .map(Transaction::getTo)
                .collect(Collectors.toList());

        Iterable<String> iterable = chainblock.getAllReceiversWithTransactionStatus(TransactionStatus.UNAUTHORIZED);
        assertNotNull(iterable);

        List<String> actual = new ArrayList<>();

        iterable.forEach(actual::add);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetReceiversWithTransactionStatusShouldReturnMultipleDuplicatedReceiversInCorrectOrder(){
        addTransactionsToTheChainBlock();

        for (int i = 0; i < 10; i++) {
            Transaction t = new TransactionImpl(199 + i, TransactionStatus.UNAUTHORIZED, "tst", "DUPLICATED_RECEIVER", 10000.99 + i);
            chainblock.add(t);
            transactionList.add(t);
        }

        List<String> expected = transactionList.stream()
                .filter(t -> t.getStatus().equals(TransactionStatus.UNAUTHORIZED))
                .sorted(Comparator.comparing(Transaction::getAmount).reversed())
                .map(Transaction::getTo)
                .collect(Collectors.toList());

        Iterable<String> iterable = chainblock.getAllReceiversWithTransactionStatus(TransactionStatus.UNAUTHORIZED);
        assertNotNull(iterable);

        List<String> actual = new ArrayList<>();

        iterable.forEach(actual::add);

        assertEquals(expected, actual);

        for (int i = 0; i < 10; i++) {
            assertEquals("DUPLICATED_RECEIVER", actual.get(i));
        }

    }

    @Test
    public void testGetAllOrderedByAmountDescendingThenByIDShouldReturnTransactionsInCorrectOrder(){
        addTransactionsToTheChainBlock();

        Transaction t1 = new TransactionImpl(123, TransactionStatus.UNAUTHORIZED, "tst_sndr1", "tst_rcvr1", 10000.8);
        Transaction t2 = new TransactionImpl(101, TransactionStatus.UNAUTHORIZED, "tst_sndr2", "tst_rcvr2", 10000.9);
        Transaction t3 = new TransactionImpl(112, TransactionStatus.UNAUTHORIZED, "tst_sndr3", "tst_rcvr3", 10000.8);
        Transaction t4 = new TransactionImpl(144, TransactionStatus.UNAUTHORIZED, "tst_sndr4", "tst_rcvr4", 10000.9);

        chainblock.add(t1);
        chainblock.add(t2);
        chainblock.add(t3);
        chainblock.add(t4);
        transactionList.add(t1);
        transactionList.add(t2);
        transactionList.add(t3);
        transactionList.add(t4);

        List<Transaction> expected = transactionList.stream()
                .sorted(Comparator.comparing(Transaction::getAmount).reversed().thenComparing(Transaction::getId))
                .collect(Collectors.toList());

        Iterable<Transaction> iterable = chainblock.getAllOrderedByAmountDescendingThenById();
        assertNotNull(iterable);
        List<Transaction> actual = new ArrayList<>();
        iterable.forEach(actual::add);

        assertEquals(iterable, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBySenderOrderedByAmountDescendingShouldThrowIfNoSuchSenderFound(){
        addTransactionsToTheChainBlock();

        chainblock.getBySenderOrderedByAmountDescending("SENDER_NOT_FOUND");
    }

    @Test
    public void testGetBySenderOrderedByAmountDescendingShouldReturnCorrectTransactions(){
        addTransactionsToTheChainBlock();

        for (int i = 0; i < 10; i++) {
            Transaction t = new TransactionImpl(200 + i, TransactionStatus.ABORTED, "CORRECT_SENDER", "TST", 100000 + i * 10);
            chainblock.add(t);
        }

        Iterable<Transaction> iterable = chainblock.getBySenderOrderedByAmountDescending("CORRECT_SENDER");
        List<Transaction> actual = createListFromIterable(iterable);

        assertEquals(10, actual.size());

        for (Transaction t : actual) {
            assertEquals("CORRECT_SENDER", t.getFrom());
        }
    }

    @Test
    public void testGetBySenderOrderedByAmountDescendingShouldShouldReturnTransactionsInCorrectOrder(){
        addTransactionsToTheChainBlock();

        for (int i = 0; i < 10; i++) {
            Transaction t = new TransactionImpl(200 + i, TransactionStatus.ABORTED, "CORRECT_SENDER", "TST", 100000 + i * 10);
            chainblock.add(t);
        }

        Iterable<Transaction> iterable = chainblock.getBySenderOrderedByAmountDescending("CORRECT_SENDER");
        List<Transaction> actual = createListFromIterable(iterable);

        double amount = 100_090;

        for (Transaction t : actual) {
            assertEquals(amount, t.getAmount(), 0.00);
            amount -= 10;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByReceiverOrderedByAmountThenByIdShouldThrowIfNoSuchReceiverFound(){
        addTransactionsToTheChainBlock();

        chainblock.getByReceiverOrderedByAmountThenById("RECEIVER_NOT_FOUND");
    }

    @Test
    public void testGetByReceiverOrderedByAmountThenByIdShouldReturnCorrectTransactions(){
        addTransactionsToTheChainBlock();

        for (int i = 0; i < 10; i++) {
            Transaction t = new TransactionImpl(200 + i, TransactionStatus.ABORTED, "tst", "CORRECT_RECEIVER", 100000 + i * 10);
            chainblock.add(t);
        }

        Iterable<Transaction> iterable = chainblock.getByReceiverOrderedByAmountThenById("CORRECT_RECEIVER");
        List<Transaction> actual = createListFromIterable(iterable);

        assertEquals(10, actual.size());

        for (Transaction t : actual) {
            assertEquals("CORRECT_RECEIVER", t.getTo());
        }
    }

    @Test
    public void testGetByReceiverOrderedByAmountThenByIdShouldShouldReturnTransactionsInCorrectOrder(){
        addTransactionsToTheChainBlock();

        List<Transaction> expected = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Transaction t = new TransactionImpl(200 + i, TransactionStatus.ABORTED, "tst", "CORRECT_RECEIVER", 100000 + i * 10);
            chainblock.add(t);
            expected.add(t);
        }

        for (int i = 0; i < 10; i++) {
            Transaction t = new TransactionImpl(300 + i, TransactionStatus.ABORTED, "tst", "CORRECT_RECEIVER", 100000 + i * 10);
            chainblock.add(t);
            expected.add(t);
        }

        expected.sort(Comparator.comparing(Transaction::getAmount).reversed().thenComparing(Transaction::getId));

        Iterable<Transaction> iterable = chainblock.getByReceiverOrderedByAmountThenById("CORRECT_RECEIVER");
        List<Transaction> actual = createListFromIterable(iterable);

        assertEquals(expected, actual);
    }

    private void addTransactionsToTheChainBlock() {
        for (Transaction transaction : transactionList) {
            chainblock.add(transaction);
        }
    }

    @Test
    public void testGetByTransactionStatusAndMaximumAmountShouldReturnEmptyCollectionWhenNoSuchTransactionsExist(){
        addTransactionsToTheChainBlock();

        Iterable<Transaction> iterable = chainblock.getByTransactionStatusAndMaximumAmount(TransactionStatus.UNAUTHORIZED, 0);

        List<Transaction> actual = createListFromIterable(iterable);

        assertTrue(actual.isEmpty());
    }

    @Test
    public void testGetByTransactionStatusAndMaximumAmountShouldReturnCorrectTransactions(){
        addTransactionsToTheChainBlock();

        List<Transaction> expected = transactionList.stream()
                .filter(t -> t.getStatus().equals(TransactionStatus.SUCCESSFUL) && t.getAmount() <= 100)
                .collect(Collectors.toList());

        List<Transaction> actual = createListFromIterable(chainblock.getByTransactionStatusAndMaximumAmount(TransactionStatus.SUCCESSFUL, 100));

        assertEquals(expected.size(), actual.size());

        for (Transaction t : actual) {
            assertEquals(TransactionStatus.SUCCESSFUL, t.getStatus());
            assertTrue(Double.compare(100, t.getAmount()) <= 100);
        }
    }

    @Test
    public void testGetByTransactionStatusAndMaximumAmountShouldReturnCorrectTransactionsInCorrectOrder(){
        addTransactionsToTheChainBlock();

        List<Transaction> expected = transactionList.stream()
                .filter(t -> t.getStatus().equals(TransactionStatus.SUCCESSFUL) && t.getAmount() <= 100)
                .sorted(Comparator.comparing(Transaction::getAmount).reversed())
                .collect(Collectors.toList());

        List<Transaction> actual = createListFromIterable(chainblock.getByTransactionStatusAndMaximumAmount(TransactionStatus.SUCCESSFUL, 100));

        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBySenderAndMinimumAmountDescendingShouldThrowIfThereAreNoSuchTransactions(){
        addTransactionsToTheChainBlock();

        chainblock.getBySenderAndMinimumAmountDescending("SENDER", 100000);
    }

    @Test
    public void testGetBySenderAndMinimumAmountDescendingShouldReturnCorrectTransactions(){
        addTransactionsToTheChainBlock();

        List<Transaction> expected = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Transaction t = new TransactionImpl(100 + i, TransactionStatus.FAILED, "TEST_SENDER", "to", 100 + 10 * i);
            chainblock.add(t);
            if(i >= 5){
                expected.add(t);
            }
        }

        List<Transaction> actual = createListFromIterable(chainblock.getBySenderAndMinimumAmountDescending("TEST_SENDER", 150));

        assertEquals(5, actual.size());
        assertEquals(expected.size(), actual.size());

        for (Transaction t : actual) {
            assertEquals("TEST_SENDER", t.getFrom());
            assertTrue(Double.compare(150, t.getAmount()) <= 0);
        }
    }

    @Test
    public void testGetBySenderAndMinimumAmountDescendingShouldReturnCorrectTransactionsInCorrectOrder(){
        addTransactionsToTheChainBlock();

        List<Transaction> expected = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Transaction t = new TransactionImpl(100 + i, TransactionStatus.FAILED, "TEST_SENDER", "to", 100 + 10 * i);
            chainblock.add(t);
            if(i >= 5){
                expected.add(t);
            }
        }

        expected.sort(Comparator.comparing(Transaction::getAmount).reversed());

        List<Transaction> actual = createListFromIterable(chainblock.getBySenderAndMinimumAmountDescending("TEST_SENDER", 150));

        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByReceiverAndAmountRangeShouldThrowIfThereAreNoSuchTransactions(){
        addTransactionsToTheChainBlock();

        chainblock.getByReceiverAndAmountRange("SOME_RECEIVER", 100000, 1000000);
    }

    @Test
    public void testGetByReceiverAndAmountRangeShouldReturnCorrectTransactions(){
        addTransactionsToTheChainBlock();

        for (int i = 0; i < 20; i++) {
            //50 - 240 amount range
            Transaction t = new TransactionImpl(300 + i, TransactionStatus.SUCCESSFUL, "tst", "SOME_RECEIVER", 50 + i * 10);
            chainblock.add(t);
        }

        List<Transaction> actual = createListFromIterable(chainblock.getByReceiverAndAmountRange("SOME_RECEIVER", 100, 190));

        assertEquals(9, actual.size());

        for (Transaction t : actual) {
            assertEquals("SOME_RECEIVER", t.getTo());
            assertTrue(t.getAmount() >= 100 && t.getAmount() < 190);
        }
    }

    @Test
    public void testGetByReceiverAndAmountRangeShouldReturnCorrectTransactionsInCorrectOrder(){
        addTransactionsToTheChainBlock();

        List<Transaction> expected = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            //50 - 240 amount range
            Transaction t = new TransactionImpl(300 + i, TransactionStatus.SUCCESSFUL, "tst", "SOME_RECEIVER", 50 + i * 10);
            chainblock.add(t);
            expected.add(t);
        }

        expected.removeIf(t -> t.getAmount() < 100 || t.getAmount() >= 190);
        expected.sort(Comparator.comparing(Transaction::getAmount).reversed().thenComparing(Transaction::getId));

        List<Transaction> actual = createListFromIterable(chainblock.getByReceiverAndAmountRange("SOME_RECEIVER", 100, 190));

        assertEquals(expected, actual);
    }

    @Test
    public void testAllInAmountRangeShouldReturnEmptyCollectionIfThereAreNoSuchTransactions(){
        addTransactionsToTheChainBlock();

        List<Transaction> actual = createListFromIterable(chainblock.getAllInAmountRange(100000, 1000000));

        assertTrue(actual.isEmpty());
    }

    @Test
    public void testAllInAmountRangeShouldReturnCorrectTransactions(){
        addTransactionsToTheChainBlock();

        for (int i = 0; i < 20; i++) {
            //50 - 240 amount range
            Transaction t = new TransactionImpl(300 + i, TransactionStatus.SUCCESSFUL, "tst", "TST", 100_000 + i * 10);
            chainblock.add(t);
        }

        List<Transaction> actual = createListFromIterable(chainblock.getAllInAmountRange(100_100, 100_190));

        assertEquals(9, actual.size());

        for (Transaction t : actual) {
            assertTrue(t.getAmount() >= 100_100 && t.getAmount() < 100_190);
        }
    }

    @Test
    public void testAllInAmountRangeShouldReturnCorrectTransactionsInCorrectOrder(){
        addTransactionsToTheChainBlock();

        for (int i = 0; i < 20; i++) {
            //50 - 240 amount range
            Transaction t = new TransactionImpl(300 + i, TransactionStatus.SUCCESSFUL, "tst", "SOME_RECEIVER", 100_000 + i * 10);
            chainblock.add(t);
        }

        List<Transaction> actual = createListFromIterable(chainblock.getAllInAmountRange(100_100, 100_190));

        for (int i = 0; i < actual.size() - 1; i++) {
            assertTrue(actual.get(i).getId() < actual.get(i + 1).getId());
            assertTrue(actual.get(i).getId() >= 300);
        }
    }

    private List<Transaction> createListFromIterable(Iterable<Transaction> iterable) {
        assertNotNull(iterable);

        List<Transaction> list = new ArrayList<>();

        iterable.forEach(list::add);

        return list;
    }
}