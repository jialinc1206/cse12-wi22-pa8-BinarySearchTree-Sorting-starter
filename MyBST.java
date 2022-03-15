/**
 * Name: Jialin Chen
 * Email: jic053@ucsd.edu
 * Sources used: comments from:
 * https://docs.google.com/document/d/e/2PACX-1vT7pL-puGeW-r0YERAyPRqvHWbPLuVeVSYUjho64FKnyyQ99rYjwKRbfhloQXc7y9iDLaDdsBWGEs0f/pub#h.bz1b4mb6a8mf
 * 
 * this file represents a binary search tree with all methods
 * for a BST
 */
import java.util.ArrayList;


public class MyBST<K extends Comparable<K>,V>{
    MyBSTNode<K,V> root = null;
    int size = 0;

    public int size(){
        return size;
    }

    /**
     * Insert a new node containing the arguments key and value 
     * into the binary search tree according to the 
     * binary search tree properties. 
     * @param key insert node's key
     * @param value insert node's value
     * @return  inserted value
     */
    public V insert(K key, V value){
        if(key == null)
            throw new NullPointerException();

        if(this.root == null) 
            this.root = new MyBSTNode<K,V>(key, value, null);
        else {
            MyBSTNode<K,V> curr = this.root;
            while(curr != null) {
                // decide to place the new node on left or right
                // depending on it's key value in relation to curr's key
                if(key.compareTo(curr.getKey()) < 0) {
                    if(curr.getLeft() == null) {
                        curr.left = new MyBSTNode<K,V>(key, value, curr);
                        curr = null;
                    }
                    else
                        curr = curr.left;
                }
                else if(key.compareTo(curr.getKey()) > 0) {
                    if(curr.getRight() == null) {
                        curr.right = new MyBSTNode<K,V>(key, value, curr);
                        curr = null;
                    }
                    else
                        curr = curr.right;
                }
                // replace key's value if two key has the same value
                else {
                    V replace = curr.getValue();
                    curr.setValue(value);
                    return replace;
                }
            }
        }
        size++;
        return null;
    }

    /**
     * Search for a node with key equal to key and 
     * return the value associated with that node
     * @param key desired key
     * @return value of search key
     */
    public V search(K key){
        if(key == null)
            return null;

        MyBSTNode<K,V> curr = this.root;
        // search according to BST laws
        while(curr != null) {
            if(key.compareTo(curr.getKey()) == 0)
                return curr.getValue();
            else if(key.compareTo(curr.getKey()) < 0)
                curr = curr.getLeft();
            else
                curr = curr.getRight();
        }
        return null;
    }

    /**
     * Search for a node with key equal to key and 
     * return the value associated with that node.
     * @param key desired key
     * @return removed value
     */
    public V remove(K key){
        if(key == null)
            return null;

        MyBSTNode<K,V> curr = this.root;
        MyBSTNode<K,V> parent = null;
        V removeValue = null;
        while(curr != null) {
            if(curr.getKey().compareTo(key) == 0) {
                // remove leaf
                if(curr.getLeft() == null && curr.getRight() == null) {
                    if(parent == null) { // node is root
                        removeValue = parent.getValue();
                        this.root = null;
                    }
                    else if(parent.getLeft() == curr) {
                        removeValue = parent.getLeft().getValue();
                        parent.left = null;
                    }
                    else {
                        removeValue = parent.getRight().getValue();
                        parent.right = null;
                    }
                    this.size--;
                }
                // remove node with left child
                else if(curr.getRight() == null) {
                    if(parent == null) {
                        removeValue = this.root.getValue();
                        this.root = curr.getLeft();
                    }
                    else if(parent.getLeft() == curr) {
                        removeValue = parent.getLeft().getValue();
                        parent.left = curr.getLeft();
                        curr.getLeft().setParent(parent);
                    }
                    else {
                        removeValue = parent.getRight().getValue();
                        parent.right = curr.getLeft();
                        curr.getLeft().setParent(parent);
                    }
                    this.size--;
                }
                // remove node with right child
                else if(curr.getLeft() == null) {
                    if(parent == null) {
                        removeValue = this.root.getValue();
                        this.root = curr.getRight();
                    }
                    else if(parent.getLeft() == curr) {
                        removeValue = parent.getLeft().getValue();
                        parent.left = curr.getRight();
                        curr.getRight().setParent(parent);
                    }
                    else {
                        removeValue = parent.getRight().getValue();
                        parent.right = curr.getRight();
                        curr.getRight().setParent(parent);
                    }
                    this.size--;
                }
                // remove node with two children
                else {
                    MyBSTNode<K,V> successor = curr.successor();

                    K sucKey = successor.getKey();
                    V sucVal = successor.getValue();
                    remove(successor.getKey());
                    removeValue = curr.getValue();
                    curr.setKey(sucKey);
                    curr.setValue(sucVal);
                }
                return removeValue; // node found
            }
            else if(curr.getKey().compareTo(key) < 0) {
                parent = curr;
                curr = curr.getRight();
            }
            else {
                parent = curr;
                curr = curr.getLeft();
            }
        }
        return null; // node not found
    }
    
    /**
     * Do an in-order traversal of the tree, 
     * adding each node to the end of an ArrayList
     * @return all nodes in the tree sorted by the key order
     */
    public ArrayList<MyBSTNode<K, V>> inorder(){
        ArrayList<MyBSTNode<K, V>> nodeList = new ArrayList<MyBSTNode<K, V>>();
        if(size == 0)
            return nodeList;
        MyBSTNode<K,V> curr = this.root;
        // find the smallest node
        while(curr.predecessor() != null) {
            curr = curr.predecessor();
        }
        nodeList.add(curr);
        // keep adding the next greater node
        while(curr.successor() != null) {
            curr = curr.successor();
            nodeList.add(curr);
        }
        return nodeList;
    }

