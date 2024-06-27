import java.util.List;
import java.util.ArrayList;

public class MyDataStructure {
    /*
     * You may add any fields that you wish to add.
     * Remember that all the data structures you use must be YOUR implementations,
     * except for the List and its implementation for the operation Range(low,
     * high).
     */

    /***
     * This function is the Init function described in Part 4.
     *
     * @param N The maximal number of items that may reside in the DS.
     */
    private IndexableSkipList myIndexableSkipList;
    private ChainedHashTable<Integer, Element<Integer, AbstractSkipList.SkipListNode>> myChainedHashTable;

    public MyDataStructure(int N) {
        // Here we perform initialization on two data types, each takes Theta(N), hence
        // Theta(2N) is Theta(N).
        // Hash table worst and expected run times:
        // https://en.wikipedia.org/wiki/Hash_table
        // SkipList worst and expected run times:
        // https://en.wikipedia.org/wiki/Skip_list
        this.myIndexableSkipList = new IndexableSkipList(0.5);
        ModularHash hashFunction = new ModularHash();
        this.myChainedHashTable = new ChainedHashTable<>(hashFunction, N, 2);
    }

    public boolean insert(int value) {
        // Searching in hashTable is expected Theta(1) due to the way we chose our hash
        // function, more info here:
        // https://en.wikipedia.org/wiki/Hash_table
        if (!(this.myChainedHashTable.search(value) == null)) {
            return false;
        }
        // Insert in skipList is expected Theta(log n)
        this.myIndexableSkipList.insert(value);
        AbstractSkipList.SkipListNode nodeToInsert = this.myIndexableSkipList.find(value);
        // Insert in HashTable is expected Theta(1)
        Element<Integer, AbstractSkipList.SkipListNode> elementToInsert = new Element<>(value, nodeToInsert);
        this.myChainedHashTable.insert(value, elementToInsert);
        return true;
        // Hence total expected time is Theta(log n)
    }

    public boolean delete(int value) {
        // First we search to see if the element exists and we know that search in
        // HashTable
        // is Theta(1)
        Element<Integer, AbstractSkipList.SkipListNode> elementToBeDeleted = this.myChainedHashTable.search(value);
        if (elementToBeDeleted == null) {
            return false;
        }

        // If the element exists, delete it
        // First find the element that we want to delete in the SkipList in expected
        // Theta(log n)
        // We know that delete in SkipList is expected Theta(log n) and in HashTable
        // it's expected Theta(1), hence total time is Theta(log n)
        AbstractSkipList.SkipListNode nodeToBeDeleted = this.myIndexableSkipList.find(value);
        this.myIndexableSkipList.delete(nodeToBeDeleted);
        this.myChainedHashTable.delete(value);
        return true;
    }

    public boolean contains(int value) {
        // Search in expected Theta(1) in hashTable
        Element<Integer, AbstractSkipList.SkipListNode> isElementExists = this.myChainedHashTable.search(value);
        return isElementExists != null;
    }

    public int rank(int value) {
        // (Thanks to 2.4 section)
        // The rank operation in a Skip List has an expected time complexity of
        // Theta(log n).
        // By adding 'span' fields to nodes, the algorithm can efficiently traverse and
        // count
        // the number of elements less than the given value, maintaining Theta(log n)
        // complexity.
        return this.myIndexableSkipList.rank(value);
    }

    public int select(int index) {
        // (Thanks to 2.4 section)
        // The select operation in a Skip List has an expected time complexity of
        // Theta(log n).
        // To find the i-th element, the algorithm traverses from the top level
        // downwards,
        // performing a binary-like search which requires log n comparisons on average.
        // Additional 'span' or 'width' fields in nodes allow efficient skipping over
        // nodes,
        // maintaining the expected time complexity of Theta(log n).
        return this.myIndexableSkipList.select(index);
    }

    public List<Integer> range(int low, int high) {
        List<Integer> result = new ArrayList<>();
        Element<Integer, AbstractSkipList.SkipListNode> lowEl = this.myChainedHashTable.search(low);
        // If low does not exist in the DS, just return null
        if (lowEl == null) {
            return null;
        }

        AbstractSkipList.SkipListNode lowNode = lowEl.satelliteData();
        result.add(lowNode.key());
        AbstractSkipList.SkipListNode currNode = lowNode.getNext(0);
        // Because our satelliteData in the table is a pointer to the node, we search
        // for the element with the low value in the hashTable,
        // then we can go straight to the SkipList to get that node, then we go down to
        // the bottom level and go to the next value until we reach the high value.
        // Just a linear search for L elements, then the expected time will be Theta(L)
        while (currNode != null && currNode.key() <= high) {
            result.add(currNode.key());
            currNode = currNode.getNext(0);
        }
        return result;
    }
}
