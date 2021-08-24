import java.util.Arrays;
import java.util.Random;

import java.awt.event.*;
import java.io.File;
import java.awt.*;
import javax.swing.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

public class SortingGame_SwingGUI extends JFrame implements MouseListener{
    
   final private String[][] game_board = {{"A","B","C","D"}, {"E","F","G","H"}, {"I","J","K"," "}};
   final private int[] index_space = {2,3};

// $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Game's method $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

     void shuffle_board(){ // method สำหรับสุ่มตาราง
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
    void moveChar(String c){ // method สำหรับเลื่อนตัวอักษร
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
    Boolean checkCondition(){ //method สำหรับเช็คว่าเล่นชนะแล้วหรือยัง หากชนะจะ return true
        String[][] sorted_board = {{"A","B","C","D"}, {"E","F","G","H"}, {"I","J","K"," "}};
        if (Arrays.deepEquals(game_board, sorted_board)){
            return true;
        }
        else{
            return false;
        }
    }
//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Saving $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    void Manage_file(String mode) { //method สำหรับ save file
        if (mode.equals("w")) { // mode "w" เป็นโหมดสำหรับการเขียน file
            try {
                DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
                Document document = documentBuilder.newDocument();
                Element root = document.createElement("ABCBlockMAP");
                document.appendChild(root);
                Element Map = document.createElement("Map");
                root.appendChild(Map);
                for (int i=0;i < 3;i++) {
                    Element Row = document.createElement("Row" + (i+1));
                    String line = "";
                    for (int j=0;j < 4;j++) {
                        line += game_board[i][j];
                    }
                    Row.appendChild(document.createTextNode(line));
                    Map.appendChild(Row);
                }
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                DOMSource domSource = new DOMSource(document);
                StreamResult streamResult = new StreamResult(new File("./save.xml"));
                transformer.transform(domSource, streamResult);
            } catch (ParserConfigurationException pce) {
                pce.printStackTrace();
            } catch (TransformerException tfe) {
                tfe.printStackTrace();
            }
        }
        else if (mode.equals("r")) { //mode "r" โหมดสำหรับการอ่าน file
            try {
                File file = new File("./save.xml");
                DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
                Document document = documentBuilder.parse(file);
                document.getDocumentElement().normalize();
                NodeList map = document.getElementsByTagName("Map");
                Node node = map.item(0);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    for (int i = 0;i < 3;i++) {
                        String[] ch = eElement.getElementsByTagName("Row"+(i+1)).item(0).getTextContent().split("");
                        for (int j = 0;j < 4;j++) {
                            game_board[i][j] = ch[j];
                            if (game_board[i][j].equals(" ")) {
                                index_space[0] = i;
                                index_space[1] = j;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (mode.equals("d")) { //mode "d" โหมดสำหรับลบ file
            try {
                final String[][] sorted_board = {{"A","B","C","D"}, {"E","F","G","H"}, {"I","J","K"," "}};
                DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
                Document document = documentBuilder.newDocument();
                Element root = document.createElement("ABCBlockMAP");
                document.appendChild(root);
                Element Map = document.createElement("Map");
                root.appendChild(Map);
                for (int i=0;i < 3;i++) {
                    Element Row = document.createElement("Row" + (i+1));
                    String line = "";
                    for (int j=0;j < 4;j++) {
                        line += sorted_board[i][j];
                    }
                    Row.appendChild(document.createTextNode(line));
                    Map.appendChild(Row);
                }
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                DOMSource domSource = new DOMSource(document);
                StreamResult streamResult = new StreamResult(new File("./save.xml"));
                transformer.transform(domSource, streamResult);
            } catch (ParserConfigurationException pce) {
                pce.printStackTrace();
            } catch (TransformerException tfe) {
                tfe.printStackTrace();
            }
        }
    }

    Boolean Check_save_exists(){ // สำหรับเช็คว่ามี file save อยู่หรือไม่
        File saveFile = new File("save.xml");
        boolean exists = saveFile.exists();
        if (exists) {
            Manage_file("r");
            if (checkCondition()) {
                return false;
            } else {
                return true;
            }
        }
        else {
            Manage_file("d");
            return false;
        }

    }

//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Mouse management $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

    void onClick(int mouse_x,int mouse_y){
        int row,col;
        row = (int)mouse_y/200;
        col = (int)mouse_x/200;
        moveChar(game_board[row][col]);
        Manage_file("w");
    }
    //------------------################
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }
    @Override
    public void mousePressed(MouseEvent e) {

    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if (game_mode.equals("Menu")){
            if (screen.getComponent(0).equals(e.getComponent())){
                shuffle_board();
                Manage_file("w");
                game_mode = "Play";
            }
            else if (Check_save_exists()){
                // TODO Continue from saved file.
                if (screen.getComponent(1).equals(e.getComponent())){
                    Manage_file("r");
                    game_mode = "Play";
                }
            }
        }
        else if (game_mode.equals("Play")){
            int x = e.getX();
            int y = e.getY();
            this.onClick(x, y);
            if (checkCondition()){
                game_mode = "Win";
            }
        }
        else if (game_mode.equals("Win")){
            game_mode = "Menu";
        }
        this.repaint();
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        if (game_mode.equals("Menu")){
            if (screen.getComponent(0).equals(e.getComponent())){
                Graphics2D g2d = (Graphics2D) screen.getGraphics();
                g2d.setStroke(new BasicStroke(3));
                g2d.setFont(new Font("Calibri", Font.PLAIN, 50));
                g2d.setColor(Color.decode("#33D81A"));
                g2d.fillRect(250, 150, 300, 100);
                g2d.setColor(Color.black);
                g2d.drawRect(250,150,300,100);
                g2d.drawString("New Game",300,215);
            }
            else if (Check_save_exists()&&screen.getComponent(1).equals(e.getComponent())){
                Graphics2D g2d = (Graphics2D) screen.getGraphics();
                g2d.setStroke(new BasicStroke(3));
                g2d.setFont(new Font("Calibri", Font.PLAIN, 50));

                g2d.setColor(Color.decode("#33D81A"));
                g2d.fillRect(250, 350, 300, 100);

                g2d.setColor(Color.black);
                g2d.drawRect(250,350,300,100);
                g2d.drawString("Continue",315,415);
            }
        } 
    }
    @Override
    public void mouseExited(MouseEvent e) {
        if (game_mode.equals("Menu")&&(screen.getComponent(0).equals(e.getComponent()) || screen.getComponent(1).equals(e.getComponent()))){
            this.repaint(); 
        }
    }
//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ GUI $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    JPanel screen = new JPanel();
    String game_mode = "Menu";

    void Draw_game(){
        screen.removeAll();
        Graphics2D g2d = (Graphics2D) screen.getGraphics();
        g2d.setStroke(new BasicStroke(2));
        for (int i=0;i<3;i++){
            g2d.setColor(Color.black);
            g2d.drawLine(0, i*200, 800, i*200);
            for (int j=0;j<4;j++){
                g2d.setColor(Color.black);
                g2d.drawLine(j*200, 0, j*200, 600);
                g2d.setColor(Color.blue);
                g2d.setFont(new Font("Calibri", Font.PLAIN, 150));
                g2d.drawString(game_board[i][j],60+(200*j),150+(200*i));
            }
        }
    }
    
    void Draw_menu(){ // วาดหน้าเมนูสำหรับเลือก newgame ถ้ามี save ก็จะมีให้เลือก continue
        screen.removeAll();
        JLabel nLb = new JLabel();
        JLabel cLb = new JLabel();

        nLb.setBounds(250, 150, 300, 100);
        nLb.addMouseListener(this);

        cLb.setBounds(250, 350, 300, 100);
        cLb.addMouseListener(this);

        screen.add(nLb);
        screen.add(cLb);

        Graphics2D g2d = (Graphics2D) screen.getGraphics();
        g2d.setStroke(new BasicStroke(3));
        g2d.setFont(new Font("Calibri", Font.PLAIN, 50));

        g2d.setColor(Color.decode("#2AEA0E"));
        g2d.fillRect(250, 150, 300, 100);

        g2d.setColor(Color.black);
        g2d.drawRect(250,150,300,100);
        g2d.drawString("New Game",300,215);
        if (Check_save_exists()){
            g2d.setColor(Color.decode("#2AEA0E"));
            g2d.fillRect(250, 350, 300, 100);

            g2d.setColor(Color.black);
            g2d.drawRect(250,350,300,100);
            g2d.drawString("Continue",315,415);
        }
    }

    void Draw_win(){ //หากเล่นเกมจนชนะจะขึ้นว่า You win
        Graphics2D g2d = (Graphics2D) screen.getGraphics();
        g2d.setColor(Color.ORANGE);
        g2d.setFont(new Font("Calibri", Font.PLAIN, 150));
        g2d.drawString("You Win",150,325);

        g2d.setColor(Color.gray);
        g2d.setFont(new Font("Calibri", Font.PLAIN, 20));
        g2d.drawString("Click for back to main menu.",300,500);
    }

    //------------------################

    public void paint(Graphics g){
        super.paint(g);
        switch (game_mode) {
            case "Play":
                Draw_game();
                break;
            case "Menu":
                Draw_menu();
                break;
            case "Win":
                Draw_win();
                break;
            default:
                break;
        }
    }

    //------------------################

    SortingGame_SwingGUI(){
        this.setTitle("Sorting Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        this.setResizable(false);

        screen.setPreferredSize(new Dimension(800, 600));
        screen.addMouseListener(this);
        this.add(screen);
        this.pack();
        this.setVisible(true);

    }
//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ MAIN $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    public static void main(String[] args) {
        new SortingGame_SwingGUI();
    }
}
