/**
 * Name: Jialin Chen
 * Email: jic053@ucsd.edu
 * Sources used: comments from:
 * https://docs.google.com/document/d/e/2PACX-1vT7pL-puGeW-r0YERAyPRqvHWbPLuVeVSYUjho64FKnyyQ99rYjwKRbfhloQXc7y9iDLaDdsBWGEs0f/pub#h.bz1b4mb6a8mf
 * 
 * this is a iterator file that points to the next node
 * for MyBST and stores last visited node
 */
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyBSTIterator<K extends Comparable<K>, V> extends MyBST<K, V> {
    abstract class MyBSTNodeIterator<T> implements Iterator<T> {
        MyBSTNode<K, V> next;
        MyBSTNode<K, V> lastVisited;

        /**
         * Constructor that initializes the node iterator
         *
         * @param first The initial node that next points
         */
        MyBSTNodeIterator(MyBSTNode<K, V> first) {
            next = first;
            lastVisited = null;
        }

        /**
         * This method is used for determining if the next pointer in the
         * iterator points to null.
         *
         * @return If next is null based on the current position of iterator
         */
        public boolean hasNext() {
            return next != null;
        }

        MyBSTNode<K, V> nextNode() {
            if(!hasNext())
                throw new NoSuchElementException();

            this.lastVisited = this.next;
            this.next = this.next.successor();

            return this.lastVisited;
        }

        /**
         * TODO: add inline comments for this method to demonstrate your
         *   understanding of this method.
         *
         * This method removes the last visited node from the tree.
         */
        public void remove() {
            // can't remove last visited for the first node 
            // or right after remove()
            if (lastVisited == null) {
                throw new IllegalStateException();
            }
            // if last visited has both children, 
            // let next point to the last visited node
            if (lastVisited.getRight() != null &&
                    lastVisited.getLeft() != null) {
                next = lastVisited;
            }
            // remove last visited node
            MyBSTIterator.this.remove(lastVisited.getKey());
            // let last visted node points to null
            lastVisited = null;
        }
    }

    /**
     * BST Key iterator class that extends the node iterator.
     */
    class MyBSTKeyIterator extends MyBSTNodeIterator<K> {

        MyBSTKeyIterator(MyBSTNode<K, V> first) {
            super(first);
        }

        /**
         * This method advance the iterator and returns a node key
         *
         * @return K the next key
         */
        public K next() {
            return super.nextNode().getKey();
        }
    }

    /**
     * BST value iterator class that extends the node iterator.
     */
    class MyBSTValueIterator extends MyBSTNodeIterator<V> {

        /**
         * Call the constructor method from node iterator
         *
         * @param first The initial value that next points
         */
        MyBSTValueIterator(MyBSTNode<K, V> first) {
            super(first);
        }

        /**
         * This method advance the iterator and returns a node value
         *
         * @return V the next value
         */
        public V next() {
            return super.nextNode().getValue();
        }
    }

    /**
     * This method is used to obtain an iterator that iterates through the
     * value of BST.
     *
     * @return The value iterator of BST.
     */
    public MyBSTKeyIterator getKeyIterator() {
        MyBSTNode<K, V> curr = root;
        if (curr != null) {
            while (curr.getLeft() != null) {
                curr = curr.getLeft();
            }
        }
        return new MyBSTKeyIterator(curr);
    }

    /**
     * This method is used to obtain an iterator that iterates through the
     * value of BST.
     *
     * @return The value iterator of BST.
     */
    public MyBSTValueIterator getValueIterator() {
        MyBSTNode<K, V> curr = root;
        if (curr != null) {
            while (curr.getLeft() != null) {
                curr = curr.getLeft();
            }
        }
        return new MyBSTValueIterator(curr);
    }
}