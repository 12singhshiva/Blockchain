import java.util.Arrays;

public class UTXO {
    private byte[] txHash;
    private int index;

    public UTXO(byte[] txHash, int index) {
        this.txHash = txHash;
        this.index = index;
    }

    public byte[] getTxHash() {
        return txHash;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof UTXO)) return false;
        UTXO utxo = (UTXO) o;
        return Arrays.equals(txHash, utxo.txHash) && index == utxo.index;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(txHash) * 31 + index;
    }
}
