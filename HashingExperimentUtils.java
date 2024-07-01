import java.util.Collections;

public class HashingExperimentUtils {
    final private static int K = 16;

    public static double[] measureInsertionsProbing() {
        double[] loadFactors = { 0.5, 0.75, 0.875, 0.9375 };
        double[] insertionTimes = new double[4];

        for (int i = 0; i < 4; i++) {
            MultiplicativeShiftingHash hashFunction = new MultiplicativeShiftingHash();
            ProbingHashTable<Long, Long> hashTable = new ProbingHashTable<>(hashFunction, K, loadFactors[i]);
            HashingUtils utils = new HashingUtils();
            Long[] keys = utils.genUniqueLong((int) ((hashTable.capacity() / loadFactors[i]) - 1));
            double totalTime = 0.0;

            for (int j = 0; j < keys.length; j++) {
                long startTime = System.nanoTime();
                hashTable.insert(keys[j], keys[j]);
                long endTime = System.nanoTime();
                totalTime += (endTime - startTime);
            }
            insertionTimes[i] = totalTime / keys.length;
        }
        return insertionTimes;
    }

    public static double[] measureSearchesProbing() {
        double[] loadFactors = { 0.5, 0.75, 0.875, 0.9375 };
        double[] searchTimes = new double[4];

        for (int i = 0; i < 4; i++) {
            MultiplicativeShiftingHash hashFunction = new MultiplicativeShiftingHash();
            ProbingHashTable<Long, Long> hashTable = new ProbingHashTable<>(hashFunction, K, loadFactors[i]);
            HashingUtils utils = new HashingUtils();
            Long[] keys = utils.genUniqueLong((int) ((hashTable.capacity() / loadFactors[i] - 1) * 2));

            for (int j = 0; j < keys.length; j += 2) {
                hashTable.insert(keys[j], keys[j]);
            }

            double totalTime = 0.0;
            for (int n = 0; n < keys.length; n++) {
                long startTime = System.nanoTime();
                hashTable.search(keys[n]);
                long endTime = System.nanoTime();
                totalTime += (endTime - startTime);
            }
            searchTimes[i] = totalTime / keys.length;
        }
        return searchTimes;
    }

    public static double[] measureInsertionsChaining() {
        double[] loadFactors = { 0.5, 0.75, 1.0, 1.5, 2.0 };
        double[] avgInsertionTimes = new double[5];

        for (int index = 0; index < 5; index++) {
            MultiplicativeShiftingHash hashFunction = new MultiplicativeShiftingHash();
            ChainedHashTable<Long, Long> chainedHashTable = new ChainedHashTable<>(hashFunction, K,
                    loadFactors[index]);
            HashingUtils hashingUtils = new HashingUtils();
            Long[] uniqueKeys = hashingUtils
                    .genUniqueLong((int) ((chainedHashTable.capacity() / loadFactors[index]) - 1));

            double totalTime = 0.0;
            for (int keyIndex = 0; keyIndex < uniqueKeys.length; keyIndex++) {
                long startTime = System.nanoTime();
                chainedHashTable.insert(uniqueKeys[keyIndex], uniqueKeys[keyIndex]);
                long endTime = System.nanoTime();
                totalTime += (endTime - startTime);
            }
            avgInsertionTimes[index] = totalTime / uniqueKeys.length;
        }
        return avgInsertionTimes;
    }

    public static double[] measureSearchesChaining() {
        double[] loadFactors = { 0.5, 0.75, 1.0, 1.5, 2.0 };
        double[] searchTimes = new double[5];

        for (int i = 0; i < 5; i++) {
            MultiplicativeShiftingHash hashFunction = new MultiplicativeShiftingHash();
            ChainedHashTable<Long, Long> hashTable = new ChainedHashTable<>(hashFunction, K, loadFactors[i]);
            HashingUtils utils = new HashingUtils();
            Long[] keys = utils.genUniqueLong((int) ((hashTable.capacity() / loadFactors[i] - 1) * 2));

            for (int j = 0; j < keys.length; j += 2) {
                hashTable.insert(keys[j], keys[j]);
            }

            double totalTime = 0.0;
            for (int n = 0; n < keys.length; n++) {
                long startTime = System.nanoTime();
                hashTable.search(keys[n]);
                long endTime = System.nanoTime();
                totalTime += (endTime - startTime);
            }
            searchTimes[i] = totalTime / keys.length;
        }
        return searchTimes;
    }
}
