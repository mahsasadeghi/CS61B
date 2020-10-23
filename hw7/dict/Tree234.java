/* Tree234.java */

package dict;

/**
 *  A Tree234 implements an ordered integer dictionary ADT using a 2-3-4 tree.
 *  Only int keys are stored; no object is associated with each key.  Duplicate
 *  keys are not stored in the tree.
 *
 *  @author Jonathan Shewchuk
 **/
public class Tree234 extends IntDictionary {

  /**
   *  (inherited)  size is the number of keys in the dictionary.
   *  root is the root of the 2-3-4 tree.
   *
   *  You may add fields if you wish, but don't change anything that
   *  would prevent toString() or find() from working correctly.
   **/
  Tree234Node root;
  Tree234Node cur;

  public Tree234() {
    root = null;
    size = 0;
  }

  /**
   *  toString() prints this Tree234 as a String.  Each node is printed
   *  in the form such as (for a 3-key node)
   *
   *      (child1)key1(child2)key2(child3)key3(child4)
   *
   *  where each child is a recursive call to toString, and null children
   *  are printed as a space with no parentheses.  Here's an example.
   *      ((1)7(11 16)22(23)28(37 49))50((60)84(86 95 100))
   *
   *  DO NOT CHANGE THIS METHOD.
   *
   *  @return a String representation of the 2-3-4 tree.
   **/
  public String toString() {
    if (root == null) {
      return "";
    } else {
      return root.toString();
    }
  }

  /**
   *  printTree() prints this Tree234 as a tree, albeit sideways.
   *
   *  You're welcome to change this method if you like.  It won't be tested.
   **/
  public void printTree() {
    if (root != null) {
      root.printSubtree(0);
    }
  }

  /**
   *  find() prints true if "key" is in this 2-3-4 tree; false otherwise.
   *
   *  @param key is the key sought.
   *  @return true if "key" is in the tree; false otherwise.
   **/
  public boolean find(int key) {
    Tree234Node node = root;
    while (node != null) {
      if (key < node.key1) {
        node = node.child1;
      } else if (key == node.key1) {
        return true;
      } else if ((node.keys == 1) || (key < node.key2)) {
        node = node.child2;
      } else if (key == node.key2) {
        return true;
      } else if ((node.keys == 2) || (key < node.key3)) {
        node = node.child3;
      } else if (key == node.key3) {
        return true;
      } else {
        node = node.child4;
      }
    }
    return false;
  }

  /**
   *  insert() inserts the key "key" into this 2-3-4 tree.  If "key" is
   *  already present, a duplicate copy is NOT inserted.
   *
   *  @param key is the key sought.
   **/
  public void insert(int key){
      if(root ==  null) { root = new Tree234Node(null,key); size++; return; }
      cur = root;
      Tree234Node path;
      
      while(cur!=null){
          if(cur.keys == 3){
              cur = splitNode(cur);
          }
          
          if (key < cur.key1) {
            path = cur.child1;
          } else if ((cur.keys == 1) || (key < cur.key2)) {
            path = cur.child2;
          } else if ((cur.keys == 2) || (key < cur.key3)) {
            path = cur.child3;
          } else {
            path = cur.child4;
          }

          if(isLeaf(cur)){
              insertNonFull(cur,key);
              break;
          }
          else {
              cur = path;
          }
      }      
  }
  
