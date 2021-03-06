/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package player;

/**
 *
 * @author jasontosh
 * 
 */

import list.*;

public class Analysis {
    
    /*
     * Finds the connections on this board for a given color. Returns an array of SLists
     * that list all of the connections for each chip.
     */
    public static SList[] findConnections(GameBoard board, int color){
        SList S = new SList();
		int oppColor;
		if(color == GameBoard.BLACK) { oppColor = GameBoard.WHITE; }
		else { oppColor = GameBoard.BLACK; }
        SList Sarr[] = new SList[(color == board.BLACK)?(board.bChipCount):(board.wChipCount)];
        int ind = 0;        
                //Scanning each cell of the board...
                for(int j=0; j<8; j++){
                    for (int k=0; k<8; k++){
                        //If we find a WHITE piece, we create a new list...
                        if(board.board[j][k] == color){
                            S = new SList();
                            S.insertEnd(new Point(j,k));
                            //Then we check up, down, and diagonal in each direction
                            //for exactly one connection.
                            //Check to the right...
                            for(int h=k+1; h<8; h++){
                                if(board.outOfBounds(j,h)){ break; }
                                if(board.board[j][h] == oppColor){ break; }
                                if(board.board[j][h] == color){ S.insertEnd(new Point(j,h)); break; }
                            }
                            //Check to the left...
                            for(int h=k-1; h>0; h--){
                                if(board.outOfBounds(j,h)){ break; }
                                if(board.board[j][h] == oppColor){ break; }
                                if(board.board[j][h] == color){ S.insertEnd(new Point(j,h)); break; }
                            }
                            //Check down
                            for(int h=j+1; h<8; h++){
                                if(board.outOfBounds(h,k)){ break; }
                                if(board.board[h][k] == oppColor){ break; }
                                if(board.board[h][k] == color){ S.insertEnd(new Point(h,k)); break; }
                            } 
                            //Check up
                            for(int h=j-1; h >= 0; h--){
                                if(board.outOfBounds(h,k)){ break; }
                                if(board.board[h][k] == oppColor){ break; }
                                if(board.board[h][k] == color){S.insertEnd(new Point(h,k)); break; }
                            }
                            //Check Diagonally down and to the right
                            for(int n=1; n<8; n++){
                                if(board.outOfBounds(j+n,k+n)){ break; }
                                if(board.board[j+n][k+n] == oppColor){ break; }
                                if(board.board[j+n][k+n] == color){ S.insertEnd(new Point(j+n,k+n)); break; }
                            } 
                            //Check Diagonally up and to the left
                            for(int n=1; n<8; n++){
                                if(board.outOfBounds(j-n,k-n)){ break; }
                                if(board.board[j-n][k-n] == oppColor){ break; }
                                if(board.board[j-n][k-n] == color){ S.insertEnd(new Point(j-n,k-n)); break; }
                            }
                            //Check Diagonally down and to the left
                            for(int n=1; n<8; n++){
                                if(board.outOfBounds(j+n,k-n)){ break; }
                                if(board.board[j+n][k-n] == oppColor){ break; }
                                if(board.board[j+n][k-n] == color){ S.insertEnd(new Point(j+n,k-n)); break; }
                            }
                            //Check Diagonally up and to the right
                            for(int n=1; n<8; n++){
                                if(board.outOfBounds(j-n,k+n)){ break; }
                                if(board.board[j-n][k+n] == oppColor){ break; }
                                if(board.board[j-n][k+n] == color){ S.insertEnd(new Point(j-n,k+n)); break; }
                            }
                            Sarr[ind]=S;
                            ind++;
                        }
                           
                    }
               
                }  
        
        return Sarr;
    }
    
