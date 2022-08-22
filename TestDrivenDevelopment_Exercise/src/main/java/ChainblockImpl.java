import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ChainblockImpl implements Chainblock {
    Map<Integer, Transaction> transactions;

    public ChainblockImpl() {
        this.transactions = new LinkedHashMap<>();
    }

    public int getCount() {
        return this.transactions.size();
    }

    public void add(Transaction transaction) {
        if (!transactions.containsKey(transaction.getId())) {
            transactions.put(transaction.getId(), transaction);
        }
    }

    public boolean contains(Transaction transaction) {
        return contains(transaction.getId());
    }

    public boolean contains(int id) {
        return this.transactions.containsKey(id);
    }

    public void changeTransactionStatus(int id, TransactionStatus newStatus) {
        validateId(id);
        transactions.get(id).setStatus(newStatus);
    }

    public void removeTransactionById(int id) {
        validateId(id);
        transactions.remove(id);
    }

    public Transaction getById(int id) {
        validateId(id);
        return transactions.get(id);
    }

    public Iterable<Transaction> getByTransactionStatus(TransactionStatus status) {
        List<Transaction> transactionList = new ArrayList<>();

        for (Transaction t : transactions.values()) {
            if (t.getStatus().equals(status)) {
                transactionList.add(t);
            }
        }

        transactionList.sort((a, b) -> Double.compare(b.getAmount(), a.getAmount()));

        if (transactionList.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return transactionList;
    }

    public Iterable<String> getAllSendersWithTransactionStatus(TransactionStatus status) {
        List<Transaction> transactions = new ArrayList<>();

        getByTransactionStatus(status).forEach(transactions::add);


        return transactions.stream()
                .map(Transaction::getFrom)
                .collect(Collectors.toList());
    }

    public Iterable<String> getAllReceiversWithTransactionStatus(TransactionStatus status) {
        List<Transaction> transactions = new ArrayList<>();

        getByTransactionStatus(status).forEach(transactions::add);


        return transactions.stream()
                .map(Transaction::getTo)
                .collect(Collectors.toList());
    }

    public Iterable<Transaction> getAllOrderedByAmountDescendingThenById() {
        return transactions.values().stream()
                .sorted(Comparator.comparing(Transaction::getAmount).reversed()
                        .thenComparing(Transaction::getId))
                .collect(Collectors.toList());
    }

    public Iterable<Transaction> getBySenderOrderedByAmountDescending(String sender) {
        List<Transaction> byCorrectSender = new ArrayList<>();

        byCorrectSender = transactions.values().stream()
                .filter(t -> t.getFrom().equals(sender))
                .collect(Collectors.toList());

        if(byCorrectSender.isEmpty()){
            throw new IllegalArgumentException();
        }

        byCorrectSender.sort(Comparator.comparing(Transaction::getAmount).reversed());

        return byCorrectSender;
    }

    public Iterable<Transaction> getByReceiverOrderedByAmountThenById(String receiver) {
        List<Transaction> transactions = this.transactions.values().stream()
                .filter(t -> t.getTo().equals(receiver))
                .sorted(Comparator.comparing(Transaction::getAmount).reversed().thenComparing(Transaction::getId))
                .collect(Collectors.toCollection(ArrayList::new));

        if(transactions.isEmpty()){
            throw new IllegalArgumentException();
        }

        return transactions;
    }

    public Iterable<Transaction> getByTransactionStatusAndMaximumAmount(TransactionStatus status, double amount) {
        List<Transaction> transactions = this.transactions.values().stream()
                .filter(t -> t.getStatus().equals(status) && t.getAmount() <= amount)
                .sorted(Comparator.comparing(Transaction::getAmount).reversed())
                .collect(Collectors.toList());

        return !transactions.isEmpty() ? transactions : new ArrayList<>();
    }

    public Iterable<Transaction> getBySenderAndMinimumAmountDescending(String sender, double amount) {
        List<Transaction> transactions = this.transactions.values().stream()
                .filter(t -> t.getFrom().equals(sender) && t.getAmount() >= amount)
                .sorted(Comparator.comparing(Transaction::getAmount).reversed())
                .collect(Collectors.toList());

        if(transactions.isEmpty()){
            throw new IllegalArgumentException();
        }

        return transactions;
    }

    public Iterable<Transaction> getByReceiverAndAmountRange(String receiver, double lo, double hi) {
        Predicate<Transaction> predicate = t -> t.getTo().equals(receiver) && t.getAmount() >= lo && t.getAmount() < hi;

        List<Transaction> transactions = this.transactions.values().stream()
                .filter(predicate)
                .sorted(Comparator.comparing(Transaction::getAmount).reversed().thenComparing(Transaction::getId))
                .collect(Collectors.toList());

        if(transactions.isEmpty()){
            throw new IllegalArgumentException();
        }

        return transactions;
    }

    public Iterable<Transaction> getAllInAmountRange(double lo, double hi) {
        Predicate<Transaction> predicate = t -> t.getAmount() >= lo && t.getAmount() < hi;

        List<Transaction> transactions = this.transactions.values().stream()
                .filter(predicate)
                .collect(Collectors.toList());

        return !transactions.isEmpty() ? transactions : new ArrayList<>();
    }

    public Iterator<Transaction> iterator() {
        return transactions.values().iterator();
    }

    private void validateId(int id) {
        if (!transactions.containsKey(id)) {
            throw new IllegalArgumentException();
        }
    }
}
