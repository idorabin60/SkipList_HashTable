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
        while (Math.random() < probability) {
            height++;
        }
        return height;
    }

    public int rank(int key) {
        throw new UnsupportedOperationException("Delete this line and replace it with your implementation");
    }

    public int select(int index) {
        int skips = 0;
        SkipListNode current = this.head;
        for (int level = this.head.height(); level >= 0; level--) {
            while (current.getNext(level) != null && current.getNext(level).key() <= index) {
                skips = skips + current.skip_nodes.get(level);
                current = current.getNext(level);
            }
        }
        return skips;

    }

}
