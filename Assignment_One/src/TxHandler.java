import java.util.*;

public class TxHandler {
    private UTXOPool utxoPool;

    public TxHandler(UTXOPool utxoPool) {
        this.utxoPool = new UTXOPool(utxoPool); // Defensive copy
    }

    public boolean isValidTx(Transaction tx) {
        Set<UTXO> claimedUTXOs = new HashSet<>();
        double inputSum = 0, outputSum = 0;

        for (int i = 0; i < tx.numInputs(); i++) {
            Transaction.Input in = tx.getInput(i);
            UTXO utxo = new UTXO(in.prevTxHash, in.outputIndex);

            // (1) Check if the UTXO exists in the pool
            if (!utxoPool.contains(utxo)) return false;

            // (2) Check signature validity
            Transaction.Output output = utxoPool.getTxOutput(utxo);
            if (!Crypto.verifySignature(output.address, tx.getRawDataToSign(i), in.signature)) return false;

            // (3) Check double-spending
            if (claimedUTXOs.contains(utxo)) return false;
            claimedUTXOs.add(utxo);

            inputSum += output.value;
        }

        // (4) Check if all output values are non-negative
        for (Transaction.Output out : tx.getOutputs()) {
            if (out.value < 0) return false;
            outputSum += out.value;
        }

        // (5) Check if input sum >= output sum
        return inputSum >= outputSum;
    }

    public Transaction[] handleTxs(Transaction[] possibleTxs) {
        List<Transaction> validTxs = new ArrayList<>();

        for (Transaction tx : possibleTxs) {
            if (isValidTx(tx)) {
                validTxs.add(tx);

                // Remove spent UTXOs
                for (Transaction.Input in : tx.getInputs()) {
                    UTXO utxo = new UTXO(in.prevTxHash, in.outputIndex);
                    utxoPool.removeUTXO(utxo);
                }

                // Add new UTXOs
                byte[] txHash = tx.getHash();
                for (int i = 0; i < tx.numOutputs(); i++) {
                    UTXO utxo = new UTXO(txHash, i);
                    utxoPool.addUTXO(utxo, tx.getOutput(i));
                }
            }
        }

        return validTxs.toArray(new Transaction[0]);
    
    }
}

