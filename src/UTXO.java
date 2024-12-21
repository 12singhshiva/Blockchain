import java.util.Objects;

public class UTXO {
    private final byte[] txHash;
    private final int index;

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
        if (this == o) return true;
        if (!(o instanceof UTXO)) return false;
        UTXO utxo = (UTXO) o;
        return index == utxo.index && Objects.equals(txHash, utxo.txHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(txHash, index);
    }
}
