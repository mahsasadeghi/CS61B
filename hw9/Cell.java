/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jasontosh
 */
public class Cell {
    protected int index;
    protected String cell;
    boolean root = true;

    public Cell(){
    }

    public Cell(int i, int j){
        this.cell = "(" + Integer.toString(i) + "," + Integer.toString(j) + ")";
    }

    public void setIndex(int ind){
        this.index = ind;
    }

    public String toString(){
        return " " + cell;
    }
}
