import java.util.Arrays;

public class Simulation {
    public static void main(String[] args) {
        // Create the genesis block
        Block genesisBlock = new Block(null, "genesis".getBytes());
        genesisBlock.finalize();
        Blockchain blockChain = new Blockchain(genesisBlock);

        // Print the genesis block hash
        System.out.println("Genesis block hash: " + Arrays.toString(genesisBlock.getHash()));

        // Add some transactions to the pool
        Transaction tx1 = new Transaction();
        tx1.addOutput(10, null);
        tx1.finalize();

        Transaction tx2 = new Transaction();
        tx2.addOutput(20, null);
        tx2.finalize();

        blockChain.addTransaction(tx1);
        blockChain.addTransaction(tx2);

        // Create a new block
        Block block1 = new Block(genesisBlock.getHash(), "block1".getBytes());
        block1.addTransaction(tx1);
        block1.finalize();

        // Add the block to the blockchain
        if (blockChain.addBlock(block1)) {
            System.out.println("Block1 added successfully.");
        } else {
            System.out.println("Block1 failed to add.");
        }

        // Check the max height block
        System.out.println("Max height block hash: " + Arrays.toString(blockChain.getMaxHeightBlock().getHash()));
    }
}
