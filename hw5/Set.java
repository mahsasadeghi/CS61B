/* Set.java */

import list.*;

/**
 *  A Set is a collection of Comparable elements stored in sorted order.
 *  Duplicate elements are not permitted in a Set.
 **/
public class Set {
  /* Fill in the data fields here. */
    protected List set;
    protected int $elements;

  /**
   * Set ADT invariants:
   *  1)  The Set's elements must be precisely the elements of the List.
   *  2)  The List must always contain Comparable elements, and those elements 
   *      must always be sorted in ascending order.
   *  3)  No two elements in the List may be equal according to compareTo().
   **/

  /**
   *  Constructs an empty Set. 
   *
   *  Performance:  runs in O(1) time.
   **/
  public Set() { 
    set = null;
    $elements = 0;
  }

  /**
   *  cardinality() returns the number of elements in this Set.
   *
   *  Performance:  runs in O(1) time.
   **/
  public int cardinality() {
    return this.$elements;
  }

  /**
   *  insert() inserts a Comparable element into this Set.
   *
   *  Sets are maintained in sorted order.  The ordering is specified by the
   *  compareTo() method of the java.lang.Comparable interface.
   *
   *  Performance:  runs in O(this.cardinality()) time.
   **/
  public void insert(Comparable c) {
      
      boolean loopFlag = false;
      boolean duplFlag = false;
      
      if(this.set == null){
          set = new DList();
          this.set.insertFront(c);
          this.$elements++;
          
      }else{
          
          ListNode element = set.front();
          
          try{
              
              // This if statement means there is only one element in the list.
              if((element.equals(this.set.back()))){ 
              
                  if(((Comparable)(element.item())).compareTo(c) < 0){
                      element.insertAfter(c);
                      this.$elements++;
                  }else if(((Comparable)(element.item())).compareTo(c) > 0){
                      element.insertBefore(c);
                      this.$elements++;
                  }else if(((Comparable)(element.item())).compareTo(c) == 0){}
                      
              }else {
                  // So if we reach this part of the statement, there must be more
                  // than one element in the list. We will traverse through the list
                  // until we find an element that is greater than the item
                  // we are trying to add. When we find that item, we will set
                  // the flag to true and insertBefore that element. If we reach
                  // the end of the list without finding a greater element, we will
                  // insert the element at the end.
                  
                  while(!(loopFlag || duplFlag)){
                      
                      if(((Comparable)(element.item())).compareTo(c) < 0){
                          
                          if((element.equals(this.set.back()))){
                              element.insertAfter(c);
                              this.$elements++;
                          }else
                              element = element.next();
                      }
                      else if(((Comparable)(element.item())).compareTo(c) > 0)
                          loopFlag = true;
                      else if(((Comparable)(element.item())).compareTo(c) == 0)
                          duplFlag = true;
                  }
              }
              
              if(loopFlag){
                  element.insertBefore(c);
                  this.$elements++;
              }
                      
              
          }catch(InvalidNodeException e1){}
      }
     
  }

  /**
   *  union() modifies this Set so that it contains all the elements it
   *  started with, plus all the elements of s.  The Set s is NOT modified.
   *  Make sure that duplicate elements are not created.
   *
   *  Performance:  Must run in O(this.cardinality() + s.cardinality()) time.
   *
   *  Your implementation should NOT copy elements of s or "this", though it
   *  will copy _references_ to the elements of s.  Your implementation will
   *  create new _nodes_ for the elements of s that are added to "this", but
   *  you should reuse the nodes that are already part of "this".
   *
   *  DO NOT MODIFY THE SET s.
   *  DO NOT ATTEMPT TO COPY ELEMENTS; just copy _references_ to them.
   **/
  public void union(Set s) {
      Set temp = new Set();
      temp.set = new DList();
      ListNode r1 = this.set.front();
      ListNode r2 = s.set.front();
      
      try{
           
          
          for(int i =0; i<this.cardinality(); i++){
              temp.set.insertBack(r1.item());
              r1 = r1.next();  
              temp.$elements++;
          }
          
          r1 = temp.set.front();
          
          for(int i =0; i<s.cardinality(); i++){
              temp.insert((Comparable)r2.item());
              r2 = r2.next();
          }

      
      }catch(InvalidNodeException e1){}
      
      this.set = temp.set;
      this.$elements = temp.$elements;
          
          
      
      
    
  }

  /**
   *  intersect() modifies this Set so that it contains the intersection of
   *  its own elements and the elements of s.  The Set s is NOT modified.
   *
   *  Performance:  Must run in O(this.cardinality() + s.cardinality()) time.
   *
   *  Do not construct any new ListNodes during the execution of intersect.
   *  Reuse the nodes of "this" that will be in the intersection.
   *
   *  DO NOT MODIFY THE SET s.
   *  DO NOT CONSTRUCT ANY NEW NODES.
   *  DO NOT ATTEMPT TO COPY ELEMENTS.
   **/
  public void intersect(Set s) {
      ListNode r1 = this.set.front();
      ListNode r2 = s.set.front();
      ListNode t1;
      boolean loopFlag = false;
      
      try{
          
          
          while(!loopFlag){
              
              if(((Comparable)(r1.item())).compareTo(r2.item()) != 0){
                  
                  if(r1.next().isValidNode())
                      t1 = r1.next();
                  else {
                      t1 = r1;
                      loopFlag = true;
                  }
                  
                  r1.remove();
                  this.$elements--;
                  r1=t1;
                  
                  if(loopFlag)
                      break;
              }
              else{
                  r1=r1.next();
                  r2=r2.next();
              }
          }
                  
      
      }catch(InvalidNodeException e1){}
  }

  /**
   *  toString() returns a String representation of this Set.  The String must
   *  have the following format:
   *    {  } for an empty Set.  No spaces before "{" or after "}"; two spaces
   *            between them.
   *    {  1  2  3  } for a Set of three Integer elements.  No spaces before
   *            "{" or after "}"; two spaces before and after each element.
   *            Elements are printed with their own toString method, whatever
   *            that may be.  The elements must appear in sorted order, from
   *            lowest to highest according to the compareTo() method.
   *
   *  WARNING:  THE AUTOGRADER EXPECTS YOU TO PRINT SETS IN _EXACTLY_ THIS
   *            FORMAT, RIGHT UP TO THE TWO SPACES BETWEEN ELEMENTS.  ANY
   *            DEVIATIONS WILL LOSE POINTS.
   **/
  public String toString() {
      if (this.set == null)
          return "{  }";
      else
          return set.toString();
          
  }

  public static void main(String[] argv) {
    Set s = new Set();
    s.insert(new Integer(3));
    s.insert(new Integer(4));
    s.insert(new Integer(3));
    s.insert(new Integer(13));
    s.insert(new Integer(10));
    s.insert(new Integer(11));
    s.insert(new Integer(21));
    s.insert(new Integer(2));
    s.insert(new Integer(12));
    System.out.println("Set s = " + s);

    Set s2 = new Set();
    s2.insert(new Integer(4));
    s2.insert(new Integer(5));
    s2.insert(new Integer(5));
    System.out.println("Set s2 = " + s2);

    Set s3 = new Set();
    s3.insert(new Integer(5));
    s3.insert(new Integer(3));
    s3.insert(new Integer(8));
    System.out.println("Set s3 = " + s3);

    s.union(s2);
    System.out.println("After s.union(s2), s = " + s);

    s.intersect(s3);
    System.out.println("After s.intersect(s3), s = " + s);

    System.out.println("s.cardinality() = " + s.cardinality());
    // You may want to add more (ungraded) test code here.
  }
}
