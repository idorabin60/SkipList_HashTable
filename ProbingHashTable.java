import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class ProbingHashTable<K, V> implements HashTable<K, V> {
    final static int DEFAULT_INIT_CAPACITY = 4;
    final static double DEFAULT_MAX_LOAD_FACTOR = 0.75;
    final private HashFactory<K> hashFactory;
    final private double maxLoadFactor;
    private int capacity;
    private HashFunctor<K> hashFunc;
    private Element<K, V>[] table;
    private double laodFactor;
    private int size;

    /*
     * You should add additional private fields as needed.
     */

    public ProbingHashTable(HashFactory<K> hashFactory) {
        this(hashFactory, DEFAULT_INIT_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    public ProbingHashTable(HashFactory<K> hashFactory, int k, double maxLoadFactor) {
        this.hashFactory = hashFactory;
        this.maxLoadFactor = maxLoadFactor;
        this.capacity = 1 << k;
        this.hashFunc = hashFactory.pickHash(k);
        this.table = new Element[capacity];
        this.laodFactor = 0;
        this.size = 0;

    }

    public V search(K key) {
        int elementIndex = this.hashFunc.hash(key);
        int initialIndex = elementIndex;
        while (true) {
            if (this.table[elementIndex].key() == key) {
                return this.table[elementIndex].satelliteData();
            }
            if (this.table[elementIndex] == null) {
                return null;
            } else {
                elementIndex = HashingUtils.mod(initialIndex + 1, this.capacity);
                if (elementIndex == initialIndex) {
                    return null;

                }
            }

        }

    }

    public void insert(K key, V value) {
        int newCapacity = this.capacity * 2;
        Element<K, V>[] newTable = new Element[newCapacity];
        HashFunctor<K> newHashFunction = hashFactory.pickHash(newCapacity);
        for (int i = 0; i < this.table.length; i++) {
            if (this.table[i] == null || this.table[i].key() == null) {
                continue;
            } else {
                int indexToBeInserted = newHashFunction.hash(this.table[i].key());
                while (true) {
                    if (newTable[indexToBeInserted] == null) {
                        newTable[indexToBeInserted] = this.table[i];
                        break;
                    } else {
                        indexToBeInserted = HashingUtils.mod(indexToBeInserted + 1, this.capacity);
                    }
                }
            }
        }
        this.capacity = newCapacity;
        this.table = newTable;
        this.hashFunc = newHashFunction;
        this.laodFactor = this.laodFactor / 2.0;

    }

    public void rehas() {

    }

    public boolean delete(K key) {
        throw new UnsupportedOperationException("Delete this line and replace it with your implementation");
    }

    public HashFunctor<K> getHashFunc() {
        return hashFunc;
    }

    public int capacity() {
        return capacity;
    }
}
