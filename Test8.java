package blockchain;

// test modifyBlock
public class Test8 {
    public static int NUM_TXS = 10;

    public static Transaction[] genTxs(int rand) {
        Transaction[] txs = new Transaction[NUM_TXS];
        for(int i=0; i<NUM_TXS; i++) {
            txs[i] = new Transaction(i+12345*rand, i+5321*rand, i*123+rand);
        }
        return txs;
    }

    public static Block[] genBlocks(int numBlock) {
        Block[] bs = new Block[numBlock];
        long baseTs = 1234567;
        long diff = 0xff;

        for(int i=0; i<numBlock; i++) {
            if(i==0) {
                // First block (genesis block)
                bs[0] = new Block(baseTs);
            } else {
                bs[i] = new Block(baseTs+i, bs[i-1].getHash());
            }
            Transaction[] txs = genTxs(i);
            for(int j=0; j<NUM_TXS; j++) {
                bs[i].addTransaction(txs[j]);
            }
            bs[i].mine(diff);
        }
        return bs;
    }
    public static String blockToString(Block b) {
        return "Block: timestamp "+b.getTimestamp()+", prevHash "+b.getPrevHash();
    }
    
    public static void main(String[] args) {
        Block[] bs = genBlocks(5);
        // Create blockchain and fill with 5 blocks
        BlockChain bc = new BlockChain();
        for(int i=0; i<5; i++){
            bc.append(bs[i]);
        }
        
        if(bc.firstInvalidBlock() != -1) return;
        System.out.println("Pass checkpoint 1");

        // Assign block #3 with a newly created block
        Block b = new Block(55555, bs[2].getHash());
        Transaction[] txs = genTxs(6);
        for(int j=0; j<NUM_TXS; j++) {
            b.addTransaction(txs[j]);
        }
        b.mine(0xff);
        bc.modifyBlock(b, 3);

        // 4th block should be invalid since 
        // the thrid block (and its hash) has changed
        if(bc.firstInvalidBlock() != 4) return;
        System.out.println("Pass checkpoint 2");

        // Modify block #3 with wrong prevHash
        b = new Block(55555, bs[1].getHash());
        txs = genTxs(6);
        for(int j=0; j<NUM_TXS; j++) {
            b.addTransaction(txs[j]);
        }
        b.mine(0xff);
        bc.modifyBlock(b, 3);

        // This newly created 3rd block should be invalid
        if(bc.firstInvalidBlock() != 3) return;
        System.out.println("Pass checkpoint 3");

        // Modify block #3 back to the original valid block
        // Everything should be fine.
        bc.modifyBlock(bs[3], 3);
        if(bc.firstInvalidBlock() != -1) return;
        System.out.println("Pass checkpoint 4");

        // Modify last block, i.e., block #4 with wrong prevHash
        b = new Block(55555, 23456);
        txs = genTxs(234);
        for(int j=0; j<NUM_TXS; j++) {
            b.addTransaction(txs[j]);
        }
        b.mine(0xff);
        bc.modifyBlock(b, 4);

        // 4th block should be invalid
        if(bc.firstInvalidBlock() != 4) return;
        System.out.println("Pass checkpoint 5");
        
        // Modify last block, i.e., block #4 with valid block
        b = new Block(55555, bs[3].getHash());
        txs = genTxs(98531456);
        for(int j=0; j<NUM_TXS; j++) {
            b.addTransaction(txs[j]);
        }
        b.mine(0xff);
        bc.modifyBlock(b, 4);

        // 4th block should be valid
        // even though its transaction and timestamp
        // have been modified
        if(bc.firstInvalidBlock() != -1) return;
        System.out.println("Pass checkpoint 6");
        
        System.out.println("All good!");

    }
}
