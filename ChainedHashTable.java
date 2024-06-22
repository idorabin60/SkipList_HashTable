import java.util.List;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;

public class ChainedHashTable<K, V> implements HashTable<K, V> {
    final static int DEFAULT_INIT_CAPACITY = 4;
    final static double DEFAULT_MAX_LOAD_FACTOR = 2;
    final private HashFactory<K> hashFactory;
    final private double maxLoadFactor;
    private int capacity;
    private HashFunctor<K> hashFunc;
    private List<Element<K, V>>[] table;
    private int currentLoadFactor;
    private int size;

    /*
     * You should add additional private fields as needed.
     */

    public ChainedHashTable(HashFactory<K> hashFactory) {
        this(hashFactory, DEFAULT_INIT_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    public ChainedHashTable(HashFactory<K> hashFactory, int k, double maxLoadFactor) {
        this.hashFactory = hashFactory;
        this.maxLoadFactor = maxLoadFactor;
        this.capacity = 1 << k;
        this.hashFunc = hashFactory.pickHash(k);
        this.table = new List[this.capacity];
        this.currentLoadFactor = 0;
        this.size = 0;

    }

    public V search(K key) {
        int listIndex = this.hashFunc.hash(key);
        List<Element<K, V>> desiredList = this.table[listIndex];

        if (desiredList != null) {
            Iterator<Element<K, V>> iterator = desiredList.iterator();
            while (iterator.hasNext()) {
                Element<K, V> element = iterator.next();
                if (element.key().equals(key)) {
                    return element.satelliteData();
                }
            }
        }
        return null;
    }

    public void insert(K key, V value) {
        if (this.currentLoadFactor >= maxLoadFactor) {
            int newCapacity = this.capacity * 2;
            HashFunctor<K> newHashFunction = hashFactory.pickHash(newCapacity);
            List<Element<K, V>>[] newTable = new List[newCapacity];
            for (int i = 0; i < this.table.length; i++) {
                Iterator<Element<K, V>> iterator = this.table[i].iterator();
                while (iterator.hasNext()) {
                    Element<K, V> element = iterator.next();
                    int elementIndex = newHashFunction.hash(element.key());
                    newTable[elementIndex].add(element);
                }
            }
            this.currentLoadFactor = this.currentLoadFactor / 2;
            this.capacity = newCapacity;
            this.table = newTable;
            this.hashFunc = newHashFunction;
        }
        Element<K, V> elmentToBeInserted = new Element<>(key, value);
        int hashTableIndex = this.hashFunc.hash(elmentToBeInserted.key());
        this.table[hashTableIndex].add(elmentToBeInserted);
        this.size++;
        this.currentLoadFactor = this.size / this.capacity;
    }

    public boolean delete(K key) {
        boolean answer = false;
        int listIndex = this.hashFunc.hash(key);
        List<Element<K, V>> desiredList = this.table[listIndex];

        if (desiredList != null) {
            Iterator<Element<K, V>> iterator = desiredList.iterator();
            while (iterator.hasNext()) {
                Element<K, V> element = iterator.next();
                if (element.key().equals(key)) {
                    desiredList.remove(element);
                    this.size--;
                    this.currentLoadFactor = this.size / this.capacity;
                    return true;
                }
            }
        }
        return answer;

    }

    public HashFunctor<K> getHashFunc() {
        return hashFunc;
    }

    public int capacity() {
        return capacity;
    }
}
