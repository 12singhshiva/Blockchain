import java.util.HashMap;

public class UTXOPool {
    private HashMap<UTXO, Transaction.Output> pool;

    // Default constructor
    public UTXOPool() {
        this.pool = new HashMap<>();
    }

    // Copy constructor
    public UTXOPool(UTXOPool uPool) {
        this.pool = new HashMap<>(uPool.getPool()); // Use getter to access 'pool'
    }

    // Add a UTXO
    public void addUTXO(UTXO utxo, Transaction.Output output) {
        pool.put(utxo, output);
    }

    // Remove a UTXO
    public void removeUTXO(UTXO utxo) {
        pool.remove(utxo);
    }

    // Get a transaction output
    public Transaction.Output getTxOutput(UTXO utxo) {
        return pool.get(utxo);
    }

    // Check if a UTXO exists
    public boolean contains(UTXO utxo) {
        return pool.containsKey(utxo);
    }

    // Getter for pool (used in the copy constructor)
    public HashMap<UTXO, Transaction.Output> getPool() {
        return this.pool;
    }
}
