package blockchain;

// Test add/getTransaction for Block class
public class Test3 {

    public static int NUM_TXS = 10;

    public static Transaction[] genTxs(int rand) {
        Transaction[] txs = new Transaction[NUM_TXS];
        for(int i=0; i<NUM_TXS; i++) {
            txs[i] = new Transaction(i+123456*rand, i+7890*rand, i+rand);
        }
        return txs;
    }

    public static String txToString(Transaction tx) {
        return "Transaction from "+tx.getSenderId()+" to "+tx.getReceiverId()+" amount "+tx.getAmount();
    }

    public static void main(String[] args) {
        // Create a genesis block
        Block b = new Block(1000);
        Transaction[] txs = genTxs(1);
        
        // Add generated transactions to block
        for(int i=0; i<NUM_TXS; i++){
            b.addTransaction(txs[i]);
        }
        
        System.out.println("Block's timestamp: "+b.getTimestamp());
        System.out.println("Block's prevHash: "+b.getPrevHash());
        // Print all transactions
        for(int i=0; i<NUM_TXS; i++) {
            Transaction tx = b.getTransaction(i);
            System.out.println("TX at index "+i+" : "+txToString(tx));
        }

        // Create a new block
        b = new Block(12345, 5555);
        
        // Generate new transactions and add them to block
        txs = genTxs(234);
        for(int i=0; i<NUM_TXS; i++){
            b.addTransaction(txs[i]);
        }
        
        System.out.println("Block's timestamp: "+b.getTimestamp());
        System.out.println("Block's prevHash: "+b.getPrevHash());
        for(int i=0; i<NUM_TXS; i++) {
            Transaction tx = b.getTransaction(i);
            System.out.println("TX at index "+i+" : "+txToString(tx));
        }


    }
    
}
