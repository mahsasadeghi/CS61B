/* HashTableChained.java */

package dict;
import list.*;
import java.util.Random;
/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

  /**
   *  Place any data fields here.
   **/
    Object[] table;
    Random generator = new Random();
    int a;
    int b;
    int p;
    int N;
    int size;
    public int[] collisionChecker;
    int collisionCounter;



  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
    int[] primes = generatePrimes(2*sizeEstimate);
    this.table = new Object[primes[primes.length-1]];
    for(int i=0; i< table.length; i++){
        this.table[i] = new SList();
    }
    primes = generatePrimes(13*primes.length);
    p = primes[primes.length-1];
    a = generator.nextInt(p);
    b= generator.nextInt(p);
    N = this.table.length;
    
    
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
    this.table = new Object[103];
    for(int i=0; i< table.length; i++){
        this.table[i] = new SList();
    }
    int[] primes = generatePrimes(17*103);
    p = primes[primes.length-1];
    a = generator.nextInt(p);
    b= generator.nextInt(p);
    N = this.table.length;
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
      return Math.abs((Math.abs((a*code + b)) % p)) % N;   
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
      return this.size;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
      return size == 0;
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
        int k = key.hashCode();
        k = compFunction(k);
        
        Entry e = new Entry();
        e.key = key;
        e.value = value;
        ((SList)this.table[k]).insertFront(e);
        
        this.size++;
        return e;
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
      try{
          int k = key.hashCode();
          k = compFunction(k);

          SListNode r1 = ((SList)this.table[k]).head;
          if(r1 == null) { return null; }
          else{
            while(r1 != null){
                if(((Entry)r1.entry).key.equals(key)) break;
                else r1 = r1.next;
            }
            return (Entry)r1.entry;
          }
      }catch(NullPointerException e) {return null;}
      
      

  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
      
      try{
          int k = key.hashCode();
          k = compFunction(k);

          SListNode r1 = ((SList)this.table[k]).head;
          if(r1 == null) { return null; }
          else{
              while(r1 != null){
                  if(((Entry)r1.entry).key.equals(key)) break;
                  else r1 = r1.next;
              }

              Entry e = (Entry)r1.entry;
              ((SList)this.table[k]).remove(r1);

              this.size--;
              return e;
          }
      }catch(NullPointerException e) { return null; }
    
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {  
      for(int i = 0; i < this.table.length; i++){
        ((SList)this.table[i]).head = null;
    }
      this.size = 0;
    
  }
  
  private static int [] generatePrimes(int max) {
    boolean[] isComposite = new boolean[max + 1];
    for (int i = 2; i * i <= max; i++) {
        if (!isComposite [i]) {
            for (int j = i; i * j <= max; j++) {
                isComposite [i*j] = true;
            }
        }
    }
    int numPrimes = 0;
    for (int i = 2; i <= max; i++) {
        if (!isComposite [i]) numPrimes++;
    }
    int [] primes = new int [numPrimes];
    int index = 0;
    for (int i = 2; i <= max; i++) {
        if (!isComposite [i]) primes [index++] = i;
    }
    
    return primes;
  }
  
}
