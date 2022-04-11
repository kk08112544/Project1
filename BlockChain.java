
package blockchain;

public class BlockChain {

    private Block[] blocks = new Block[100];// กำหนดขนาดarrayของblockที่100

    private int currentIndex = 0;// กำหนดค่าเริ่มต้นที่0

    public BlockChain() {

    }

    public void append(Block b) {// append คือการใส่ค่าลงในarray
        this.blocks[currentIndex] = b;// บอกค่าblock bใส่แทนที่ลงในarray;
        currentIndex++;// นับต่อไปเพื่อให้appendตัวต่อไปไม่ซ้ำตัวเดิมนับต่อไปก้ต่อเมื่อมีappend
    }

    public void modifyBlock(Block b, int idx) {// แก้ไขข้อมูลในarrayตำแหน่งนั้นๆ
        this.blocks[idx] = b;// แก้ไขattributeในตำแหน่งของindexแล้วเอาตัวที่แก้ไขใส่ลงไปในindexอีกครั้งนึง;

    }

    public int firstInvalidBlock() {// เป้าหมายหาblockแรกที่ไม่ถูกต้องในarray box การทำงานของมันคือเอาblock array
                                    // มาวนในfor
        // loop ใช้ในfunctionของmethod isvalid
        long currentPrevHash = 0;
        for (int i = 0; i < currentIndex; i++) {// มีไว้วนarray ของblock
            if (!(blocks[i].isValid())) {// เปนmethodในคลาสblock
                return i;// เอาblockไปตรวจสอบในloop
            } // ตรวจสอบความถูกต้องของบลอคไหมถ้าถูกมันข้ามถ้าผิดมันจะreturnตำแหน่งarrayที่ผิดกลับไป
            if (i == 0) {// ดูค่าprevhashบลอคแรกของarrayดุุว่าเปน0รึเปล่าถ้าไม่ใช่เปน0
                         // มันจะถือว่าบลอคนั้นไมาถูกต้อง และreturn ตำแหน่งของarrayที่มันอ่าน
                if (blocks[i].getPrevHash() != 0) {
                    return i;
                }
            } else {
                if (blocks[i].getPrevHash() != currentPrevHash) {// ดูprevhashของblockปัจจุบันตรงกับค่าhashของบลอคก่อนหน้ารึเปล่า
                    return i;
                }
            }
            currentPrevHash = blocks[i].getHash();// สร้างค่าcurrent
                                                  // prevhashมาเพื่อที่จะเอาไปเปรียบเทียบloopต่อไปในเงื่อนไขที่สาม
            // ถ้าarray ถูกต้องหมดจะreturnกลับเป็น-1
        } // ถ้าผืดreturn กลับเป็นเลขindexทีผิด
        return -1;
    }

    public boolean isTxInBlockchain(Transaction tx) {// เป้าหมายตรวจสอบtransactionว่ามีอยุ่ในblockของblockchainรึเปล่า
        if (firstInvalidBlock() != -1) {
            return false;// ตรวจก่อนว่าเป็นvalidถูกต้องรึเปล่า
        }
        for (int i = 0; i < currentIndex; i++) {// ใช้ในการเปรียบเทียบloopแรกวนเอาตัวblock
            int lenght = blocks[i].getTransactionLenght();// หาtransactionในblockมีอยู่เท่าไหร่
            for (int j = 0; j < lenght; j++) {// อีกloopนเอาตัวค่าtransactionในarrayของตัวบล๊อค
                Transaction currentTran = blocks[i].getTransaction(j);// ดึงtransactionปัจจุบันในloopออกมา
                // transaction
                // currentTranเลือกtransactionในblockมา1ตัวเพื่อที่จะเอามาเปรียบเทียบ
                if (tx.getReceiverId() == currentTran.getReceiverId() && tx.getSenderId() == currentTran.getSenderId()
                        && tx.getAmount() == currentTran.getAmount()) {// เปรียบเทียบtransactionในloopกับtransactionที่
                    // มาจากparameter
                    // ถ้าtransactionที่มาจากparameterตรงกัน3ตัว จะถือว่าอยู่ในblockchainนี้จะreturn
                    // true เปนปกติ
                    return true;
                }
            }

        }
        return false;// transactionไม่ได้อยู่ในblockchainนี้return false;
    }

}