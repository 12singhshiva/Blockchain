import java.security.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Create a UTXOPool
            UTXOPool utxoPool = new UTXOPool();

            // Generate a dummy public key for testing
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(512); // Key size
            KeyPair pair = keyGen.generateKeyPair();
            PublicKey pubKey = pair.getPublic();

            // Create a dummy transaction and add it to the UTXOPool
            Transaction.Output output = new Transaction.Output(10.0, pubKey);
            byte[] txHash = new byte[]{0, 1, 2}; // Example transaction hash
            UTXO utxo = new UTXO(txHash, 0);
            utxoPool.addUTXO(utxo, output);

            // Initialize TxHandler with the UTXOPool
            TxHandler txHandler = new TxHandler(utxoPool);

            // Create a new transaction to test
            Transaction tx = new Transaction();
            tx.addInput(txHash, 0);
            tx.addOutput(5.0, pubKey);
            tx.addOutput(4.0, pubKey);
            tx.finalizeHash();

            // Debug the Transaction
            System.out.println("Transaction created: " + tx);

            // Test isValidTx
            boolean isValid = txHandler.isValidTx(tx);
            System.out.println("Is the transaction valid? " + isValid);

            // Test handleTxs
            Transaction[] validTxs = txHandler.handleTxs(new Transaction[]{tx});
            System.out.println("Number of valid transactions: " + validTxs.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
