import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
    public static class Input {
        public byte[] prevTxHash;
        public int outputIndex;
        public byte[] signature;

        public Input(byte[] prevTxHash, int outputIndex) {
            this.prevTxHash = prevTxHash;
            this.outputIndex = outputIndex;
        }
    }

    public static class Output {
        public double value;
        public PublicKey address;

        public Output(double value, PublicKey address) {
            this.value = value;
            this.address = address;
        }
    }

    private byte[] hash;
    private List<Input> inputs;
    private List<Output> outputs;

    public Transaction() {
        inputs = new ArrayList<>();
        outputs = new ArrayList<>();
    }

    public void addInput(byte[] prevTxHash, int outputIndex) {
        inputs.add(new Input(prevTxHash, outputIndex));
    }

    public void addOutput(double value, PublicKey address) {
        outputs.add(new Output(value, address));
    }

    public void addSignature(byte[] signature, int index) {
        inputs.get(index).signature = signature;
    }

    public void finalizeHash() {
        this.hash = computeHash();
    }

    public byte[] getHash() {
        return this.hash;
    }

    public List<Input> getInputs() {
        return this.inputs;
    }

    public List<Output> getOutputs() {
        return this.outputs;
    }

    public int numInputs() {
        return inputs.size();
    }

    public Input getInput(int index) {
        return inputs.get(index);
    }

    public int numOutputs() {
        return outputs.size();
    }

    public Output getOutput(int index) {
        return outputs.get(index);
    }

    public byte[] getRawDataToSign(int index) {
        return new byte[0]; // Placeholder
    }

    private byte[] computeHash() {
        return new byte[0]; // Placeholder
    }
}
