package blockchain;

// Test append/firstInvalidBlock
public class Test7 {

    public static int NUM_TXS = 10;

    public static Transaction[] genTxs(int rand) {
        Transaction[] txs = new Transaction[NUM_TXS];
        for (int i = 0; i < NUM_TXS; i++) {
            txs[i] = new Transaction(i + 12345 * rand, i + 5321 * rand, i * 123 + rand);
        }
        return txs;
    }

    public static Block[] genBlocks(int numBlock) {
        Block[] bs = new Block[numBlock];
        long baseTs = 1234567;
        long diff = 0xff;

        for (int i = 0; i < numBlock; i++) {
            if (i == 0) {
                // First block (genesis block)
                bs[0] = new Block(baseTs);
            } else {
                bs[i] = new Block(baseTs + i, bs[i - 1].getHash());
            }
            Transaction[] txs = genTxs(i);
            for (int j = 0; j < NUM_TXS; j++) {
                bs[i].addTransaction(txs[j]);
            }
            bs[i].mine(diff);
        }
        return bs;
    }

    public static String blockToString(Block b) {
        return "Block: timestamp " + b.getTimestamp() + ", prevHash " + b.getPrevHash();
    }

    public static void main(String[] args) {
        Block[] bs = genBlocks(5);

        // Create a blockchain and fill with 5 blocks
        BlockChain bc = new BlockChain();
        for (int i = 0; i < 5; i++) {
            bc.append(bs[i]);
        }

        // All blocks should be valid
        if (bc.firstInvalidBlock() != -1)
            return;
        System.out.println("Pass checkpoint 1");

        // invalid chaining
        Block b = new Block(55555, bs[1].getHash());
        Transaction[] txs = genTxs(6);
        for (int j = 0; j < NUM_TXS; j++) {
            b.addTransaction(txs[j]);
        }
        b.mine(0xff);
        bc.append(b);

        // The last block should be invalid because
        // it does not chain with the fourth block
        // even though mining is done correctly
        if (bc.firstInvalidBlock() != 5)
            return;
        System.out.println("Pass checkpoint 2");

        System.out.println("All good!");

    }

}