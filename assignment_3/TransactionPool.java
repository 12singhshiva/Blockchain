import java.util.*;

public class TransactionPool {
    private final Map<ByteArrayWrapper, Transaction> pool;

    public TransactionPool() {
        this.pool = new HashMap<>();
    }

    public void addTransaction(Transaction tx) {
        pool.put(new ByteArrayWrapper(tx.getHash()), tx);
    }

    public void removeTransaction(byte[] txHash) {
        pool.remove(new ByteArrayWrapper(txHash));
    }

    public Transaction getTransaction(byte[] txHash) {
        return pool.get(new ByteArrayWrapper(txHash));
    }

    public Set<Transaction> getTransactions() {
        return new HashSet<>(pool.values());
    }
}
