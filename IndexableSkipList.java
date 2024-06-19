public class IndexableSkipList extends AbstractSkipList {
    final protected double probability;

    public IndexableSkipList(double probability) {
        super();
        this.probability = probability;
    }

    @Override
    public SkipListNode find(int key) {
        SkipListNode current = this.head;
        for (int level = this.head.height(); level >= 0; level--) {
            while (current.getNext(level) != null && current.getNext(level).key() <= key) {
                current = current.getNext(level);
            }
        }
        return current;
    }

    @Override
    public int generateHeight() {
        int height = 0;
        while (Math.random() <= probability) {
            height++;
        }
        return height;
    }

    public int rank(int key) {
        System.out.println(this.size);
        SkipListNode current = this.head;
        int counter = 0;
        for (int level = this.head.height(); level >= 0; level--) {
            while (current.getNext(level) != null && current.getNext(level).key() <= key) {

                counter += current.skip_nodes.get(level) + 1;
                current = current.getNext(level);
            }
        }
        if (current.key() == key) {
            return counter;
        }
        System.out.println("ido");
        return counter + 1;

    }

    public int select(int index) {
        return 0;

    }

}
