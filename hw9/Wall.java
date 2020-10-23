
/**
 *
 * @author jasontosh
 */
public class Wall {
    String index;
    int ind1;
    int ind2;
    boolean horizontal;
    boolean vertical;
    boolean exists = true;

    public Wall(){}

    public void addVertical(int i, int j){
        this.index = "(" + Integer.toString(i) + "," + Integer.toString(j) + ")";
        ind1 = i;
        ind2 = j;
        vertical = true;
    }

    public void addHorizontal(int i, int j){
        this.index = "(" + Integer.toString(i) + "," + Integer.toString(j) + ")";
        ind1 = i;
        ind2 = j;
        horizontal = true;
    }

}
