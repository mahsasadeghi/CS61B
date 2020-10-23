/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package list;

/**
 *
 * @author jasontosh
 */
public class LockDList extends DList {
    
    LockDList(){
        super();
    }
    
  
  public static void lockNode(DListNode node){
      ((LockDListNode)node).LOCKED = true;
  }
  
  public void remove(LockDListNode node) {
          if(node.LOCKED == true){
          }
          else {
              if(node != null){
                  node.prev.next = node.next;
                  node.next.prev = node.prev;
                  size--;
              }else {
              }
          }
  }


  public static void main(String[] args){
      // Testing LockDList and LockDListNode

    System.out.println("");
    System.out.println("Create a new LockDList");
    LockDList h = new LockDList();
    ((LockDList)h).insertFront(1);
    ((LockDList)h).insertBack(2);
    ((LockDList)h).insertBack(3);
    System.out.println(h);
    System.out.println("Is H a LockDList?...");
    System.out.println((h instanceof LockDList));
    LockDListNode d = new LockDListNode(h.head.next,h.head.next.prev,h.head.next.next);
    System.out.println("Set d to the first element of list. Is d a LockDListNode?");
    System.out.println((d instanceof LockDListNode));
    System.out.println("Locking d...");
    lockNode(d);
    System.out.println("Attempting to remove d...");
    h.remove(d);
    System.out.println("List after attempted removal");
    System.out.println(h);
  }

}