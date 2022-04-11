package blockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;

import javax.sql.rowset.spi.TransactionalWriter;

public class Block {

    private long timestamp;
    private long prevHash = 0;
    private long hash;
    private long nonce;
    private Transaction[] transactions = new Transaction[10];// กำหนดarray transactionมีขนาด10
    private int currentIndex = 0;
    private boolean isGenesis = false;

    public Block(long timestamp) {
        this.timestamp = timestamp;
    }

    public Block(long timestamp, long prevHash) {
        this.timestamp = timestamp;
        this.prevHash = prevHash;
    }

    private long hash(long in) {
        String key = Long.toString(in);

        long hash = 0;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(key.getBytes(StandardCharsets.UTF_8));
            for (int i = 0; i < 8; i++) {
                hash <<= 8;
                hash ^= (long) bytes[i] & 0xff;
            }
        } catch (Exception e) {
            System.out.println("This message should not happen. Please contact Lecturer");
            e.printStackTrace();
        }

        return hash;
    }

    public void addTransaction(Transaction tx) {// เอาparameter ของtxใส่ลงในtransaction (transaction tx) บอกค่าว่าเป็น
        // variablesแบบไหน
        this.transactions[currentIndex] = tx;
        currentIndex++;// เพิ่่มไปเรื่อยๆจนถึงtransactionเท่ากับ10ว
    }

    public Transaction getTransaction(int idx) {// class transaction รับค่าtransactionรับเลขindexมาในparameter
        return this.transactions[idx];// return ค่าtransaction indexมา;
    }

    public int getTransactionLenght() {// อยากได้จำนวนของอาเรย์ตอนนี้เลยใช้currentIndexส่งกลับได้เลยว
        return currentIndex;
    }

    public void mine(long difficulty) {
        long result;
        long transactionSummery = 0;
        long currentNonce = -999999999;// เพราะว่าจะต้องการหาว่าตัวไหนที่ทำให้สมการเป็นจริง

        for (int i = 0; i < currentIndex; i++) {// จะนับจนกว่าจำนวนในarrayมากที่สุดทีมี
            transactionSummery = transactionSummery + transactions[i].getAmount() + transactions[i].getReceiverId()
                    + transactions[i].getSenderId();
        }
        do {// เพื่อที่จะให้สมการที่หาค่่าเปนจริง
            result = currentNonce + timestamp + transactionSummery;
            this.nonce = currentNonce;
            currentNonce++;
            this.hash = hash(result);// เปนการเรียกmethod ของhash
        } while (this.hash >= difficulty);// ถ้ามากกว่ามันจะทำต่อไปเรื่อยๆจนกว่าจะน้อยกว่าdifficulty
    }

    public boolean isValid() {
        long result = 0;
        long transactionSummery = 0;
        for (int i = 0; i < currentIndex; i++) {
            transactionSummery = transactionSummery + transactions[i].getAmount() + transactions[i].getReceiverId()
                    + transactions[i].getSenderId();
        } // หาผลรวมtransaction summery แล้วเอามาบวกกับnonce,timestamp;
        result = this.nonce + timestamp + transactionSummery;

        return (this.hash == hash(result));// return เท่ากับ2ตัวเปนboolean;
    }

    public void setNonce(long n) {// เรียกค่าparameter มาset attributeในclass;
        this.nonce = n;
    }

    public long getNonce() {// ส่งค่ากลับให้คลาสที่เรียกมัน
        return this.nonce;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public long getHash() {
        return this.hash;
    }

    public long getPrevHash() {
        return this.prevHash;
    }

    public void setBlockHash(long h) {
        this.hash = h;
    }

}
