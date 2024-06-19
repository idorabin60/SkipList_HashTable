public class main {
    public static void main(String[] args) {
        IndexableSkipList skipList = new IndexableSkipList(0.5);

        skipList.insert(1);
        skipList.insert(2);
        skipList.insert(3);
        skipList.insert(4);
        skipList.insert(5);

        System.out.println("Element at index 0: " + skipList.select(0)); // Should print 1
        System.out.println("Element at index 2: " + skipList.select(2)); // Should print 3
        System.out.println("Element at index 4: " + skipList.select(4)); // Should print 5
    }
}
