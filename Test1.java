package blockchain;

// Test Transaction class
public class Test1 {
    
    public static void main(String[] args) {
        Transaction tx = new Transaction(123456, 666123, 5);
        System.out.println("Transaction1 from "+tx.getSenderId()+" to "+tx.getReceiverId()+" amount "+tx.getAmount());
        tx = new Transaction(1601231, 60392, 2);
        System.out.println("Transaction2 from "+tx.getSenderId()+" to "+tx.getReceiverId()+" amount "+tx.getAmount());
    }
}
