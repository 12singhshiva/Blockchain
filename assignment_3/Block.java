import java.util.ArrayList;
import java.util.List;

public class Block {
    private byte[] prevBlockHash;
    private byte[] hash;
    private List<Transaction> transactions;
    private Transaction coinbase;

    public Block(byte[] prevBlockHash, byte[] coinbaseHash) {
        this.prevBlockHash = prevBlockHash;
        this.transactions = new ArrayList<>();
        this.coinbase = new Transaction();
        this.coinbase.addOutput(10, null); // Example coinbase transaction
        this.coinbase.finalize();
    }

    public byte[] getPrevBlockHash() {
        return prevBlockHash;
    }

    public byte[] getHash() {
        return hash;
    }

    public void finalize() {
        // Simplified hash computation for demo purposes
        this.hash = new byte[]{(byte) (Math.random() * 255)};
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Transaction getCoinbase() {
        return coinbase;
    }

    public void addTransaction(Transaction tx) {
        transactions.add(tx);
    }
}

