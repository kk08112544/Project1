package blockchain;

// Test mine method
public class Test4 {

    public static int NUM_TXS = 10;

    public static Transaction[] genTxs() {
        Transaction[] txs = new Transaction[NUM_TXS];
        for (int i = 0; i < NUM_TXS; i++) {
            txs[i] = new Transaction(i, i, i);
        }
        return txs;
    }

    public static String blockToString(Block b) {
        return "Block: timestamp " + b.getTimestamp() + ", prevHash " + b.getPrevHash();
    }

    public static String txToString(Transaction tx) {
        return "Transaction from " + tx.getSenderId() + " to " + tx.getReceiverId() + " amount " + tx.getAmount();
    }

    public static void main(String[] args) {
        Transaction[] txs = genTxs();

        // Test mining genesis block
        Block b = new Block(1000);
        for (int i = 0; i < NUM_TXS; i++) {
            b.addTransaction(txs[i]);
        }

        System.out.println("Mining: " + blockToString(b));
        // mine block with difficulty = -1
        long diff = -1;
        b.mine(diff);

        // Hash should be < diff and isValid should return true
        if (b.getHash() >= diff || !b.isValid())
            return;
        System.out.println("Pass checkpoint 1");

        // Test mining non-genesis block
        b = new Block(1000, 8888);
        for (int i = 0; i < NUM_TXS; i++) {
            b.addTransaction(txs[i]);
        }

        System.out.println("Mining: " + blockToString(b));
        b.mine(diff);

        // Hash should be < diff and isValid should return true
        if (b.getHash() >= diff || !b.isValid())
            return;
        System.out.println("Pass checkpoint 2");

        System.out.println("All good!");

    }

}
