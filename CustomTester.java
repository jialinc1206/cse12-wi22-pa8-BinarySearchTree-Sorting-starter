/**
 * Name: Jialin Chen
 * Email: jic053@ucsd.edu
 * Sources used: none
 * 
 * this file contains custom tests for my 
 * implementation of the code 
 */

import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;

public class CustomTester {
    MyBST<Integer, Integer> Tree;

    /**
     * The setup method create a complete tree with height three
     */
    @Before
    public void setup(){

        MyBST.MyBSTNode<Integer, Integer> root = 
            new MyBST.MyBSTNode(4, 40, null);
        MyBST.MyBSTNode<Integer, Integer> two = 
            new MyBST.MyBSTNode(2, 20, root);
        MyBST.MyBSTNode<Integer, Integer> six = 
            new MyBST.MyBSTNode(6, 60, root);
        MyBST.MyBSTNode<Integer, Integer> one = 
            new MyBST.MyBSTNode(1, 10, two);
        MyBST.MyBSTNode<Integer, Integer> three = 
            new MyBST.MyBSTNode(3, 30, two);
        MyBST.MyBSTNode<Integer, Integer> five = 
            new MyBST.MyBSTNode(5, 50, six);

        this.Tree = new MyBST();
        this.Tree.root = root;
        root.setLeft(two);
        root.setRight(six);
        two.setLeft(one);
        two.setRight(three);
        six.setLeft(five);
        this.Tree.size = 6;
    }

    // test insert when key is null
    @Test
    public void testInsert1() {
        MyBST.MyBSTNode<Integer, Integer> root = Tree.root;
        try {
            Tree.insert(null, 1);
            fail();
        } catch (Exception NoSuchElementException) {
            //exception caught
        }
    }

    // test insert with non-existing key
    @Test
    public void testInsert2() {
        MyBST.MyBSTNode<Integer, Integer> root = Tree.root;
        assertNull(Tree.insert(7, 70));
        assertEquals((Integer)7, root.getRight().getRight().getKey());
    }

    // test insert when a value is replaced
    @Test
    public void testInsert3() {
        MyBST.MyBSTNode<Integer, Integer> root = Tree.root;
        assertEquals((Integer)100, Tree.insert(3, 100));
        assertEquals((Integer)100, root.getLeft().getRight().getValue());
    }

    // test search when key is null
    @Test
    public void testSearch() {
        assertNull(Tree.search(null));    
    }

    // test remove when key is null
    @Test
    public void testRemove1() {
        assertNull(Tree.remove(null));
    }

    // test remove when node is removed
    @Test
    public void testRemove2() {
        MyBST.MyBSTNode<Integer, Integer> root = Tree.root;
        assertEquals((Integer)40, Tree.remove(4));
        assertEquals((Integer)5, Tree.root.getKey());
    }

    // test nextNode when hasNext is false
    @Test
    public void testNextNode() {
        MyBSTIterator<Integer, Integer> iterTree = new MyBSTIterator(); 
        MyBSTIterator<Integer, Integer>.MyBSTValueIterator vi = 
        iterTree.new MyBSTValueIterator(null);
        try {
            vi.nextNode();
            fail();
        } catch (Exception NoSuchElementException) {
            // exception caught
        }
    }

    // test book when start time < 0
    @Test
    public void testBook1() {
        MyCalendar cal = new MyCalendar();
        try {
            cal.book(-1, 10);
            fail();
        } catch (Exception IllegalArgumentException) {
            // exception caught
        }    
    }

    // test book when start time = end time
    @Test
    public void testBook2() {
        MyCalendar cal = new MyCalendar();
        try {
            cal.book(10, 10);
            fail();
        } catch (Exception IllegalArgumentException) {
            // exception caught
        }      }



    // test inorder for an empty tree
    @Test
    public void testInOrder() {
        MyBST.MyBSTNode<Integer, Integer> root = Tree.root;
        ArrayList<MyBST.MyBSTNode<Integer, Integer>> expectedRes 
            = new ArrayList<>();

        MyBST<Integer, Integer> emptyTree = new MyBST<>();
        
        assertEquals(expectedRes, emptyTree.inorder());
    }
}
