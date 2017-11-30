package control;

import elements.Bola;
import elements.Muro;
import elements.Pacman;
import elements.Element;
import elements.Fantasma;
import static elements.Fantasma.MOVE_DOWN;
import static elements.Fantasma.MOVE_LEFT;
import static elements.Fantasma.MOVE_RIGHT;
import static elements.Fantasma.MOVE_UP;
import elements.Morango;
import elements.PowerPellet;
import utils.Consts;
import utils.Drawing;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Projeto de POO 2017
 * 
 * @author Luiz Eduardo
 * Baseado em material do Prof. Jose Fernando Junior
 */
public class GameScreen extends javax.swing.JFrame implements KeyListener {
    
    long tempoInicio = System.currentTimeMillis();
    
    private final Pacman lolo;
    private final Fantasma lulu;
    private final ArrayList<Element> elemArray;
    private final GameController controller = new GameController();
    private char map1[][]= {
        {'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
        {'#','.','.','.','.','.','.','.','.','#','#','.','.','.','.','.','.','.','*','#'},
        {'#','.','#','.','#','#','#','#','.','#','#','.','#','.','#','#','.','#','.','#'},
        {'#','.','#','.','.','.','.','.','.','#','#','.','#','.','#','#','.','#','.','#'},
        {'#','.','#','.','#','.','#','.','.','.','.','.','.','.','.','.','.','#','.','#'},
        {'#','.','#','.','#','.','#','.','#','#','#','#','.','.','#','#','.','#','.','#'},
        {'#','.','#','.','#','.','#','.','#','x','x','#','.','.','#','#','.','#','.','#'},
        {'.','.','#','.','#','.','#','.','#','x','x','#','.','.','.','.','.','#','.','.'},
        {'#','.','#','.','#','.','#','.','#','#','#','#','.','#','*','#','.','#','.','#'},
        {'#','.','.','.','.','.','.','.','.','.','.','.','.','#','.','#','.','#','.','#'},
        {'#','#','.','#','#','.','#','.','.','#','#','.','.','.','.','.','.','.','.','#'},
        {'#','#','.','#','#','.','#','.','#','#','#','#','.','#','#','#','.','#','.','#'},
        {'#','#','.','#','#','.','#','.','.','.','.','.','.','#','#','#','.','#','.','#'},
        {'#','#','.','#','#','.','.','.','.','.','.','.','.','.','.','.','.','#','.','#'},
        {'#','.','.','.','#','.','#','#','.','#','#','.','#','.','#','#','.','#','.','#'},
        {'#','.','#','.','#','.','#','#','.','*','.','.','#','.','#','#','.','.','.','#'},
        {'.','.','#','.','#','.','.','.','.','#','#','.','#','.','#','#','.','#','.','.'},
        {'#','.','#','.','#','.','#','#','.','#','#','.','.','.','#','#','.','#','.','#'},
        {'#','*','.','.','.','.','.','.','.','#','#','.','.','.','.','.','.','.','.','#'},
        {'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'}
    };

    public GameScreen() {
        Drawing.setGameScreen(this);
        initComponents();
        
        this.addKeyListener(this);   /*teclado*/
        
        /*Cria a janela do tamanho do tabuleiro + insets (bordas) da janela*/
        this.setSize(Consts.NUM_CELLS * Consts.CELL_SIZE + getInsets().left + getInsets().right,
                     Consts.NUM_CELLS * Consts.CELL_SIZE + getInsets().top + getInsets().bottom);

        elemArray = new ArrayList<Element>();

        /*Cria e adiciona elementos*/
        lolo = new Pacman("lolo.png");
        lolo.setPosition(0, 0);
        this.addElement(lolo);
        
        for (int i = 0; i < Consts.NUM_CELLS; i++) {
            for (int j = 0; j < Consts.NUM_CELLS; j++) {
                if(map1[i][j] == '#')
                {
                    Muro muro = new Muro("wall.png");
                    muro.setPosition(i, j);
                    this.addElement(muro);
                }
                if(map1[i][j] == '.')
                {
                    Bola bolinha = new Bola("dot.png");
                    bolinha.setPosition(i, j);
                    this.addElement(bolinha);
                }
                if(map1[i][j] == '*')
                {
                    PowerPellet pellet = new PowerPellet("power.png");
                    pellet.setPosition(i, j);
                    this.addElement(pellet);
                }
            }
        }
        
        lulu = new Fantasma("fantasma.png");
        lulu.setPosition(9, 9);
        this.addElement(lulu);
           
    }
    
    public final void addElement(Element elem) {
        elemArray.add(elem);
    }
    
    public void removeElement(Element elem) {
        elemArray.remove(elem);
    }
    
    @Override
    public void paint(Graphics gOld) {
        Graphics g = getBufferStrategy().getDrawGraphics();
        
        /*Criamos um contexto grafico*/
        Graphics g2 = g.create(getInsets().right, getInsets().top, getWidth() - getInsets().left, getHeight() - getInsets().bottom);

        if(Consts.numBolas !=0){
            for (int i = 0; i < Consts.NUM_CELLS; i++) {
                for (int j = 0; j < Consts.NUM_CELLS; j++) {
                    try {
                        Image newImage = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Consts.PATH + "preto.png");
                        g2.drawImage(newImage,
                                j * Consts.CELL_SIZE, i * Consts.CELL_SIZE, Consts.CELL_SIZE, Consts.CELL_SIZE, null);
                        if(lolo.comeFantasma == true){
                            lulu.changeImage("comida.png");
                            lulu.setMortal(true);
                        }

                    } catch (IOException ex) {
                        Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        else{
             for (int i = 0; i < Consts.NUM_CELLS; i++) {
                for (int j = 0; j < Consts.NUM_CELLS; j++) {
                    try {
                        Image newImage = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Consts.PATH + "win.png");
                        g2.drawImage(newImage,
                                j * Consts.CELL_SIZE, i * Consts.CELL_SIZE, Consts.CELL_SIZE, Consts.CELL_SIZE, null);

                    } catch (IOException ex) {
                        Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
        }
        
        this.controller.drawAllElements(elemArray, g2);
        this.controller.drawDynamicElements(elemArray, g2);
        this.controller.processAllElements(elemArray, lolo, lulu);
        this.setTitle("-> Cell: " + lolo.getStringPosition() + "                  Vidas: " + lolo.vidas + "                  Pontuação: " + lolo.pontuacao);
        
        
        g.dispose();
        g2.dispose();
        if (!getBufferStrategy().contentsLost()) {
            getBufferStrategy().show();
        }
    }
    
    public void go() {
        TimerTask task = new TimerTask() {
            
            public void run() {
                repaint();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, Consts.DELAY);
    }
    
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            lolo.changeImage("lolo_cima.png");
            lolo.setMovDirection(Pacman.MOVE_UP);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            lolo.changeImage("lolo_baixo.png");
            lolo.setMovDirection(Pacman.MOVE_DOWN);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            lolo.changeImage("lolo_esq.png");
            lolo.setMovDirection(Pacman.MOVE_LEFT);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            lolo.changeImage("lolo.png");
            lolo.setMovDirection(Pacman.MOVE_RIGHT);
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            lolo.setMovDirection(Pacman.STOP);
        }
        
        //repaint(); /*invoca o paint imediatamente, sem aguardar o refresh*/
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SCC0604 - Pacman");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocation(new java.awt.Point(20, 20));
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
