import java.util.*;

public class MaxFeeTxHandler extends TxHandler {
    public MaxFeeTxHandler(UTXOPool utxoPool) {
        super(utxoPool);
    }

    @Override
    public Transaction[] handleTxs(Transaction[] possibleTxs) {
        List<Transaction> selectedTxs = new ArrayList<>();
        Arrays.sort(possibleTxs, (a, b) -> Double.compare(computeFee(b), computeFee(a)));

        for (Transaction tx : possibleTxs) {
            if (isValidTx(tx)) {
                selectedTxs.add(tx);
            }
        }
        return selectedTxs.toArray(new Transaction[0]);
    }

    private double computeFee(Transaction tx) {
        double inputSum = 0, outputSum = 0;

        for (Transaction.Input in : tx.getInputs()) {
            UTXO utxo = new UTXO(in.prevTxHash, in.outputIndex);
            if (utxoPool.contains(utxo)) {
                inputSum += utxoPool.getTxOutput(utxo).value;
            }
        }

        for (Transaction.Output out : tx.getOutputs()) {
            outputSum += out.value;
        }

        return inputSum - outputSum;
    }
}
