import java.util.Arrays;
import java.util.Random;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class SortingGame_SwingGUI extends JFrame implements MouseListener{
    
    private String[][] game_board = {{"A","B","C","D"}, {"E","F","G","H"}, {"I","J","K"," "}};
    private int[] index_space = {2,3};

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
            if (0<(int)random(0,2)) {
                remember[round%3] = moving;
            }
        }
    }
    //------------------################
    int random(int min,int max){
        Random num = new Random();
        return num.nextInt((max)-min) + min;
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

//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Mouse management $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

    String onClick(int mouse_x,int mouse_y){
        int row,col;
        row = (int)mouse_y/200;
        col = (int)mouse_x/200;
        moveChar(game_board[row][col]);
        if (checkCondition()){
            game_mode = "Win";
            Change_screen(win_screen);
        }
        return game_board[row][col];
    }
    //------------------################
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }
    @Override
    public void mousePressed(MouseEvent e) {
        // Todo 
        
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        // Todo  
        game_mode = "Play";
        int x = e.getX();
        int y = e.getY();
        this.onClick(x, y);
        Change_screen(play_screen);    
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        // Todo 
        
    }
    @Override
    public void mouseExited(MouseEvent e) {
        // Todo 
    }

//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ GUI $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    JPanel play_screen = new JPanel();
    JPanel menu_screen = new JPanel();
    JPanel win_screen = new JPanel();
    String game_mode = "Menu";

    void Setup(){
        this.setTitle("Sorting Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        this.setResizable(false);

        play_screen.setPreferredSize(new Dimension(800, 600));
        play_screen.addMouseListener(this);
        menu_screen.setPreferredSize(new Dimension(800, 600));
        menu_screen.addMouseListener(this);
        win_screen.setPreferredSize(new Dimension(800, 600));

        Change_screen(menu_screen);
    }

    void Change_screen(JPanel screen){
        this.getContentPane().removeAll();
        this.add(screen);
        this.pack();
        this.repaint();
    }

    //------------------################

    void Draw_game(Graphics g){
        //Graphics2D g2d = (Graphics2D) play_screen.getGraphics();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, 800, 600);
        g2d.setColor(Color.blue);
        for (int i=0;i<3;i++){
            g2d.drawLine(0, i*200, 800, i*200);
            for (int j=0;j<4;j++){
                g2d.drawLine(j*200, 0, j*200, 600);
                g2d.setFont(new Font("Calibri", Font.PLAIN, 150));
                g2d.drawString(game_board[i][j],60+(200*j),150+(200*i));
            }
        }
    }

    void Draw_menu(Graphics g){
        //Graphics2D g2d = (Graphics2D) menu_screen.getGraphics();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("Calibri", Font.PLAIN, 150));
        // New game butt =-=-=-=-=-= New game butt =-=-=-=-=-= New game butt =-=-=-=-=-=
        g2d.drawRect(250,150,300,100);
        g2d.drawString("New Game",250,150);
        // Continue butt =-=-=-=-=-= Continue butt =-=-=-=-=-= Continue butt =-=-=-=-=-=
        g2d.drawRect(250,350,300,100);
    }

    void Draw_win(Graphics g){
        //Graphics2D g2d = (Graphics2D) win_screen.getGraphics();
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, 800, 600);
        g2d.setColor(Color.black);
        g2d.setFont(new Font("Calibri", Font.PLAIN, 150));
        g2d.drawString("Win",250,150);

    }

    //------------------################

    public void paint(Graphics g){
        switch (game_mode) {
            case "Play":
                Draw_game(g);
                break;

            case "Menu":
                Draw_menu(g);
                break;
            case "Win":
                Draw_win(g);
                break;

            default:
                break;
        }

    }

    //------------------################

    SortingGame_SwingGUI(){
        Setup();
        this.setVisible(true);
    }
//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ MAIN $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    public static void main(String[] args) {
        SortingGame_SwingGUI game = new SortingGame_SwingGUI();
        game.shuffle_board();
    }
}
