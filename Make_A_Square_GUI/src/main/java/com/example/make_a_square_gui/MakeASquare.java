package com.example.make_a_square_gui;
import java.util.*;

public class MakeASquare {
    public static List<int[][]> Square(HashMap<Integer, int[][]> sendPieces) throws InterruptedException {


        //pass pieces to the slaves area...
        Mythread.allPieces = sendPieces;
        Mythread.foundBoard = false;


        //preparing the slaves.
        Mythread[] masterSlave = new Mythread[constants.numberOfThreads];
         List<int[][]> allBoards = new ArrayList<>(); // List to hold all found boards

        for(int i = 0; i < constants.numberOfThreads; i++){
          masterSlave[i]=new Mythread();
        masterSlave[i].setName(Integer.toString(i));
        masterSlave[i].start();

        }
        
      
        //wait slaves to finish...
        for(int  k = 0; k < constants.numberOfThreads; k++){


            masterSlave[k].join();
            if (masterSlave[k].getThBoard() != null) {
                allBoards.add(masterSlave[k].getThBoard());
                System.out.println("board for thread "+k);
 
                for (int i = 0; i < masterSlave[k].getThBoard().length; i++) {
                    for (int j = 0; j <masterSlave[k].getThBoard().length; j++) {
                        System.out.print(masterSlave[k].getThBoard()[i][j] + " ");
                    }
                    System.out.println(); // Move to the next line after each row
                }
            }


        }
        if (!allBoards.isEmpty()) {
            return allBoards;
            
        }
     else return null;





        
        
    }
}