  private Tree234Node splitNode(Tree234Node node){
      int mid = node.key2;
      Tree234Node n1 = new Tree234Node(null,node.key1);
      Tree234Node n2 = new Tree234Node(null,node.key3);
      if(node.equals(root)){
          root = new Tree234Node(null,mid);
          this.size++;
          root.child1 = n1;
          root.child2 = n2;
          n1.parent = root;
          n2.parent = root;
          node.parent = null;
          n1.child1 = node.child1;
          n1.child2 = node.child2;    
          n2.child1 = node.child3;   
          n2.child2 = node.child4;
          try{
          n1.child1.parent = n1;
          n1.child2.parent = n1;
          n2.child1.parent = n2;
          n2.child2.parent = n2;
          }catch(NullPointerException e){}
          node.child1 = null;
          node.child2 = null;
          node.child3 = null;
          node.child4 = null;
          return root;
      }
      
      if(node.parent.keys == 1){
          if(node.equals(node.parent.child1)){
              node.parent.child1 = n1;
              node.parent.child3 = node.parent.child2;
              node.parent.child2 = n2;
              node.parent.child4 = null;
              n1.child1 = node.child1;
              n1.child2 = node.child2;
              n2.child1 = node.child3;
              n2.child2 = node.child4;
              try{
              n1.child1.parent = n1;
              n1.child2.parent = n1;
              n2.child1.parent = n2;
              n2.child2.parent = n2;
              }catch(NullPointerException e){}
              n1.parent = node.parent;
              n2.parent = node.parent;
          }
          else {
              node.parent.child2 = n1;
              node.parent.child3 = n2;
              node.parent.child4 = null;
              n1.child1 = node.child1;
              n1.child2 = node.child2;
              n2.child1 = node.child3;
              n2.child2 = node.child4;
              try{
              n1.child1.parent = n1;
              n1.child2.parent = n1;
              n2.child1.parent = n2;
              n2.child2.parent = n2;
              }catch(NullPointerException e){}
              n1.parent = node.parent;
              n2.parent = node.parent;
          }
      }
      else if(node.parent.keys == 2){
          if(node.equals(node.parent.child1)){
              node.parent.child4 = node.parent.child3;
              node.parent.child3 = node.parent.child2;
              node.parent.child2 = n2;
              node.parent.child1 = n1;
              n1.child1 = node.child1;
              n1.child2 = node.child2;
              n2.child1 = node.child3;
              n2.child2 = node.child4;
              try{
              n1.child1.parent = n1;
              n1.child2.parent = n1;
              n2.child1.parent = n2;
              n2.child2.parent = n2;
              }catch(NullPointerException e){}
              n1.parent = node.parent;
              n2.parent = node.parent;
          }
          else if(node.equals(node.parent.child2)){
              node.parent.child4 = node.parent.child3;
              node.parent.child3 = n2;
              node.parent.child2 = n1;
              n1.child1 = node.child1;
              n1.child2 = node.child2;
              n2.child1 = node.child3;
              n2.child2 = node.child4;
              try{
              n1.child1.parent = n1;
              n1.child2.parent = n1;
              n2.child1.parent = n2;
              n2.child2.parent = n2;
              }catch(NullPointerException e){}
              n1.parent = node.parent;
              n2.parent = node.parent;
          }
          else {
              node.parent.child4 = n2;
              node.parent.child3 = n1;
              n1.child1 = node.child1;
              n1.child2 = node.child2;
              n2.child1 = node.child3;
              n2.child2 = node.child4;
              try{
              n1.child1.parent = n1;
              n1.child2.parent = n1;
              n2.child1.parent = n2;
              n2.child2.parent = n2;
              }catch(NullPointerException e){}
              n1.parent = node.parent;
              n2.parent = node.parent;
          }
      }
      insertNonFull(node.parent,mid);
      return node.parent;   
  }
      
  
  
  private void insertNonFull(Tree234Node node, int key){    
      if(node.keys == 1){
          if(key < node.key1){
              node.key2 = node.key1;
              node.key1 = key;
              node.keys+=1;
              node.child4 = null;
              size++;
          }else{
              node.key2 = key;
              node.keys+=1;
              node.child4 = null;
              size++;
          }
      }
      else if(node.keys == 2){
          if(key < node.key1){
              node.key3 = node.key2;
              node.key2 = node.key1;
              node.key1 = key;
              node.keys+=1;
              size++;
          }else if(key > node.key1 && key < node.key2){
              node.key3 = node.key2;
              node.key2 = key;
              node.keys+=1;
              size++;
          }else{
              node.key3 = key;
              node.keys+=1;
              size++;
          }
      }else if(node.keys > 2){ 
          System.out.println("Trying to insert " + key + " into " + node +" but node is FULL!");
          System.out.println("FATAL ERROR");
          System.exit(0);
      }
  }       
  
