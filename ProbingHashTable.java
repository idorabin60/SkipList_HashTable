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
    private double loadFactor;
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
        this.loadFactor = 0;
        this.size = 0;
    }

    public V search(K key) {
        int elementIndex = Math.abs(this.hashFunc.hash(key)) % this.capacity;
        int initialIndex = elementIndex;
        while (true) {
            if (this.table[elementIndex] == null) {
                return null;
            }
            if (this.table[elementIndex].key() != null && this.table[elementIndex].key().equals(key)) {
                return this.table[elementIndex].satelliteData();
            } else {
                elementIndex = HashingUtils.mod(elementIndex + 1, this.capacity);
                if (elementIndex == initialIndex) {
                    return null;
                }
            }
        }
    }

    public Element<K, V> searchForDelete(K key) {
        int elementIndex = Math.abs(this.hashFunc.hash(key)) % this.capacity;
        int initialIndex = elementIndex;
        while (true) {
            if (this.table[elementIndex] == null) {
                return null;
            }
            if (this.table[elementIndex].key() != null && this.table[elementIndex].key().equals(key)) {
                return this.table[elementIndex];
            } else {
                elementIndex = HashingUtils.mod(elementIndex + 1, this.capacity);
                if (elementIndex == initialIndex) {
                    return null;
                }
            }
        }
    }

    public void insert(K key, V value) {
        if (this.loadFactor >= maxLoadFactor) {
            this.rehash();
        }
        Element<K, V> elementToBeInserted = new Element<>(key, value);
        int indexToBeInserted = Math.abs(this.hashFunc.hash(key)) % this.capacity;
        while (true) {
            if (this.table[indexToBeInserted] == null || this.table[indexToBeInserted].key() == null) {
                this.table[indexToBeInserted] = elementToBeInserted;
                break;
            } else {
                indexToBeInserted = HashingUtils.mod(indexToBeInserted + 1, this.capacity);
            }
        }
        this.size++;
        this.loadFactor = (double) this.size / this.capacity;
    }

    public void rehash() {
        int newCapacity = this.capacity * 2;
        Element<K, V>[] newTable = new Element[newCapacity];
        int newK = (int) (Math.log(newCapacity) / Math.log(2));
        HashFunctor<K> newHashFunction = hashFactory.pickHash(newK);
        for (int i = 0; i < this.table.length; i++) {
            if (this.table[i] == null || this.table[i].key() == null) {
                continue;
            } else {
                int indexToBeInserted = Math.abs(newHashFunction.hash(this.table[i].key())) % newCapacity;
                while (true) {
                    if (newTable[indexToBeInserted] == null) {
                        newTable[indexToBeInserted] = this.table[i];
                        break;
                    } else {
                        indexToBeInserted = HashingUtils.mod(indexToBeInserted + 1, newCapacity);
                    }
                }
            }
        }
        this.capacity = newCapacity;
        this.table = newTable;
        this.hashFunc = newHashFunction;
        this.loadFactor = (double) this.size / this.capacity;
    }

    public boolean delete(K key) {
        Element<K, V> elementToDelete = searchForDelete(key);
        if (elementToDelete != null) {
            elementToDelete.setKey(null);
            this.size--;
            this.loadFactor = (double) this.size / this.capacity;
            return true;
        }
        return false;
    }

    public HashFunctor<K> getHashFunc() {
        return hashFunc;
    }

    public int capacity() {
        return capacity;
    }
}
