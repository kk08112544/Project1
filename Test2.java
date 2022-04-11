package blockchain;

// Test constructor and getTimestamp/getPrevHash in Block class
public class Test2 {
    public static void main(String[] args) {
        Block b1 = new Block(123456);
        System.out.println("Timestamp: "+b1.getTimestamp());
        System.out.println("Previous block's hash: "+b1.getPrevHash());
        Block b2 = new Block(39812, 982193125);
        System.out.println("Timestamp: "+b2.getTimestamp());
        System.out.println("Previous block's hash: "+b2.getPrevHash());
        Block b3 = new Block(123, 111111);
        System.out.println("Timestamp: "+b3.getTimestamp());
        System.out.println("Previous block's hash: "+b3.getPrevHash());
    }
    
}
