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
        int counter = -1;
        SkipListNode curr = head;
        for (int level = head.height(); level >= 0; --level) {
            while (curr.getNext(level) != tail && curr.getNext(level).key() <= key) {
                curr = curr.getNext(level);
                counter = counter + curr.skip_nodes.get(level) + 1;
            }
        }

        return counter + 1;
    }

    public int select(int index) {
        int counter = 0;
        SkipListNode curr = head;
        for (int level = head.height(); level >= 0; --level) {
            while (curr.getNext(level) != tail && counter + curr.getNext(level).skip_nodes.get(level) + 1 <= index) {
                curr = curr.getNext(level);
                if (counter != index) {
                    counter = counter + curr.skip_nodes.get(level) + 1;
                }
            }
        }
        return curr.key();
    }

}
