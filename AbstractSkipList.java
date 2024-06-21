import java.util.NoSuchElementException;

import javax.swing.text.Style;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

abstract public class AbstractSkipList {
    final protected SkipListNode head;
    final protected SkipListNode tail;

    protected int size;

    public AbstractSkipList() {
        head = new SkipListNode(Integer.MIN_VALUE);
        tail = new SkipListNode(Integer.MAX_VALUE);
        size = 0;
        increaseHeight();
    }

    public void increaseHeight() {
        head.addLevel(tail, null, 0);
        tail.addLevel(null, head, this.size);
    }

    abstract SkipListNode find(int key);

    abstract public int generateHeight();

    public SkipListNode search(int key) {
        SkipListNode curr = find(key);

        return curr.key() == key ? curr : null;
    }

    public SkipListNode insert(int key) {
        int nodeHeight = generateHeight();
        this.size++;

        while (nodeHeight > head.height()) {
            increaseHeight();
        }

        SkipListNode prevNode = find(key);
        if (prevNode.key() == key) {
            this.size--;
            return null;
        }

        SkipListNode newNode = new SkipListNode(key);
        int prevCounter = 0;
        for (int level = 0; level <= nodeHeight && prevNode != null; ++level) {
            SkipListNode nextNode = prevNode.getNext(level);

            newNode.addLevel(nextNode, prevNode, prevCounter);
            prevNode.setNext(level, newNode);
            nextNode.setPrev(level, newNode);

            while (prevNode != null && prevNode.height() == level) {
                prevCounter = prevCounter + prevNode.skip_nodes.get(level) + 1;
                prevNode = prevNode.getPrev(level);
            }
        }
        SkipListNode curr = newNode.getNext(0);
        for (int level = 0; level <= head.height(); level++) {
            while (curr.height() < level) {
                curr = curr.getNext(level - 1);
            }
            if (level <= newNode.height()) {
                curr.skip_nodes.set(level, curr.skip_nodes.get(level) - newNode.skip_nodes.get(level));
            } else {
                curr.skip_nodes.set(level, curr.skip_nodes.get(level) + 1);
            }
        }

        return newNode;
    }

    public boolean delete(SkipListNode skipListNode) {
        this.size--;
        SkipListNode curr = skipListNode.getNext(0);
        for (int level = 0; level <= head.height(); level++) {
            while (curr.height() < level) {
                curr = curr.getNext(level - 1);
            }
            if (level <= skipListNode.height()) {
                curr.skip_nodes.set(level, curr.skip_nodes.get(level) + skipListNode.skip_nodes.get(level));
            } else {
                curr.skip_nodes.set(level, curr.skip_nodes.get(level) - 1);
            }
        }
        for (int level = 0; level <= skipListNode.height(); ++level) {
            SkipListNode prev = skipListNode.getPrev(level);
            SkipListNode next = skipListNode.getNext(level);
            prev.setNext(level, next);
            next.setPrev(level, prev);
        }

        return true;
    }

    public SkipListNode predecessor(SkipListNode skipListNode) {
        return skipListNode.getPrev(0);
    }

    public SkipListNode successor(SkipListNode skipListNode) {
        return skipListNode.getNext(0);
    }

    public SkipListNode minimum() {
        if (head.getNext(0) == tail) {
            throw new NoSuchElementException("Empty Linked-List");
        }

        return head.getNext(0);
    }

    public SkipListNode maximum() {
        if (tail.getPrev(0) == head) {
            throw new NoSuchElementException("Empty Linked-List");
        }

        return tail.getPrev(0);
    }

    private void levelToString(StringBuilder s, int level) {
        s.append("H    ");
        SkipListNode curr = head.getNext(0);

        while (curr != tail) {
            if (curr.height >= level) {
                s.append(curr.key());
                s.append("    ");
            } else {
                s.append("    ");
                for (int i = 0; i < curr.key().toString().length(); i = i + 1)
                    s.append(" ");
            }

            curr = curr.getNext(0);
        }

        s.append("T\n");
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for (int level = head.height(); level >= 0; --level) {
            levelToString(str, level);
        }

        return str.toString();
    }

    public static class SkipListNode extends Element<Integer, Object> {
        final private List<SkipListNode> next;
        final private List<SkipListNode> prev;
        private int height;
        protected ArrayList<Integer> skip_nodes;

        public SkipListNode(int key) {
            super(key);
            next = new ArrayList<>();
            prev = new ArrayList<>();
            this.height = -1;
            this.skip_nodes = new ArrayList<>();

        }

        public SkipListNode getPrev(int level) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            return prev.get(level);
        }

        public SkipListNode getNext(int level) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            return next.get(level);
        }

        public void setNext(int level, SkipListNode next) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            this.next.set(level, next);
        }

        public void setPrev(int level, SkipListNode prev) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            this.prev.set(level, prev);
        }

        public void addLevel(SkipListNode next, SkipListNode prev, int spans) {
            ++height;
            this.next.add(next);
            this.prev.add(prev);
            this.skip_nodes.add(spans);
        }

        public int height() {
            return height;
        }
    }
}