    public static float scoreBoard(GameBoard board, int color){
        
        //Before running through the evaluation, we check the special case. If the
        //given board is a winning board we immediately return a winning value, 1 or -1
        //depending on which color's turn it is.
        
        int oppColor;
        int thisChipCount = (color == board.BLACK)?(board.bChipCount):(board.wChipCount);
        int winColor = board.checkForWinner(color);
        float whiteScore = 0;
        float blackScore = 0;
        
        if(color == GameBoard.WHITE){
            oppColor = GameBoard.BLACK;
        }else{
            oppColor = GameBoard.WHITE;
        }
        
        if(winColor == color){
            return 1;
        }else if(winColor == oppColor){
            return -1;
        }
        
        /*
         * Analyze the board for non-winning cases. Rank will start at 30, and 
         * decrease for doing stupid things
         */
        
        /*    | **********************************************************************
             *| WHITEWHITEWHITEWHITEWHITEWHITEWHITEWHITEWHITEWHITEWHITEWHITEWHITEWHITE|
             *| WHITEWHITEWHITEWHITEWHITEWHITEWHITEWHITEWHITEWHITEWHITEWHITEWHITEWHITE|
             *| WHITEWHITEWHITEWHITEWHITEWHITEWHITEWHITEWHITEWHITEWHITEWHITEWHITEWHITE|
             *| WHITEWHITEWHITEWHITEWHITEWHITEWHITEWHITEWHITEWHITEWHITEWHITEWHITEWHITE|
             * ************************************************************************
         * Score the Board for the color WHITE and store the score in the 
         * variable whiteScore
         */
                
            
            double rank = 30;
            int counter = 0;
            
            /*/
             * Check for trashing first
             */
//            if(board.board[0][2] == GameBoard.WHITE
//                    && board.board[0][4] == GameBoard.WHITE
//                    && board.board[2][2] == GameBoard.WHITE 
//                    && board.board[2][4] == GameBoard.WHITE){
//                if(board.board[0][1] == GameBoard.WHITE || board.board[2][1] == GameBoard.WHITE) {return -1; }
//            }
         
            
            /*
             * Check first column of the board. More than two chips, rank decreases.
             * exactly two chips, rank increaes, one chip, rank stays the same, 0 chips the rank 
             * decreases
             */
            for(int i=1; i<7; i++){
                if(board.board[0][i] == GameBoard.WHITE) { counter++; }
            }
            if(counter > 2) { rank-=2; }
            if(counter == 2) { rank++; }
            if(counter == 1) { }
            if(counter == 0) { rank--; }
            

            /*
             * Checks the goal column for chips. More than 2 chips, return -1 (illegal)
             * 1 or two chips, rank is increased. 0 chips, rank is decreased.
             */
            counter = 0;
            for(int i=1; i<7; i++){
                if(board.board[7][i] == GameBoard.WHITE) { counter++; }
            }
            if(counter > 2) { return -1; }
            if(counter == 1 || counter == 2) {rank++; }
            if(counter == 0) { rank--; }


            /*
             * Check all middle columns for more than one chip. Ideal number of chips
             * in any column is 1-3. So if there is 1 or 2 chips in any one
             * column, the rank will stay the same. if there is three, the rank will 
             * increase. If there is more than 3, the ranking will decrease. No chips 
             * in a column, and the rank will decrease.
             *
             */
            counter = 0;
            for(int j=1; j<7; j++){
                for(int i=0; i<7; i++){
                    if(board.board[j][i] == GameBoard.WHITE) { counter++; }
                }
            if(counter > 3) { rank-=2; }
            if(counter == 3) { rank++; }
            if(counter == 1 || counter == 2) { }
            if(counter < 1) { rank --; }
            }

            /*
             * Check for connections between chips. 
             * If there are 5 or more connections for each chip, ranking increases.
             * If there are 4 connections for each chip, ranking increases.
             * 3 connections, ranking stays the same
             * 2 connections, ranking decreases
             * Less than two connections, ranking decreases
             */
            counter = 0;
            SList[] S = findConnections(board,GameBoard.WHITE);
            for(int i=0; i< S.length; i++){
                if((S[i].length() - 1) > 5) { rank+=2; }
                if((S[i].length() - 1) == 4) { rank++; }
                if((S[i].length() - 1) == 3) { }
                if((S[i].length() - 1) == 2) { rank--; }
                if((S[i].length() - 1) < 2) { rank-= 2; }
            }
            
            /*
             * Check for networks. This part of the analysis will award the most points.
             * 10 points for each network of at least 5 chips. 5 points for each network of 4 chips.
             * 0 points for each network of 3 chips. -1 points for each network of 2 chips. -2 for each network of 1 chip
             * -5 points for each network of zero chips. Off for now, but may be used later. (e.g. in the tournament).
             */
            //for(int i=0; i<S.length; i++){
	    // int depth = findNetworkDepth(board,color,((Point)S[i].head.m));
	    // if(depth > 4) { rank += 10; }
	    // if(depth == 4) { rank += 5; }
	    // if(depth == 3) { }
	    // if(depth == 2) { rank--; }
	    // if(depth == 1) { rank -= 2; }
	    // if(depth == 0) { rank -= 5; }
            //}
            
            
            
            /*
             * Give a final score to a whiteboard by running it through the 
             * scoring function
             */
            if(rank < 0) { rank = 0; }
            whiteScore = (float)scoringFunction(rank);
            
                
         /*   | **********************************************************************
             *| BLACKBLACKBLACKBLACKBLACKBLACKBLACKBLACKBLACKBLACKBLACKBLACKBLACKBLACK|
             *| BLACKBLACKBLACKBLACKBLACKBLACKBLACKBLACKBLACKBLACKBLACKBLACKBLACKBLACK|
             *| BLACKBLACKBLACKBLACKBLACKBLACKBLACKBLACKBLACKBLACKBLACKBLACKBLACKBLACK|
             *| BLACKBLACKBLACKBLACKBLACKBLACKBLACKBLACKBLACKBLACKBLACKBLACKBLACKBLACK|
             * ************************************************************************
             * 
             * Score the board for BLACK and store the value in the variable
             * blackScore
             */
                
            /*
             * Check first row of the board. More than two chips, rank decreases.
             * exactly two chips, rank increaes, one chip, rank stays the same, 0 chips the rank 
             * decreases
             */
            rank = 30;
            counter = 0;
            for(int i=1; i<7; i++){
                if(board.board[0][i] == GameBoard.BLACK) { counter++; }
            }
            if(counter > 2) { rank-=2; }
            if(counter == 2) { rank++; }
            if(counter == 1) {  }
            if(counter == 0) { rank--; }

            /*
             * Checks the goal row for chips. More than 2 chips, return -1 (illegal)
             * 1 or two chips, rank is increased. 0 chips, rank is decreased.
             */
            counter = 0;
            for(int i=1; i<7; i++){
                if(board.board[i][7] == GameBoard.BLACK) { counter++; }
            }
            if(counter > 2) { return -1; }
            if(counter == 1 || counter == 2) { rank++;}
            if(counter == 0) { rank--; }


            /*
             * Check all middle rows for more than one chip. Ideal number of chips
             * in any column is 1-3. So if there is 1 or 2 chips in any one
             * column, the rank will stay the same. if there is three, the rank will 
             * increase. If there is more than 3, the ranking will decrease. No chips 
             * in a column, and the rank will decrease.
             *
             */
            counter = 0;
            for(int j=0; j<7; j++){
                for(int i=0; i<7; i++){
                    if(board.board[i][j] == GameBoard.BLACK) { counter++; }
                }
            if(counter > 3) { rank-=2; }
            if(counter == 3) { rank ++; }
            if(counter == 1 || counter == 2) {}
            if(counter < 1) { rank--; }
            }

            /*
             * Check for connections between chips. 
             * If there are 5 or more connections for each chip, ranking increases.
             * If there are 4 connections for each chip, ranking increases.
             * 3 connections, ranking stays the same
             * 2 connections, ranking decreases
             * Less than two connections, ranking decreases
             */
            counter = 0;
            S = findConnections(board,GameBoard.BLACK);
            for(int i=0; i< S.length; i++){
                if((S[i].length() - 1) > 5) { rank+=2; }
                if((S[i].length() - 1) == 4) { rank++; }
                if((S[i].length() - 1) == 3) {  }
                if((S[i].length() - 1) == 2) { rank--; }
                if((S[i].length() - 1) < 2) { rank-= 2; }
            }
            
            /*
             * Check for networks. This part of the analysis will award the most points.
             * 10 points for each network of at least 5 chips. 5 points for each network of 4 chips.
             * 0 points for each network of 3 chips. -1 points for each network of 2 chips. -2 for each network of 1 chip
             * -5 points for each network of zero chips.
             */
            //for(int i=0; i<S.length; i++){
	    //int depth = findNetworkDepth(board,color,((Point)S[i].head.m));
	    //if(depth > 4) { rank += 10; }
	    //if(depth == 4) { rank += 5; }
	    //if(depth == 3) { }
	    //if(depth == 2) { rank--; }
	    //if(depth == 1) { rank -= 2; }
	    //if(depth == 0) { rank -= 5; }
            //}
            
            
            
            /*
             * Give a final score to a blackboard by running it through the 
             * scoring function
             */
            if(rank == 0) { rank = 0; }
            blackScore = (float)scoringFunction(rank);
                
        
        /*
         * Return value for the analysis function.
         */
	    // if(color == GameBoard.WHITE) { return whiteScore; }
	    // else { return blackScore; }
            

	    /*
	     *Alternate return value, for deeper searches.
	     */
	     if(color == GameBoard.WHITE){
		 if(blackScore >= whiteScore) { return -blackScore; }
		 else { return whiteScore; }
	     }else {
		 if(whiteScore >= blackScore) { return -whiteScore; }
		 else { return blackScore; }
	     }
           
    }
    
    
    /*/
     * scores the board given a ranking on a decayed exponential scale so
     * that the value of the board is always between 0 and 1
     */
    private static double scoringFunction(double rank){
        return 1-Math.exp(-rank/20d);
    }
    
    
    /*
     * Given a board and a chip's coordinates on the board, will return the depth
     * of that chips network on the board.
     */
    private static int findNetworkDepth(GameBoard board, int color, Point p){
        //int[][] findPieces = board.findPieces(color);
        //AdjacencyMatrix adj = board.makeAdjacencyMatrix(findPieces, color);
        //System.out.println(adj);
        
        return -1;
	    }
    
    
}
        
          

