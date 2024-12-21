import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Transaction {
    private List<Input> inputs;
    private List<Output> outputs;
    private byte[] hash;

    public Transaction() {
        inputs = new ArrayList<>();
        outputs = new ArrayList<>();
    }

    public List<Input> getInputs() {
        return inputs;
    }

    public List<Output> getOutputs() {
        return outputs;
    }

    public void addInput(byte[] prevTxHash, int outputIndex) {
        inputs.add(new Input(prevTxHash, outputIndex));
    }

    public void addOutput(double value, Object address) {
        outputs.add(new Output(value, address));
    }

    public void finalize() {
        // Simplified hash computation for demonstration purposes
        this.hash = new byte[]{(byte) (Math.random() * 255)};
    }

    public byte[] getHash() {
        return hash;
    }

    public int numInputs() {
        return inputs.size();
    }

    public int numOutputs() {
        return outputs.size();
    }

    public Input getInput(int index) {
        return inputs.get(index);
    }

    public Output getOutput(int index) {
        return outputs.get(index);
    }

    /**
     * Builds raw data for the input index to sign.
     *
     * @param index the input index
     * @return the raw data for signing
     */
    public byte[] getRawDataToSign(int index) {
        Input input = inputs.get(index);
        List<Byte> rawData = new ArrayList<>();

        // Add the hash of the previous transaction
        if (input.prevTxHash != null) {
            for (byte b : input.prevTxHash) {
                rawData.add(b);
            }
        }

        // Add the output index
        for (byte b : intToBytes(input.outputIndex)) {
            rawData.add(b);
        }

        // Add all outputs
        for (Output output : outputs) {
            for (byte b : doubleToBytes(output.value)) {
                rawData.add(b);
            }
            if (output.address != null) {
                for (byte b : output.address.toString().getBytes()) {
                    rawData.add(b);
                }
            }
        }

        // Convert list to byte array
        byte[] rawArray = new byte[rawData.size()];
        for (int i = 0; i < rawData.size(); i++) {
            rawArray[i] = rawData.get(i);
        }

        return rawArray;
    }

    /**
     * Converts an int to a byte array.
     *
     * @param value the int value
     * @return the byte array
     */
    private byte[] intToBytes(int value) {
        return new byte[]{
                (byte) (value >> 24),
                (byte) (value >> 16),
                (byte) (value >> 8),
                (byte) value
        };
    }

    /**
     * Converts a double to a byte array.
     *
     * @param value the double value
     * @return the byte array
     */
    private byte[] doubleToBytes(double value) {
        long longBits = Double.doubleToLongBits(value);
        return new byte[]{
                (byte) (longBits >> 56),
                (byte) (longBits >> 48),
                (byte) (longBits >> 40),
                (byte) (longBits >> 32),
                (byte) (longBits >> 24),
                (byte) (longBits >> 16),
                (byte) (longBits >> 8),
                (byte) longBits
        };
    }

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
        public Object address;

        public Output(double value, Object address) {
            this.value = value;
            this.address = address;
        }
    }
}