    /**
     * This class is a static nested class of the MyBST class. 
     * Objects of this class represent the nodes of the binary search tree
     */
    static class MyBSTNode<K,V>{
        private static final String TEMPLATE = "Key: %s, Value: %s";
        private static final String NULL_STR = "null";

        private K key;
        private V value;
        private MyBSTNode<K,V> parent;
        private MyBSTNode<K,V> left = null;
        private MyBSTNode<K,V> right = null;

        /**
         * Creates a MyBSTNode<K,V> storing specified data
         * @param key the key the MyBSTNode<K,V> will
         * @param value the data the MyBSTNode<K,V> will store
         * @param parent the parent of this node
         */
        public MyBSTNode(K key, V value, MyBSTNode<K, V> parent){
            this.key = key;
            this.value = value;
            this.parent = parent; 
        }

        /**
         * Return the key stored in the the MyBSTNode<K,V>
         * @return the key stored in the MyBSTNode<K,V>
         */
        public K getKey(){
            return key;
        }

        /**
         * Return data stored in the MyBSTNode<K,V>
         * @return the data stored in the MyBSTNode<K,V>
         */
        public V getValue(){
            return value;
        }

        /**
         * Return the parent
         * @return the parent
         */
        public MyBSTNode<K,V> getParent(){
            return parent;
        }

        /**
         * Return the left child 
         * @return left child
         */
        public MyBSTNode<K,V> getLeft(){
            return left;
        }

        /**
         * Return the right child 
         * @return right child
         */
        public MyBSTNode<K,V> getRight(){
            return right;
        }

        /**
         * Set the key stored in the MyBSTNode<K,V>
         * @param newKey the key to be stored
         */
        public void setKey(K newKey){
            this.key = newKey;
        }

        /**
         * Set the data stored in the MyBSTNode<K,V>
         * @param newValue the data to be stored
         */
        public void setValue(V newValue){
            this.value = newValue;
        }

        /**
         * Set the parent
         * @param newParent the parent
         */
        public void setParent(MyBSTNode<K,V> newParent){
            this.parent = newParent;
        }

        /**
         * Set the left child
         * @param newLeft the new left child
         */
        public void setLeft(MyBSTNode<K,V> newLeft){
            this.left = newLeft;
        }

        /**
         * Set the right child
         * @param newRight the new right child
         */
        public void setRight(MyBSTNode<K,V> newRight){
            this.right = newRight;
        }

        /**
         * TODO: add inline comments for this method to demonstrate your
         *   understanding of this method. The predecessor can be implemented
         *   in a similar way.
         *
         * This method returns the in order successor of current node object.
         * It can be served as a helper method when implementing inorder().
         * @return the successor of current node object
         */
        public MyBSTNode<K, V> successor(){
            // if the current node has a right child
            // find the smallest child on the right side
            if(this.getRight() != null){
                MyBSTNode<K,V> curr = this.getRight();
                // by keep locating the left child on the right
                while(curr.getLeft() != null){
                    curr = curr.getLeft();
                }
                return curr;
            }
            // if current node doesn't have a right child
            // find the first parent that's greater than the current node
            else{
                MyBSTNode<K,V> parent = this.getParent();
                MyBSTNode<K,V> curr = this;
                // by locating when curr is a left child for the first time
                while(parent != null && curr == parent.getRight()){
                    curr = parent;
                    parent = parent.getParent();
                }
                return parent;
            }
        }

        /**
         * returns the node with the greatest key that is 
         * smaller than the key of this node.
         * @return the predecessor of the current node
         */
        public MyBSTNode<K, V> predecessor(){
            // if the current node has a left child
            // find the greatset child on the left side
            if(this.getLeft() != null){
                MyBSTNode<K,V> curr = this.getLeft();
                // by keep locating the right child on the left
                while(curr.getRight() != null){
                    curr = curr.getRight();
                }
                return curr;
            }
            // if current node doesn't have a left child
            // find the first parent that's smaller than the current node
            else{
                MyBSTNode<K,V> parent = this.getParent();
                MyBSTNode<K,V> curr = this;
                // by locating when curr is a left child for the first time
                while(parent != null && curr == parent.getLeft()){
                    curr = parent;
                    parent = parent.getParent();
                }
                if(parent == null)
                    return null;
                return parent;
            }
        }

        /** This method compares if two node objects are equal.
         * @param obj The target object that the currect object compares to.
         * @return Boolean value indicates if two node objects are equal
         */
        public boolean equals(Object obj){
            if (!(obj instanceof MyBSTNode))
                return false;

            MyBSTNode<K,V> comp = (MyBSTNode<K,V>)obj;
            
            return( (this.getKey() == null ? comp.getKey() == null : 
                this.getKey().equals(comp.getKey())) 
                && (this.getValue() == null ? comp.getValue() == null : 
                this.getValue().equals(comp.getValue())));
        }

        /**
         * This method gives a string representation of node object.
         * @return "Key:Value" that represents the node object
         */
        public String toString(){
            return String.format(
                    TEMPLATE,
                    this.getKey() == null ? NULL_STR : this.getKey(),
                    this.getValue() == null ? NULL_STR : this.getValue());
        }
    }
}