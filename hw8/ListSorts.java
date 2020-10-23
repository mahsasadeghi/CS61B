/* ListSorts.java */

import list.*;

public class ListSorts {

  private final static int SORTSIZE = 100;

  /**
   *  makeQueueOfQueues() makes a queue of queues, each containing one item
   *  of q.  Upon completion of this method, q is empty.
   *  @param q is a LinkedQueue of objects.
   *  @return a LinkedQueue containing LinkedQueue objects, each of which
   *    contains one object from q.
   **/
  public static LinkedQueue makeQueueOfQueues(LinkedQueue q) {
      LinkedQueue Q = new LinkedQueue();
      LinkedQueue temp;
      try{
          while(q.size() > 0){
              temp = new LinkedQueue();
              temp.enqueue(q.dequeue());
              Q.enqueue(temp);
          }
          
      }catch(QueueEmptyException e) {System.out.println("Mistake");}
      return Q;
  }

  /**
   *  mergeSortedQueues() merges two sorted queues into a third.  On completion
   *  of this method, q1 and q2 are empty, and their items have been merged
   *  into the returned queue.
   *  @param q1 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @param q2 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @return a LinkedQueue containing all the Comparable objects from q1 
   *   and q2 (and nothing else), sorted from smallest to largest.
   **/
  public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) {
      LinkedQueue Q = new LinkedQueue();
      int s = q1.size() + q2.size();
      try{
          
          for(int i=0; i<s; i++){
              
              if(!(q1.isEmpty() || q2.isEmpty())){
              
                  if(((Comparable)q1.front()).compareTo((Comparable)q2.front()) < 0){
                      Q.enqueue(q1.dequeue());
                  }
                  else if(((Comparable)q1.front()).compareTo((Comparable)q2.front()) > 0){
                      Q.enqueue(q2.dequeue());
                  }
                  else{
                      Q.enqueue(q1.dequeue());
                      Q.enqueue(q2.dequeue());
                  }
              }
              else{
                  if(q1.isEmpty()){
                      Q.append(q2);
                      break;
                  }
                  else { Q.append(q1); break; }
              }
                  
              
          }
      
      }catch(QueueEmptyException e){System.out.println("Error");}
      return Q;
  }

  /**
   *  partition() partitions qIn using the pivot item.  On completion of
   *  this method, qIn is empty, and its items have been moved to qSmall,
   *  qEquals, and qLarge, according to their relationship to the pivot.
   *  @param qIn is a LinkedQueue of Comparable objects.
   *  @param pivot is a Comparable item used for partitioning.
   *  @param qSmall is a LinkedQueue, in which all items less than pivot
   *    will be enqueued.
   *  @param qEquals is a LinkedQueue, in which all items equal to the pivot
   *    will be enqueued.
   *  @param qLarge is a LinkedQueue, in which all items greater than pivot
   *    will be enqueued.  
   **/   
  public static void partition(LinkedQueue qIn, Comparable pivot, 
                               LinkedQueue qSmall, LinkedQueue qEquals, 
                               LinkedQueue qLarge) {
      while(!qIn.isEmpty()){
          try{
              if(((Comparable)qIn.front()).compareTo((Comparable)pivot) > 0){
                  qLarge.enqueue(qIn.dequeue());
              }
              else if(((Comparable)qIn.front()).compareTo((Comparable)pivot) < 0){
                  qSmall.enqueue(qIn.dequeue());
              }
              else {
                  qEquals.enqueue(qIn.dequeue());
              }
          }catch(QueueEmptyException e) {System.out.println("You set the place on fire");}
      }
      
      
      
  }

  /**
   *  mergeSort() sorts q from smallest to largest using mergesort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void mergeSort(LinkedQueue q){
      LinkedQueue Q = makeQueueOfQueues(q);
      try{
          if(Q.size() == 1){ q.append((LinkedQueue)Q.dequeue()); }
          
          for(int i=0; i< Integer.MAX_VALUE; i++){
              LinkedQueue q1 = (LinkedQueue)Q.dequeue();
              LinkedQueue q2 = (LinkedQueue)Q.dequeue();
              Q.enqueue(mergeSortedQueues(q1,q2));
              
              if(Q.size() == 1){
                  q.append((LinkedQueue)Q.dequeue());
                  break;
              }
              else{ continue; }
          }
      }catch(QueueEmptyException e){}
  
  }

  /**
   *  quickSort() sorts q from smallest to largest using quicksort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void quickSort(LinkedQueue q) {
      int rand = (int)Math.random() * (q.size() - 1);
      Object pivot = q.nth(rand);
      LinkedQueue qS = new LinkedQueue();
      LinkedQueue qE = new LinkedQueue();
      LinkedQueue qL = new LinkedQueue();
      partition(q,(Comparable)pivot,qS,qE,qL);
      if(qL.size() > 1){
      quickSort(qL);
      }
      qE.append(qL);
      if(qS.size() > 1){
          quickSort(qS);
      }
      qS.append(qE);
      q.append(qS);    
      
  }

  /**
   *  makeRandom() builds a LinkedQueue of the indicated size containing
   *  Integer items.  The items are randomly chosen between 0 and size - 1.
   *  @param size is the size of the resulting LinkedQueue.
   **/
  public static LinkedQueue makeRandom(int size) {
    LinkedQueue q = new LinkedQueue();
    for (int i = 0; i < size; i++) {
      q.enqueue(new Integer((int) (size * Math.random())));
    }
    return q;
  }

  /**
   *  main() performs some tests on mergesort and quicksort.  Feel free to add
   *  more tests of your own to make sure your algorithms works on boundary
   *  cases.  Your test code will not be graded.
   **/
  public static void main(String [] args) {

    LinkedQueue q = makeRandom(10);
    System.out.println(q.toString());
    mergeSort(q);
    System.out.println(q.toString());

    q = makeRandom(10);
    System.out.println(q.toString());
    quickSort(q);
    System.out.println(q.toString());

    // Remove these comments for Part III.
    Timer stopWatch = new Timer();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    mergeSort(q);
    stopWatch.stop();
    System.out.println("Mergesort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

    stopWatch.reset();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    quickSort(q);
    stopWatch.stop();
    System.out.println("Quicksort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");
    
  }

}
