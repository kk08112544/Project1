package blockchain;

// test isTxInBlockchain
public class Test9 {

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

        BlockChain bc = new BlockChain();
        for (int i = 0; i < 5; i++) {
            bc.append(bs[i]);
        }

        // only txs created using rand=0,1,2,3 or 4 are valid
        Transaction[] txs = genTxs(0);

        // All of these txs should be in blockchain
        for (int i = 0; i < NUM_TXS; i++) {
            if (!bc.isTxInBlockchain(txs[i]))
                return;
        }
        System.out.println("Pass checkpoint 1");

        // only txs created using rand=0,1,2,3 or 4 are valid
        txs = genTxs(4);

        // All of these txs should be in blockchain
        for (int i = 0; i < NUM_TXS; i++) {
            if (!bc.isTxInBlockchain(txs[i]))
                return;
        }
        System.out.println("Pass checkpoint 2");

        // only txs created using rand=0,1,2,3 or 4 are valid
        txs = genTxs(5);

        // All of these txs should be in blockchain
        for (int i = 0; i < NUM_TXS; i++) {
            if (bc.isTxInBlockchain(txs[i]))
                return;
        }
        System.out.println("Pass checkpoint 3");

        // Generate new txs with rand=100 (invalid)
        txs = genTxs(100);

        // New txs should not be in blockchain
        for (int i = 0; i < NUM_TXS; i++) {
            if (bc.isTxInBlockchain(txs[i]))
                return;
        }
        System.out.println("Pass checkpoint 4");
        System.out.println("All good!");

    }
}