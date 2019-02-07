import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.*;
import java.awt.*;


public class GUI extends JFrame{

    int spacing=5;

    int neighb;

    int mx=-100 ,my=-100; //store mouse movements

    Random random=new Random();

    int[][] mines=new int[16][9]; //0 if mine,, 1 if no mine
    int[][] neighbors=new int[16][9]; // 0-8 -> no. of neighbours which are mines

    boolean[][] revealed=new boolean[16][9];
    boolean[][] flagged=new boolean[16][9];

    public GUI(){
        this.setTitle("K's Minesweeper");
        this.setSize(1280,800+29); //6 pixels for border and 29 for title
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);

        for(int i=0;i<16;i++){//ROW
            for(int j=0;j<9;j++){ //column ..16 x 9
               if(random.nextInt(100)<20){//0-99 random number..0.20 probability of mines
                   mines[i][j]=1;
               }else{
                   mines[i][j]=0;
               }
               revealed[i][j]=true;
               //my code
                neighb=0;
                for(int m=0;m<16;m++) {//ROW
                    for (int n = 0; n < 9; n++) {
                        if(!(m==i && n==j)){
                        if (isNeigh(i, j, m, n) == true)
                            neighb += 1;
                    }
                    }
                }
                neighbors[i][j]=neighb;
            }

        }


        Board board=new Board();
        this.setContentPane(board);

        Move move=new Move();
        this.addMouseMotionListener(move);

        Click click=new Click();
        this.addMouseListener(click);
    }

    public class Board extends JPanel{
         public void paintComponent(Graphics g){
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0,0,1280,800);
            for(int i=0;i<16;i++){//ROW
                for(int j=0;j<9;j++){ //column ..16 x 9
                    g.setColor(Color.GRAY);

                    if(mines[i][j]==1){
                        g.setColor(Color.YELLOW);
                    }
                    if(revealed[i][j]==true){
                        g.setColor(Color.WHITE);
                        if(mines[i][j]==1){
                            g.setColor(Color.RED);
                        }
                    }

                    if((mx>=spacing+i*80) && (mx<i*80+80-spacing) && (my>=spacing+j*80+80+26) && (my<spacing+j*80+80+26+80-2*spacing)){
                        g.setColor(Color.LIGHT_GRAY);
                    }
                    g.fillRect(spacing+i*80,spacing+j*80+80,80-2*spacing,80-2*spacing); //each box a square of 80px
                    if(revealed[i][j]==true){
                        g.setColor(Color.BLACK);
                        if(mines[i][j]==0 && neighbors[i][j]!=0) {
                            if(neighbors[i][j]==1){
                                g.setColor(Color.BLUE);
                            }else if(neighbors[i][j]==2){
                                g.setColor(Color.GREEN);
                            }else if(neighbors[i][j]==3){
                                g.setColor(new Color(0,123,0));
                            }else if(neighbors[i][j]==4){
                                g.setColor(new Color(0,0,128));
                            }else if(neighbors[i][j]==5){
                                g.setColor(new Color(170,34,34));
                            }else if(neighbors[i][j]==6){
                                g.setColor(new Color(72,209,204));
                            }else if(neighbors[i][j]==7){
                                g.setColor(Color.BLACK);
                            }else if(neighbors[i][j]==8){
                                g.setColor(Color.DARK_GRAY);
                            }

                            g.setFont(new Font("ARIAL", Font.BOLD, 40));
                            g.drawString(Integer.toString(neighbors[i][j]), i * 80 + 27, j * 80 + 80 + 55);
                        }
                        else if(neighbors[i][j]==1){
                        g.fillRect(i*80+10+20,j*80+80+20,20,40);
                        g.fillRect(i*80+20,j*80+80+10+20,40,20);
                        g.fillRect(i*80+5+20,j*80+80+5+20,30,30);
                      }
                    }
                }
            }
         }
    }

    public class Move implements MouseMotionListener{

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mx=e.getX();
            my=e.getY();
        }
    }

    public class Click implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            if(inBoxX()!=-1 && inBoxY()!=-1){
                revealed[inBoxX()][inBoxY()]=true;
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    public int inBoxX(){ //find box clicked
        for(int i=0;i<16;i++){//ROW
            for(int j=0;j<9;j++){ //column ..16 x 9
                if((mx>=spacing+i*80) && (mx<i*80+80-spacing) && (my>=spacing+j*80+80+26) && (my<j*80+80+26+80-spacing)){
                    return i;
                }
            }
        }
        return -1;
    }

    public int inBoxY(){//find box clicked
        for(int i=0;i<16;i++){//ROW
            for(int j=0;j<9;j++){ //column ..16 x 9
                if((mx>=spacing+i*80) && (mx<i*80+80-spacing) && (my>=spacing+j*80+80+26) && (my<spacing+j*80+80+26+80-2*spacing)){
                    return j;
                }
            }
        }
        return -1;
    }

    public boolean isNeigh(int mX, int mY, int cX, int cY){//check if two boxes are neighbours
        if(mX-cX<2 && mX-cX>-2 && mY-cY<2 && mY-cY>-2 && mines[cX][cY]==1){
            return true;
        }
        return false;
    }
}
