package com.example.make_a_square_gui;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
interface constants{
    public final int gridRows = 4;
    public final int gridCols = 4;
    
    public final int numberOfPieces = 5;
    public final int logNumberOfPieces = 3; //log2(numberOfPieces-1)+1
    
    public final int numberOfMoves = 4;
    public final int logNumberOfMoves = 2; //log2(numberOfMoves-1)+1
    
    public final int bitsPerPiece = (logNumberOfPieces + logNumberOfMoves);
    public final int bitsPerBoard = numberOfPieces * bitsPerPiece;
    public final int numberOfBoards = (1 << bitsPerBoard);
    
    public final int numberOfThreads = 5;
    public final int sectionSize = numberOfBoards / numberOfThreads;
}

public class Mythread  extends Thread {
    public static List<int[][]> result;
    private volatile boolean running = true;
    static boolean foundBoard;
    private static Set<String> boardSet = new HashSet<>();
    private ReentrantLock lock;
    public static int[][] finallyBoard;
    private int[][] thBoard;


    public void terminate() {
        this.running = false;
    }
    public int[][] getThBoard() {
        return thBoard;
    }

    public void setThBoard(int[][] thBoard) {
        this.thBoard = thBoard;
    }

    static public Map<Integer, int[][]> allPieces;
    

    @Override
    public void run() {
        int[][] finalBoard;
        int threadID = Integer.parseInt(Thread.currentThread().getName());
        
        int from = threadID * constants.sectionSize;
        int to = Math.min(from + constants.sectionSize - 1 , constants.numberOfBoards - 1);
        if(threadID == constants.numberOfThreads - 1)
            to = constants.numberOfBoards - 1;
        
        //last thread must complete to the end of the states.
        for(int mask = from; mask <= to; mask++){
            Board slaveBoard = new Board(allPieces);
            finalBoard = slaveBoard.decompose(mask);
            
//            if(foundBoard)
//                break;





            if(finalBoard != null) {
                synchronized (Mythread.class) {
                    String boardKey = Arrays.deepToString(finalBoard);
                    if (!boardSet.contains(boardKey)) {
                        boardSet.add(boardKey);
                        this.setThBoard(finalBoard);



                        foundBoard = true;
                        finallyBoard = finalBoard;
                    }
                }
            }
        }
        System.out.println("Thread " + threadID + " finished.");
    }
}
