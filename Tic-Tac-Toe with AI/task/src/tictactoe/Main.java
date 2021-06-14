package tictactoe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static char[][] enterCells(){
        int xnum = 0, onum = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Cells: ");

        String cells = scanner.nextLine();
        char[] ch = new char[cells.length()];

        for(int i = 0; i < cells.length(); i++){
            if(cells.charAt(i) == '_'){
                ch[i] = ' ';
            }else {
                ch[i] = cells.charAt(i);
            }
        }
        System.out.println(Arrays.toString(ch));
        char[][] arr = {{ch[0], ch[1], ch[2]}, {ch[3], ch[4], ch[5]}, {ch[6], ch[7], ch[8]}};

        showCells(arr);
        return arr;
    }
    static boolean analyseGame(char[][] Arr){
        List<Character> list = new ArrayList<Character>();
        for (int i = 0; i < Arr.length; i++) {
            // tiny change 1: proper dimensions
            for (int j = 0; j < Arr[i].length; j++) {
                // tiny change 2: actually store the values
                list.add(Arr[i][j]);
            }
        }

        char[] ch = new char[list.size()];
        for (int i = 0; i < ch.length; i++) {
            ch[i] = list.get(i);
        }

        char winner = '_';
        int winningcombi = 0;
        char X = 'X';
        char O = 'O';
        char[] arr = {X, O};
        int numofX = 0;
        int numofO = 0;
        // checking all possibilities for winning
        for(int j = 0; j < 2; j++){
            char r = arr[j];
            for(int i = 0; i <= 6; i+=3) {
                if (r == ch[i] && r == ch[i+1] && r == ch[i+2]) {
                    winningcombi++;
                    winner = r;
                }
            }
            for(int i = 0; i <= 2; i++) {
                if (r == ch[i] && r == ch[i + 3] && r == ch[i + 6]) {
                    winningcombi++;
                    winner = r;
                }
            }

            if(r == ch[0] && r == ch[4] && r == ch[8]){
                winningcombi++;
                winner = r;
            }
            if(r == ch[2] && r == ch[4] && r == ch[6]){
                winningcombi++;
                winner = r;
            }
        }


        if(winningcombi == 0){
            for(char c: ch){
                if(c == 'X'){
                    numofX++;
                }
                if(c == 'O'){
                    numofO++;
                }
            }
            if(Math.abs(numofX - numofO) > 1){
                System.out.println("Impossible");
                return false;
            }
            /*
            for(char c : ch) {
                if (c == ' ') {
                    System.out.println("Game not finished");
                    return true;
                }
            }*/

            if(numofO + numofX == 9) {
                System.out.println("Draw");
                return false;
            }
        }

        if(winningcombi >= 1){
            System.out.println(winner + " wins");
            return false;
        }
        return true;
    }
    static char[][] enterCoord(char[][] arr, char c){
        int xnum = 0, onum = 0;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(arr[i][j] == 'X'){
                    xnum++;
                }
                if(arr[i][j] == 'O'){
                    onum++;
                }
            }
        }
        if(xnum > onum){
            c = 'O';
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the coordinates: ");

        int row = 0;
        int column = 0;
        String str = scanner.nextLine();

        if(Character.isLetter(str.charAt(0)) || Character.isLetter(str.charAt(2))){
            System.out.println("You should enter numbers!");
            return enterCoord(arr, c);
        }else{
            row = Character.getNumericValue(str.charAt(0)) - 1; //System.out.println(row);
            column = Character.getNumericValue(str.charAt(2)) - 1; //System.out.println(column);
        }
        if(row > 2 || row < 0 || column > 2 || column < 0){
            System.out.println("Coordinates should be from 1 to 3!");
            return enterCoord(arr, c);
        }

        if(arr[row][column] == ' ') {
            arr[row][column] = c;
        }else{
            System.out.println("This cell is occupied! Choose another one!");
            return enterCoord(arr, c);
        }
        return arr;
    }
    static void showCells(char[][] arr){
        System.out.println("---------");
        for(int i = 0; i < 3; i++){
            for(int j = -1; j < 4; j++){
                if(j == -1 || j == 3){
                    System.out.print("| ");
                }else{
                    System.out.print(arr[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println("---------");

    }
    static char[][] easyAI(char[][] arr, char c){
        int row = (int)(Math.random() * 3); //System.out.println(row);
        int column = (int)(Math.random() * 3); //System.out.println(column);
        if(arr[row][column] == ' ') {
            arr[row][column] = c;
        }else{
            //System.out.println("This cell is occupied! Choose another one!");
            return easyAI(arr, c);
        }

        return arr;
    }
    static int[] generateMoves(char[][] arr, char c){
        int row = (int)(Math.random() * 3); //System.out.println(row);
        int column = (int)(Math.random() * 3); //System.out.println(column);
        //check if opponent can win
        char v = 0;
        if(c == 'X'){
            v = 'O';
        }else if(c == 'O'){
            v = 'X';
        }
        char s = ' ';
        for(int i = 0; i < 2; i++){
            if(arr[i][0] == v && arr[i][1] == v && arr[i][2] == s){
                row = i; column = 2; break;
            }
            if(arr[i][0] == v && arr[i][1] == s && arr[i][2] == v){
                row = i; column = 1; break;
            }
            if(arr[i][0] == s && arr[i][1] == v && arr[i][2] == v){
                row = i; column = 0; break;
            }
        }
        //vertical check
        for(int i = 0; i < 2; i++) {
            if(arr[0][i] == v && arr[1][i] == v && arr[2][i] == s){
                row = 2; column = i; break;
            }
            if(arr[0][i] == v && arr[1][i] == s && arr[2][i] == v){
                row = 1; column = i; break;
            }
            if(arr[0][i] == s && arr[1][i] == v && arr[2][i] == v){
                row = 0; column = i; break;
            }
        }
        //cross check
        if(arr[0][0] == v && arr[1][1] == v && arr[2][2] == s){
            row = 2; column = 2;
        }
        if(arr[0][0] == v && arr[1][1] == s && arr[2][2] == v){
            row = 1; column = 1;
        }
        if(arr[0][0] == s && arr[1][1] == v && arr[2][2] == v){
            row = 0; column = 0;
        }

        if(arr[0][2] == v && arr[1][1] == v && arr[2][0] == s){
            row = 2; column = 0;
        }
        if(arr[0][2] == v && arr[1][1] == s && arr[2][0] == v){
            row = 1; column = 1;
        }
        if(arr[0][2] == s && arr[1][1] == v && arr[2][0] == v){
            row = 0; column = 2;
        }

        //check if it can win
        //horizontal check
        for(int i = 0; i < 2; i++){
            if(arr[i][0] == c && arr[i][1] == c && arr[i][2] == s){
                row = i; column = 2; break;
            }
            if(arr[i][0] == c && arr[i][1] == s && arr[i][2] == c){
                row = i; column = 1; break;
            }
            if(arr[i][0] == s && arr[i][1] == c && arr[i][2] == c){
                row = i; column = 0; break;
            }
        }
        //vertical check
        for(int i = 0; i < 2; i++) {
            if(arr[0][i] == c && arr[1][i] == c && arr[2][i] == s){
                row = 2; column = i; break;
            }
            if(arr[0][i] == c && arr[1][i] == s && arr[2][i] == c){
                row = 1; column = i; break;
            }
            if(arr[0][i] == s && arr[1][i] == c && arr[2][i] == c){
                row = 0; column = i; break;
            }
        }
        //cross check
        if(arr[0][0] == c && arr[1][1] == c && arr[2][2] == s){
            row = 2; column = 2;
        }
        if(arr[0][0] == c && arr[1][1] == s && arr[2][2] == c){
            row = 1; column = 1;
        }
        if(arr[0][0] == s && arr[1][1] == c && arr[2][2] == c){
            row = 0; column = 0;
        }

        if(arr[0][2] == c && arr[1][1] == c && arr[2][0] == s){
            row = 2; column = 0;
        }
        if(arr[0][2] == c && arr[1][1] == s && arr[2][0] == c){
            row = 1; column = 1;
        }
        if(arr[0][2] == s && arr[1][1] == c && arr[2][0] == c){
            row = 0; column = 2;
        }

        int[] rc = {row, column};
        return rc;
    }
    static char[][] mediumAI(char[][] arr, char c){
        //random r and c
        int[] rc = generateMoves(arr, c);
        int row = rc[0];
        int column = rc[1];

        if(arr[row][column] == ' ') {
            arr[row][column] = c;
        }else{
            //System.out.println("This cell is occupied! Choose another one!");
            return mediumAI(arr, c);
        }

        return arr;
    }
    // returns list of the indexes of empty spots on the board
    static List<int[]> emptyIdx(char[][] arr) {
        List<int[]> emptyIdxes = new ArrayList<int[]>();

        int a = 0;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(arr[i][j] == ' '){
                    int[] idexes = {i, j};
                    emptyIdxes.add(idexes);
                    //System.out.print(Arrays.toString(emptyIdxes[a]) + " ");
                }
            }
            //System.out.println();
        }

        return emptyIdxes;
    }
    // winning combinations using the board indexies
    static boolean winning(char[][] arr, char c){
        if(
            (arr[0][0] == c && arr[0][1] == c && arr[0][2] == c) ||
            (arr[1][0] == c && arr[1][1] == c && arr[1][2] == c) ||
            (arr[2][0] == c && arr[2][1] == c && arr[2][2] == c) ||
            (arr[0][0] == c && arr[1][0] == c && arr[2][0] == c) ||
            (arr[0][1] == c && arr[1][1] == c && arr[2][1] == c) ||
            (arr[0][2] == c && arr[1][2] == c && arr[2][2] == c) ||
            (arr[0][0] == c && arr[1][1] == c && arr[2][2] == c) ||
            (arr[0][2] == c && arr[1][1] == c && arr[2][0] == c)
        ){
            return true;
        }else{
            return false;
        }
    }

    static Move minimax(char c, char[][] newboard, char player){

        //available spots
        List<int[]> availSpots = emptyIdx(newboard);
        //System.out.println(Arrays.deepToString(availSpots));
        //int[][] emptyarr = {{0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0}};
        char v = 0;
        //c is aiPlayer, v is huPlayer
        if(c == 'X'){
            v = 'O';
        }else if(c == 'O'){
            v = 'X';
        }

        //checks for the terminal states such as win, lose, and tie
        //and returning a value accordingly
        if(winning(newboard, v)){
            return new Move(-10);
        }else if(winning(newboard, c)){
            return new Move(10);
        }else if (availSpots.size() == 0){
            return new Move(0);
        }

        int[][] availSpot = availSpots.toArray(new int[availSpots.size()][2]);

        // an array to collect all the objects
        List<Move> moves = new ArrayList<Move>();

        // loop through available spots
        for (var i = 0; i < availSpots.size(); i++){
            //create an object for each and store the index of that spot
            Move move = new Move();

            //move.ch = newboard[availSpot[i][0]][availSpot[i][1]];
            move.pos = new int[]{availSpot[i][0], availSpot[i][1]};

            // set the empty spot to the current player
            newboard[availSpot[i][0]][availSpot[i][1]] = player;
            //System.out.println(Arrays.deepToString(newboard));

            /*collect the score resulted from calling minimax
            on the opponent of the current player*/
            if(player == c){
                Move result = minimax(c, newboard, v);//
                move.score = result.score;
            }else{
                Move result = minimax(c, newboard, c);//
                move.score = result.score;
            }
            // reset the spot to empty
            newboard[availSpot[i][0]][availSpot[i][1]] = ' ';
            // push the object to the array
            moves.add(move);
        }
        //list to arr
        //Move[] ar = new Move[moves.size()];

        // if it is the computer's turn loop over the moves and choose the move with the highest score
        int bestMove = 0;
        if(player == c) {
            int bestScore = -10000;

            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).score > bestScore){
                    bestScore = moves.get(i).score; //System.out.println(bestScore);
                    bestMove = i;

                }
            }
        }else{ // else loop over the moves and choose the move with the lowest score
            int bestScore = 10000;

            for (int i = 0; i < moves.size(); i++) {
                //System.out.println(i + " " + ar[i]);
                if (moves.get(i).score < bestScore){
                    bestScore = moves.get(i).score;
                    bestMove = i;
                }
            }
        }
        // return the chosen move (object) from the moves array
        //System.out.println("Hello");
        return moves.get(bestMove);
    }
    static char[][] hardAI(char[][] arr, char c){
        char[][] emptyarr = {{' ', ' ', ' '},{' ', ' ', ' '},{' ', ' ', ' '}};
        /*
        if(arr == emptyarr){
            int row = (int)(Math.random() * 3);
            int column = (int)(Math.random() * 3);
            arr[row][column] = c;
            return arr;
        }*/

        Move move = minimax(c, arr, c);
        int row = move.pos[0];
        int column = move.pos[1];
        arr[row][column] = c;
        return arr;
    }
    static void exit(){
        System.exit(0);
    }
    static String[] inputCmd(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input command: ");
        String[] cmd = new String[2];
        String str = new String();
        if(scanner.hasNext("exit")){
            exit();
        }
        if(scanner.hasNext("start")) {
            str = scanner.nextLine();
            if(str.length() < 6){
                System.out.println("Bad parameters!");
                return inputCmd();
            }else{
                String[] arr = str.split(" ");
                if(arr.length < 3){
                    System.out.println("Bad parameters!");
                    return inputCmd();
                }else{
                    String[] inputs = {"user", "easy", "medium", "hard"};
                    boolean bool1 = false, bool2 = false;
                    //System.out.println(arr[1]);
                    for(int i = 0; i < 4; i++){
                        if(arr[1].equals(inputs[i])){
                            bool1 = true;
                            break;
                        }
                    }
                    //System.out.println(arr[2]);
                    for(int i = 0; i < 4; i++){
                        if(arr[2].equals(inputs[i])){
                            bool2 = true;
                            break;
                        }
                    }
                    if(bool1 && bool2) {
                        cmd[0] = arr[1];
                        cmd[1] = arr[2];
                    }else{
                        System.out.println("Bad parameters!");
                        return inputCmd();
                    }
                }
            }
        }else{
            System.out.println("Bad parameters!");
            return inputCmd();
        }
        return cmd;
    }
    static void startgame(String[] parameters, char[][] arr){
        boolean bool = true;
        char c = 'X';
        int count = 0;
        while(bool) {
            //user move
            if(parameters[0].equalsIgnoreCase("user")) {
                arr = enterCoord(arr, c);
                showCells(arr);
                bool = analyseGame(arr);
                if (!bool) {
                    break;
                }
                c = 'O';
            }
            //ai move
            if(parameters[0].equalsIgnoreCase("easy")) {
                System.out.println("Making move level \"easy\"");
                arr = easyAI(arr, c);
                showCells(arr);
                bool = analyseGame(arr);
                if (!bool) {
                    break;
                }
                c = 'O';
            }

            if(parameters[0].equalsIgnoreCase("medium")) {
                System.out.println("Making move level \"medium\"");
                arr = mediumAI(arr, c);
                showCells(arr);
                bool = analyseGame(arr);
                if (!bool) {
                    break;
                }
                c = 'O';
            }

            if(parameters[0].equalsIgnoreCase("hard")) {
                System.out.println("Making move level \"hard\"");
                arr = hardAI(arr, c);
                showCells(arr);
                bool = analyseGame(arr);
                if (!bool) {
                    break;
                }
                c = 'O';
            }

            if(parameters[1].equalsIgnoreCase("user")) {
                arr = enterCoord(arr, c);
                showCells(arr);
                bool = analyseGame(arr);
                if (!bool) {
                    break;
                }
            }

            if(parameters[1].equalsIgnoreCase("easy")) {
                System.out.println("Making move level \"easy\"");
                arr = easyAI(arr, c);
                showCells(arr);
                bool = analyseGame(arr);
                if (!bool) {
                    break;
                }
            }

            if(parameters[1].equalsIgnoreCase("medium")) {
                System.out.println("Making move level \"medium\"");
                arr = mediumAI(arr, c);
                showCells(arr);
                bool = analyseGame(arr);
                if (!bool) {
                    break;
                }
            }

            if(parameters[1].equalsIgnoreCase("hard")) {
                System.out.println("Making move level \"hard\"");
                arr = hardAI(arr, c);
                showCells(arr);
                bool = analyseGame(arr);
                if (!bool) {
                    break;
                }
            }
            /*
            count++;
            if ((count % 2 == 0)) {
                c = 'X';
            } else {
                c = 'O';
            }*/
            c = 'X';
        }
    }
    public static void main(String[] args) {
        String[] parameters = inputCmd();

        char[][] arr = {{' ', ' ', ' '},{' ', ' ', ' '},{' ', ' ', ' '}};
        showCells(arr);
        startgame(parameters, arr);

    }
}

