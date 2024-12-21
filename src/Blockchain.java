import java.util.*;

public class Blockchain {
    public static final int CUT_OFF_AGE = 10;

    private class BlockNode {
        Block block;
        BlockNode parent;
        UTXOPool utxoPool;
        int height;

        public BlockNode(Block block, BlockNode parent, UTXOPool utxoPool) {
            this.block = block;
            this.parent = parent;
            this.utxoPool = utxoPool;
            this.height = (parent == null) ? 1 : parent.height + 1;
        }
    }

    private BlockNode maxHeightNode; // The block with the maximum height
    private Map<ByteArrayWrapper, BlockNode> blockMap; // Maps block hash to BlockNode
    private TransactionPool transactionPool;

    public Blockchain(Block genesisBlock) {
        UTXOPool genesisUTXOPool = new UTXOPool();
        addCoinbaseToUTXOPool(genesisBlock, genesisUTXOPool);
        BlockNode genesisNode = new BlockNode(genesisBlock, null, genesisUTXOPool);

        this.maxHeightNode = genesisNode;
        this.blockMap = new HashMap<>();
        this.transactionPool = new TransactionPool();

        blockMap.put(new ByteArrayWrapper(genesisBlock.getHash()), genesisNode);
    }

    public Block getMaxHeightBlock() {
        return maxHeightNode.block;
    }

    public UTXOPool getMaxHeightUTXOPool() {
        return new UTXOPool(maxHeightNode.utxoPool);
    }

    public TransactionPool getTransactionPool() {
        return transactionPool;
    }

    public boolean addBlock(Block block) {
        if (block.getPrevBlockHash() == null) {
            return false;
        }

        ByteArrayWrapper parentHash = new ByteArrayWrapper(block.getPrevBlockHash());
        BlockNode parentNode = blockMap.get(parentHash);

        if (parentNode == null) {
            return false;
        }

        if (parentNode.height + 1 <= maxHeightNode.height - CUT_OFF_AGE) {
            return false;
        }

        UTXOPool newUTXOPool = new UTXOPool(parentNode.utxoPool);
        TxHandler txHandler = new TxHandler(newUTXOPool);
        Transaction[] validTxs = txHandler.handleTxs(block.getTransactions().toArray(new Transaction[0]));

        if (validTxs.length != block.getTransactions().size()) {
            return false;
        }

        addCoinbaseToUTXOPool(block, newUTXOPool);
        BlockNode newNode = new BlockNode(block, parentNode, newUTXOPool);
        blockMap.put(new ByteArrayWrapper(block.getHash()), newNode);

        for (Transaction tx : block.getTransactions()) {
            transactionPool.removeTransaction(tx.getHash());
        }

        if (newNode.height > maxHeightNode.height) {
            maxHeightNode = newNode;
        }

        return true;
    }

    public void addTransaction(Transaction tx) {
        transactionPool.addTransaction(tx);
    }

    private void addCoinbaseToUTXOPool(Block block, UTXOPool utxoPool) {
        Transaction coinbase = block.getCoinbase();
        for (int i = 0; i < coinbase.numOutputs(); i++) {
            UTXO utxo = new UTXO(coinbase.getHash(), i);
            utxoPool.addUTXO(utxo, coinbase.getOutput(i));
        }
    }
}
