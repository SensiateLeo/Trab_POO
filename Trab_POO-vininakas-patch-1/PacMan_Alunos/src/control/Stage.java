package control;

import elements.Bola;
import elements.Muro;
import elements.Pacman;
import elements.Element;
import elements.Blinky;
import elements.Clyde;
import elements.Inky;
import elements.Morango;
import elements.Pinky;
import elements.PowerPellet;
import java.awt.Color;
import java.awt.Font;
import utils.Consts;
import utils.Drawing;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
public class Stage extends javax.swing.JFrame implements KeyListener {
    
    int controleFantasma = 0;
    long tempoInicioFantasma = 0;
    long tempoDecorrente = 0 ;
    int faseAtual = 1;
    
    private final Pacman lolo;
    private final Blinky lulu;
    private final Inky lala;
    private final Pinky lele;
    private final Clyde lili;
    private final ArrayList<Element> elemArray;
    private final GameController controller = new GameController();
    private static int tamlim;
    private static int tamcol;
    
    File arquivo = new File("map1.txt");
    private final char map1[][] = leitura(arquivo);
   
        
    public Stage() {
        Drawing.setGameScreen(this);
        initComponents();
        
        this.addKeyListener(this);   /*teclado*/
                
        /*Cria a janela do tamanho do tabuleiro + insets (bordas) da janela*/
        this.setSize(Consts.NUM_CELLS * Consts.CELL_SIZE + getInsets().left + getInsets().right,
                     Consts.NUM_CELLS * Consts.CELL_SIZE + getInsets().top + getInsets().bottom);

        elemArray = new ArrayList<Element>();

        /*Cria e adiciona elementos*/
        lolo = new Pacman("lolo.png");
        lolo.setPosition(2, 2);
        this.addElement(lolo);
        
        lulu = new Blinky("fantasma.png");
        lulu.setPosition(10, 9);
        this.addElement(lulu);
        
        lala = new Inky("inky.png");
        lala.setPosition(5,9);
        this.addElement(lala);
        
        lele = new Pinky("pinky.png");
        lele.setPosition(5,12);
        this.addElement(lele);
        
        lili = new Clyde("clyde.png");
        lili.setPosition(10,12);
        this.addElement(lili);
           
        
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

        for (int i = 0; i < Consts.NUM_CELLS; i++) {
            for (int j = 0; j < Consts.NUM_CELLS; j++) {
                try {
                    Image newImage = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Consts.PATH + "preto.png");
                    g2.drawImage(newImage,
                            j * Consts.CELL_SIZE, i * Consts.CELL_SIZE, Consts.CELL_SIZE, Consts.CELL_SIZE, null);

                } catch (IOException ex) {
                    Logger.getLogger(Stage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        if(lolo.comeFantasma == true)
        {
            if(controleFantasma==0)
            {
                tempoInicioFantasma = System.currentTimeMillis()/1000;
                controleFantasma = 1;
            }
            lulu.changeImage("comida.png");
            lala.changeImage("comida.png");
            lele.changeImage("comida.png");
            lili.changeImage("comida.png");
            lili.setMortal(true);
            lele.setMortal(true);
            lulu.setMortal(true);
            lala.setMortal(true);
            
            if(tempoDecorrente-tempoInicioFantasma >= 7)
            {
                lulu.changeImage("fantasma.png");
                lele.changeImage("pinky.png");
                lili.changeImage("clyde.png");
                lala.changeImage("inky.png");
                lili.setMortal(false);
                lele.setMortal(false);
                lulu.setMortal(false);
                lala.setMortal(false);
                lolo.comeFantasma = false;
                controleFantasma = 0;
                Consts.numFantasmas = 4;
            }
        }
            tempoDecorrente = (System.currentTimeMillis()/1000);
            System.out.println("Tempo FIM : " + tempoDecorrente);
             
           
            
        this.controller.drawAllElements(elemArray, g2);
        this.controller.drawDynamicElements(elemArray, g2);
        this.controller.processAllElements(elemArray, lolo, lulu, lala, lele, lili);
        this.setTitle("-> Cell: " + lolo.getStringPosition());
               
        if(lolo.vidas==5)
        {
            g2.setColor(Color.YELLOW);
            g2.fillOval(90, 15, 20, 20);
            g2.drawOval(90,15, 20, 20);
            g2.setColor(Color.YELLOW);
            g2.fillOval(120, 15, 20, 20);
            g2.drawOval(120,15, 20, 20);
            g2.setColor(Color.YELLOW);
            g2.fillOval(150, 15, 20, 20);
            g2.drawOval(150,15, 20, 20);
            g2.setColor(Color.YELLOW);
            g2.fillOval(180, 15, 20, 20);
            g2.drawOval(180,15, 20, 20);
            g2.setColor(Color.YELLOW);
            g2.fillOval(210, 15, 20, 20);
            g2.drawOval(210,15, 20, 20);
        }
        if(lolo.vidas==4)
        {
            g2.setColor(Color.YELLOW);
            g2.fillOval(90, 15, 20, 20);
            g2.drawOval(90,15, 20, 20);
            g2.setColor(Color.YELLOW);
            g2.fillOval(120, 15, 20, 20);
            g2.drawOval(120,15, 20, 20);
            g2.setColor(Color.YELLOW);
            g2.fillOval(150, 15, 20, 20);
            g2.drawOval(150,15, 20, 20);
            g2.setColor(Color.YELLOW);
            g2.fillOval(180, 15, 20, 20);
            g2.drawOval(180,15, 20, 20);
        }
        
        if(lolo.vidas==3)
        {
            g2.setColor(Color.YELLOW);
            g2.fillOval(90, 15, 20, 20);
            g2.drawOval(90,15, 20, 20);
            g2.setColor(Color.YELLOW);
            g2.fillOval(120, 15, 20, 20);
            g2.drawOval(120,15, 20, 20);
            g2.setColor(Color.YELLOW);
            g2.fillOval(150, 15, 20, 20);
            g2.drawOval(150,15, 20, 20);   
        }
        else
        {
            if(lolo.vidas==2)
            {
                g2.setColor(Color.YELLOW);
                g2.fillOval(90, 15, 20, 20);
                g2.drawOval(90,15, 20, 20);
                g2.setColor(Color.YELLOW);
                g2.fillOval(120, 15, 20, 20);
                g2.drawOval(120,15, 20, 20);
            }
            else
            {
                if(lolo.vidas==1)
                {
                    g2.setColor(Color.YELLOW);
                    g2.fillOval(90, 15, 20, 20);
                }
            }
        }
        String vida = "Vida ";
        String pontos = "Pontuacao ";
        String fase = "Fase ";
        pontos = pontos + Integer.toString(lolo.pontuacao);
        fase = fase + Integer.toString(faseAtual);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font(("Comics Sans"),Font.BOLD,20));
        g2.drawString(fase, 515, 35);
        g2.drawString(pontos,245, 35);
        g2.drawString(vida,40, 35);
        
        if(lolo.pontuacao == 10000 || lolo.pontuacao == 20000 || lolo.pontuacao == 30000|| lolo.pontuacao == 40000|| lolo.pontuacao == 50000){
            lolo.vidas = lolo.vidas + 1;
        }
        
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
            if(lulu.isMortal()==false){
                lulu.setMovDirection(Pacman.MOVE_UP);
                lili.setMovDirection(Pacman.MOVE_UP);
            }
            else{
                lulu.setMovDirection(Pacman.MOVE_DOWN);
                lili.setMovDirection(Pacman.MOVE_DOWN);
            } 
            Random random = new Random();
            int y = random.nextInt(2);
            lele.setMovDirection(y+1);
            int x = random.nextInt(4);
            lala.setMovDirection(x+1);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            lolo.changeImage("lolo_baixo.png");
            lolo.setMovDirection(Pacman.MOVE_DOWN);
            if(lulu.isMortal()==false){
                lulu.setMovDirection(Pacman.MOVE_DOWN);
                lili.setMovDirection(Pacman.MOVE_DOWN);
            }
            else{
                lulu.setMovDirection(Pacman.MOVE_UP);
                lili.setMovDirection(Pacman.MOVE_UP);
            } 
            Random random = new Random();
            int y = random.nextInt(2);
            lele.setMovDirection(y+1);
            int x = random.nextInt(4);
            lala.setMovDirection(x+1);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            lolo.changeImage("lolo_esq.png");
            lolo.setMovDirection(Pacman.MOVE_LEFT);
            if(lulu.isMortal()==false){
                lulu.setMovDirection(Pacman.MOVE_LEFT);
                lili.setMovDirection(Pacman.MOVE_LEFT);
            }
            else{
                lulu.setMovDirection(Pacman.MOVE_RIGHT);
                lili.setMovDirection(Pacman.MOVE_RIGHT);
            }      
            Random random = new Random();
            int y = random.nextInt(2);
            lele.setMovDirection(y+3);
            int x = random.nextInt(4);
            lala.setMovDirection(x+1);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            lolo.changeImage("lolo.png");
            lolo.setMovDirection(Pacman.MOVE_RIGHT);
            if(lulu.isMortal()==false){
                lulu.setMovDirection(Pacman.MOVE_RIGHT);
                lili.setMovDirection(Pacman.MOVE_RIGHT);
            }
            else{
                lulu.setMovDirection(Pacman.MOVE_LEFT);
                lili.setMovDirection(Pacman.MOVE_LEFT);
            }     
            Random random = new Random();
            int y = random.nextInt(2);
            lele.setMovDirection(y+3);
            int x = random.nextInt(4);
            lala.setMovDirection(x+1);
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
    
    public char[][] leitura(File arq)
    {
        int i,j;
        try
        {
            FileReader fr = new FileReader(arq);
            BufferedReader br = new BufferedReader(fr);
            tamlim = Integer.parseInt(br.readLine());
            tamcol = Integer.parseInt(br.readLine());
            fr.close();
            br.close();
        } 
        catch (FileNotFoundException ex) 
        {
            System.err.println(ex.getMessage());
        } 
        catch (IOException ex) 
        {
            System.err.println(ex.getMessage());
        }
        
        char map[][] = new char[tamlim][tamcol]; 
 
        try 
        {
            FileReader fr = new FileReader("map1.txt");
            BufferedReader br = new BufferedReader(fr);
            tamlim = Integer.parseInt(br.readLine());
            tamcol = Integer.parseInt(br.readLine());
            i = 0;
            while (br.ready()) 
            {
                String linha = br.readLine();
                for(j=0;j<tamcol;j++)
                {
                   map[i][j] = linha.charAt(j);
                }
                i++;
            }
            br.close();
            fr.close();
        } 
        catch (FileNotFoundException ex) 
        {
            System.err.println(ex.getMessage());
        } catch (IOException ex) 
        {
            System.err.println(ex.getMessage());
        }
        return map;
    }
}
