import java.util.List;

import AbstractSkipList.SkipListNode;

import java.util.ArrayList;

public class MyDataStructure {
    /*
     * You may add any fields that you wish to add.
     * Remember that all the data-structures you use must be YOUR implementations,
     * except for the List and its implementation for the operation Range(low,
     * high).
     */

    /***
     * This function is the Init function described in Part 4.
     *
     * @param N The maximal number of items that may reside in the DS.
     */
    private IndexableSkipList myIndexableSkipList;
    private ChainedHashTable<Integer, Element> myChainedHashTable;

    public MyDataStructure(int N) {
        this.myIndexableSkipList = new IndexableSkipList(0.5);
        ModularHash hushFunction = new ModularHash();
        this.myChainedHashTable = new ChainedHashTable<>(hushFunction, N, 2);
    }

    public boolean insert(int value) {
        if (!(this.myChainedHashTable.search(value) == null)) {
            return false;
        }
        this.myIndexableSkipList.insert(value);
        AbstractSkipList.SkipListNode nodeToInsert = this.myIndexableSkipList.find(value);
        this.myChainedHashTable.insert(value, nodeToInsert);
        return true;
    }

    public boolean delete(int value) {
        Element elementToBeDeleted = this.myChainedHashTable.search(value);
        if (elementToBeDeleted == null) {
            return false;
        }
        AbstractSkipList.SkipListNode nodeToBeDeleted = this.myIndexableSkipList.find(value);
        this.myIndexableSkipList.delete(nodeToBeDeleted);
        this.myChainedHashTable.delete(value);
        return true;
    }

    public boolean contains(int value) {
        Element isElementExists = this.myChainedHashTable.search(value);
        if (isElementExists == null) {
            return false;
        }
        return true;

    }

    public int rank(int value) {
        return this.myIndexableSkipList.rank(value);
    }

    public int select(int index) {
        return Integer.MIN_VALUE;
    }

    public List<Integer> range(int low, int high) {
        return null;
    }
}
