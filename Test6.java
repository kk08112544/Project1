package blockchain;

// test isValid
public class Test6 {
    
    public static int NUM_TXS = 10;

    public static Transaction[] genTxs() {
        Transaction[] txs = new Transaction[NUM_TXS];
        for(int i=0; i<NUM_TXS; i++) {
            //txs[i] = new Transaction(i+12345, i+5321, i*123);
            txs[i] = new Transaction(0, 0, 0);
        }
        return txs;
    }

    public static String blockToString(Block b) {
        return "Block: timestamp "+b.getTimestamp()+", prevHash "+b.getPrevHash();
    }
    
    public static void main(String[] args) {
        Block b = new Block(0);
        Transaction[] txs = genTxs();
        
        for(int i=0; i<NUM_TXS; i++){
            b.addTransaction(txs[i]);
        }

        b.setNonce(0);
        if(b.isValid()) return;

        // First 64 bits of SHA256("0") = 6912158355717386040
        b.setBlockHash(6912158355717386040L);
        if(!b.isValid()) return;

        b.setNonce(123456);
        if(b.isValid()) return;

        b.setBlockHash(-8244227316661955646L);
        if(!b.isValid()) return;

        b.setNonce(821376591381998L);
        if(b.isValid()) return;

        b.setBlockHash(3470687238763665914L);
        if(!b.isValid()) return;

        b.setNonce(-1111111111);
        if(b.isValid()) return;

        b.setBlockHash(-294286252769050086L);
        if(!b.isValid()) return;

        b.setBlockHash(8164234271813369514L);
        if(b.isValid()) return;

        b.setNonce(38765234L);
        if(!b.isValid()) return;

        System.out.println("All good!");
    }
    
}
