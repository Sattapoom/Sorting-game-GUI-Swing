import java.util.Arrays;
import java.util.Random;

public class SortingGame_SwingGUI {
    
    String[][] game_board = {{"A","B","C","D"}, {"E","F","G","H"}, {"I","J","K"," "}};
    int[] index_space = {2,3};
    
// $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Game's metthord $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
     void shuffle_board(){
        String[] remember = new String[3];
        int ran = (int) random(100,250);
        for (int round=0 ; round<=ran;round++) {
            String[] move_able = {" "," "," "," "};
            if (index_space[0]==1) {
                move_able[0] = game_board[index_space[0]-1][index_space[1]];
                move_able[1] = game_board[index_space[0]+1][index_space[1]];
            }
            else if (index_space[0]==0) {
                move_able[1] = game_board[index_space[0]+1][index_space[1]];
            }
            else if (index_space[0]==2) {
                move_able[0] = game_board[index_space[0]-1][index_space[1]];
            }
            if (index_space[1]==0) {
                move_able[3] = game_board[index_space[0]][index_space[1]+1];
            }
            else if (index_space[1]==3) {
                move_able[2] = game_board[index_space[0]][index_space[1]-1];
            }
            else {
                move_able[3] = game_board[index_space[0]][index_space[1]+1];
                move_able[2] = game_board[index_space[0]][index_space[1]-1];
            }
        
            String moving = move_able[(int) random(0,4)];  
            while (moving.equals(" ")) {
                moving = move_able[(int) random(0,4)];    
            }
            if ( !(moving.equals(remember[0]) || moving.equals(remember[1]) || moving.equals(remember[2]))) {
                moveChar(moving);
            }
            if (0<(int)random(0,1)) {
                remember[round%3] = moving;
            }
        }
    }
    //------------------################
    int random(int min,int max){
        Random num = new Random();
        return num.nextInt((max+1)-min) + min;
    }
    //------------------################
    String onClick(int mouse_x,int mouse_y){
        int row,col;
        row = (int)mouse_y/200;
        col = (int)mouse_x/200;
        return game_board[row][col];
    }
    //------------------################
    void moveChar(String c){
        int topIndex = index_space[0] - 1;
        if (topIndex >= 0) {
            if (game_board[topIndex][index_space[1]].equals(c)) {
            game_board[topIndex][index_space[1]] = " ";
            game_board[index_space[0]][index_space[1]] = c;
            index_space[0] = topIndex;
            return;
            }
        }
        int bottomIndex = index_space[0] + 1;
        if (bottomIndex <= 2) {
            if (game_board[bottomIndex][index_space[1]].equals(c)) {
            game_board[bottomIndex][index_space[1]] = " ";
            game_board[index_space[0]][index_space[1]] = c;
            index_space[0] = bottomIndex;
            return;
            }
        }
        int leftIndex = index_space[1] - 1;
        if (leftIndex >= 0) {
            if (game_board[index_space[0]][leftIndex].equals(c)) {
            game_board[index_space[0]][leftIndex] = " ";
            game_board[index_space[0]][index_space[1]] = c;
            index_space[1] = leftIndex;
            return;
            }
        }
        int rightIndex = index_space[1] + 1;
        if (rightIndex <= 3) {
            if (game_board[index_space[0]][rightIndex].equals(c)) {
            game_board[index_space[0]][rightIndex] = " ";
            game_board[index_space[0]][index_space[1]] = c;
            index_space[1] = rightIndex;
            return;
            }
        }
        return;
    }
    //------------------################
    Boolean checkCondition(){
        String[][] sorted_board = {{"A","B","C","D"}, {"E","F","G","H"}, {"I","J","K"," "}};
        if (Arrays.deepEquals(game_board, sorted_board)){
            return true;
        }
        else{
            return false;
        }
    }
//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Saving $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    void Manage_file(String mode) {
        if (mode.equals("w")) {

        }
        else if (mode.equals("r")) {

        }
        else if (mode.equals("d")) {

        }
    }
//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ GUI $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$



//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ MAIN $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    public static void main(String[] args) {
        
    }
}