  private boolean isLeaf(Tree234Node node){
      if(node.child1 == null &&
              node.child2 == null &&
              node.child3 == null &&
              node.child4 == null)
          return true;
      else return false;
  }       

  /**
   *  testHelper() prints the String representation of this tree, then
   *  compares it with the expected String, and prints an error message if
   *  the two are not equal.
   *
   *  @param correctString is what the tree should look like.
   **/
  public void testHelper(String correctString) {
    String treeString = toString();
    System.out.println(treeString);
    if (!treeString.equals(correctString)) {
      System.out.println("ERROR:  Should be " + correctString);
    }
  }

  /**
   *  main() is a bunch of test code.  Feel free to add test code of your own;
   *  this code won't be tested or graded.
   **/
  public static void main(String[] args) {
    Tree234 t = new Tree234();

    System.out.println("\nInserting 84.");
    t.insert(84);
    t.testHelper("84");

    System.out.println("\nInserting 7.");
    t.insert(7);
    t.testHelper("7 84");

    System.out.println("\nInserting 22.");
    t.insert(22);
    t.testHelper("7 22 84");

    System.out.println("\nInserting 95.");
    t.insert(95);
    t.testHelper("(7)22(84 95)");

    System.out.println("\nInserting 50.");
    t.insert(50);
    t.testHelper("(7)22(50 84 95)");

    System.out.println("\nInserting 11.");
    t.insert(11);
    t.testHelper("(7 11)22(50 84 95)");

    System.out.println("\nInserting 37.");
    t.insert(37);
    t.testHelper("(7 11)22(37 50)84(95)");

    System.out.println("\nInserting 60.");
    t.insert(60);
    t.testHelper("(7 11)22(37 50 60)84(95)");

    System.out.println("\nInserting 1.");
    t.insert(1);
    t.testHelper("(1 7 11)22(37 50 60)84(95)");

    System.out.println("\nInserting 23.");
    t.insert(23);
    t.testHelper("(1 7 11)22(23 37)50(60)84(95)");

    System.out.println("\nInserting 16.");
    t.insert(16);
    t.testHelper("((1)7(11 16)22(23 37))50((60)84(95))");

    System.out.println("\nInserting 100.");
    t.insert(100);
    t.testHelper("((1)7(11 16)22(23 37))50((60)84(95 100))");

    System.out.println("\nInserting 28.");
    t.insert(28);
    t.testHelper("((1)7(11 16)22(23 28 37))50((60)84(95 100))");

    System.out.println("\nInserting 86.");
    t.insert(86);
    t.testHelper("((1)7(11 16)22(23 28 37))50((60)84(86 95 100))");

    System.out.println("\nInserting 49.");
    t.insert(49);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((60)84(86 95 100))");

    System.out.println("\nInserting 81.");
    t.insert(81);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((60 81)84(86 95 100))");

    System.out.println("\nInserting 51.");
    t.insert(51);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((51 60 81)84(86 95 100))");

    System.out.println("\nInserting 99.");
    t.insert(99);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((51 60 81)84(86)95(99 100))");

    System.out.println("\nInserting 75.");
    t.insert(75);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((51)60(75 81)84(86)95" +
                 "(99 100))");

    System.out.println("\nInserting 66.");
    t.insert(66);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((51)60(66 75 81))84((86)95" +
                 "(99 100))");

    System.out.println("\nInserting 4.");
    t.insert(4);
    t.testHelper("((1 4)7(11 16))22((23)28(37 49))50((51)60(66 75 81))84" +
                 "((86)95(99 100))");

    System.out.println("\nInserting 80.");
    t.insert(80);
    t.testHelper("(((1 4)7(11 16))22((23)28(37 49)))50(((51)60(66)75" +
                 "(80 81))84((86)95(99 100)))");

    System.out.println("\nFinal tree:");
    t.printTree();
  }

}
