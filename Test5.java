package blockchain;

// Test mine method (2)
public class Test5 {
    
    public static int NUM_TXS = 10;

    public static Transaction[] genTxs() {
        Transaction[] txs = new Transaction[NUM_TXS];
        for(int i=0; i<NUM_TXS; i++) {
            txs[i] = new Transaction(i, i, i);
        }
        return txs;
    }

    public static String blockToString(Block b) {
        return "Block: timestamp "+b.getTimestamp()+", prevHash "+b.getPrevHash();
    }
    
    public static void main(String[] args) {
        Transaction[] txs = genTxs();
        
        // Create a new block and assign transactions to it
        Block b = new Block(1000);
        for(int i=0; i<NUM_TXS; i++){
            b.addTransaction(txs[i]);
        }
        
        System.out.println("Mining: "+blockToString(b));
        // Increase difficulty level: L indicates this number is of type long
        // It should take a couple of seconds or a minute
        long diff = -0x7fffff0fffffffffL; 
        b.mine(diff);

        // Hash should be < diff and isValid should return true
        if(b.getHash() >= diff || !b.isValid()) return;
        System.out.println("Pass checkpoint 1");

        // Now, manually setting blockHash to the new value 
        // This should result in isValid() = false
        b.setBlockHash(3567821);
        if(b.isValid()) return;
        System.out.println("Pass checkpoint 2");
        
        System.out.println("All good!");
    }
}
