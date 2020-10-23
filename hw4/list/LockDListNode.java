

package list;

/**
 *
 * @author jasontosh
 */
public class LockDListNode extends DListNode {
    
    protected boolean LOCKED;
    
    LockDListNode(Object item, DListNode p, DListNode n) {
        super(item,p,n);
        LOCKED = false;
        
    }
    
}
