package blockchain;

// Test all features
public class Test10 {

    public static int NUM_TXS = 10;
    public static int NUM_BLOCKS = 100;

    public static Transaction[] genTxs(int rand) {
        Transaction[] txs = new Transaction[NUM_TXS];
        for (int i = 0; i < NUM_TXS; i++) {
            txs[i] = new Transaction(i + 12345 * rand, i + 5321 * rand, i * 123 + rand);
        }
        return txs;
    }

    public static String txToString(Transaction tx) {
        return "Transaction from " + tx.getSenderId() + " to " + tx.getReceiverId() + " amount " + tx.getAmount();
    }

    public static String blockToString(Block b) {
        String output = "Block: timestamp " + b.getTimestamp() + ", prevHash " + b.getPrevHash() + ", nonce "
                + b.getNonce() + ", hash " + b.getHash() + "\n";
        for (int i = 0; i < NUM_TXS; i++) {
            output += " " + txToString(b.getTransaction(i)) + "\n";
        }
        return output;
    }

    public static void main(String[] args) {
        // Create a blockchain
        BlockChain bChain = new BlockChain();
        Block[] blocks = new Block[NUM_BLOCKS];

        long baseTs = 189821506;
        long prevHash = 0;
        for (int i = 0; i < NUM_BLOCKS; i++) {

            // Mine each block and assign it to bChain
            // Later blocks are mined with higher difficulty level
            blocks[i] = new Block(baseTs + i, prevHash);
            long difficulty = -0x7fff0fffffffffffL - 100 * i;
            Transaction[] txs = genTxs(i);
            for (int j = 0; j < txs.length; j++) {
                blocks[i].addTransaction(txs[j]);
            }
            blocks[i].mine(difficulty);
            bChain.append(blocks[i]);
            System.out.println("Succesfully mining block " + i);
            System.out.println("Block content: " + blockToString(blocks[i]));
            prevHash = blocks[i].getHash();
        }

        // Check if this blockchain is valid
        System.out.println("Blockchain is valid? " + (bChain.firstInvalidBlock() == -1));

        // Check if this transaction is in blockchain
        Transaction tx = new Transaction(2 + 12345 * 90, 2 + 5321 * 90, 2 * 123 + 90);
        System.out.println("Transaction: " + txToString(tx) + " is in blockchain? " + bChain.isTxInBlockchain(tx));

        // This transaction should not be in blockchain
        tx = new Transaction(100, 200, 300);
        System.out.println("Transaction: " + txToString(tx) + " is in blockchain? " + bChain.isTxInBlockchain(tx));

        // Modify the first block with last block
        // The first block should be invalid since this modified first block
        // is no longer a genesis block
        bChain.modifyBlock(blocks[NUM_BLOCKS - 1], 0);
        System.out.println("Which block is invalid? " + bChain.firstInvalidBlock());

        // Restore the first block
        // It now should be all valid
        bChain.modifyBlock(blocks[0], 0);
        System.out.println("Which block is invalid? " + bChain.firstInvalidBlock());

        // Replace the last block with a newly created valid block
        Block b = new Block(0, blocks[NUM_BLOCKS - 2].getHash());
        Transaction[] txs = genTxs(0);
        for (int j = 0; j < txs.length; j++) {
            b.addTransaction(txs[j]);
        }
        // Without mining, this new block should be invalid
        bChain.modifyBlock(b, NUM_BLOCKS - 1);
        System.out.println("Which block is invalid? " + bChain.firstInvalidBlock());

        // With mining, it should be good
        b.mine(0);
        bChain.modifyBlock(b, NUM_BLOCKS - 1);
        System.out.println("Which block is invalid? " + bChain.firstInvalidBlock());

        // Replace block #88 with an invalid prevHash
        b = new Block(0, 234);
        txs = genTxs(0);
        for (int j = 0; j < txs.length; j++) {
            b.addTransaction(txs[j]);
        }
        b.mine(0);
        bChain.modifyBlock(b, 88);

        // block #88 should be invalid
        System.out.println("Which block is invalid? " + bChain.firstInvalidBlock());

        // Replace block #88 with a valid prevHash
        b = new Block(0, blocks[87].getHash());
        txs = genTxs(0);
        for (int j = 0; j < txs.length; j++) {
            b.addTransaction(txs[j]);
        }
        b.mine(0);
        bChain.modifyBlock(b, 88);

        // block #89 should be invalid
        System.out.println("Which block is invalid? " + bChain.firstInvalidBlock());

    }

}
