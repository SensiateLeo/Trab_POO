package control;

import elements.Dot;
import elements.Muro;
import elements.Pacman;
import elements.Element;
import elements.Cereja;
import elements.Fantasma;
import elements.Morango;
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
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Projeto de POO 2017
 * 
 * @authors Hiago de Franco, Leonardo Sensiate, Mateus Castilho Leite e Vinícius Nakasone.
 * Baseado em material do Prof. Jose Fernando Junior disponibilizado pelo professor Luiz Eduardo.
 */
public class Stage extends javax.swing.JFrame implements KeyListener {
    
    int controleTempCereja = 0 ;
    int controleTempMorango = 0;
    int controleFantasma = 0;
    
    long tempoInicioEspecial = System.currentTimeMillis()/1000; ;
    long tempoFimCereja = 0;
    long tempoFimMorango = 0;
    long tempoDecorrente = 0;
    int faseAtual = 1;
    
    private final Pacman pac;
    private final Fantasma blinky;
    private final Fantasma inky;
    private final Fantasma pinky;
    private final Fantasma clyde;
    private final ArrayList<Element> elemArray;
    private final GameController controller = new GameController();
    private static int tamlim;
    private static int tamcol;
    
    File arquivo = new File("map3.txt");
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
        pac = new Pacman("lolo.png");
        pac.setPosition(2, 2);
        this.addElement(pac);
        
        blinky = new Fantasma("fantasma.png");
        blinky.setPosition(9, 9);
        this.addElement(blinky);
        
        inky = new Fantasma("inky.png");
        inky.setPosition(7,9);
        this.addElement(inky);
        
        pinky = new Fantasma("pinky.png");
        pinky.setPosition(7,12);
        this.addElement(pinky);
        
        clyde = new Fantasma("clyde.png");
        clyde.setPosition(9,12);
        this.addElement(clyde);
        
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
                    Dot bolinha = new Dot("dot.png");
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

        Cereja cereja = new Cereja("cereja.png");
        Morango morango = new Morango("morango.png");
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
        
        tempoDecorrente = (System.currentTimeMillis()/1000);
        
        if(pac.comeFantasma == true)
        {
            if(controleFantasma==0)
            {
                controleFantasma = 1;
            }
            blinky.changeImage("comida.png");
            inky.changeImage("comida.png");
            pinky.changeImage("comida.png");
            clyde.changeImage("comida.png");
            clyde.setMortal(true);
            pinky.setMortal(true);
            blinky.setMortal(true);
            inky.setMortal(true);
            
            if(tempoDecorrente-Consts.tempoInicioFantasma >= 7)
            {
                blinky.changeImage("fantasma.png");
                pinky.changeImage("pinky.png");
                clyde.changeImage("clyde.png");
                inky.changeImage("inky.png");
                clyde.setMortal(false);
                pinky.setMortal(false);
                blinky.setMortal(false);
                inky.setMortal(false);
                pac.comeFantasma = false;
                controleFantasma = 0;
                Consts.numFantasmas = 4;
                if(blinky.getVivo() == false){
                    blinky.setPosition(9, 9);
                    blinky.setVivo(true);
                    this.addElement(blinky);
                }

                if(inky.getVivo() == false){
                    inky.setPosition(7,9);
                    inky.setVivo(true);
                    this.addElement(inky);
                }

                if(pinky.getVivo() == false){
                    pinky.setPosition(7,12);
                    pinky.setVivo(true);
                    this.addElement(pinky);
                }

                if(clyde.getVivo() == false){
                    clyde.setPosition(9,12);
                    clyde.setVivo(true);
                    this.addElement(clyde);
                }
            }
        }
        if(((tempoDecorrente-tempoInicioEspecial)%30 == 0) && (tempoDecorrente-tempoInicioEspecial)!= 0)
        { 
            if(controleTempCereja==0)
            {
                tempoFimCereja = System.currentTimeMillis()/1000;
                Random r = new Random();
                int x = r.nextInt(22);
                int y = r.nextInt(22);

                if(faseAtual==1)
                {
                    while(map1[x][y]!='.')
                    {
                        x = r.nextInt(Consts.NUM_CELLS);
                        y = r.nextInt(Consts.NUM_CELLS);
                        System.out.println(map1[x][y]);
                    }
                    cereja.setPosition(x, y);
                    this.addElement(cereja);                   
                }
                controleTempCereja=1;
            }     
        }
        if((tempoDecorrente-tempoFimCereja)>=15 && controleTempCereja==1)
        {
            controleTempCereja = 0;
            cereja.removeCereja(elemArray);
        }
        
        if(((tempoDecorrente-tempoInicioEspecial)%75 == 0) && (tempoDecorrente-tempoInicioEspecial)!= 0)
        { 
            
            if(controleTempMorango==0)
            {
                tempoFimMorango = System.currentTimeMillis()/1000;
                Random r = new Random();
                int x = r.nextInt(Consts.NUM_CELLS);
                int y = r.nextInt(Consts.NUM_CELLS);

                if(faseAtual==1)
                {
                    while(map1[x][y]!='.')
                    {
                        x = r.nextInt(Consts.NUM_CELLS);
                        y = r.nextInt(Consts.NUM_CELLS);
                        System.out.println(map1[x][y]);
                    }
                    morango.setPosition(x, y);
                    this.addElement(morango);                   
                }
                controleTempMorango=1;
            }     
        }
        if((tempoDecorrente-tempoFimMorango)>=15 && controleTempMorango==1)
        {
            controleTempMorango = 0;
            morango.removeMorango(elemArray);
        }
            
        this.controller.drawAllElements(elemArray, g2);
        this.controller.drawDynamicElements(elemArray, g2);
        this.controller.processAllElements(elemArray, pac, blinky, inky, pinky, clyde);
        this.setTitle("-> Cell: " + pac.getStringPosition());
               
        if(pac.vidas==5)
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
        if(pac.vidas==4)
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
        
        if(pac.vidas==3)
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
            if(pac.vidas==2)
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
                if(pac.vidas==1)
                {
                    g2.setColor(Color.YELLOW);
                    g2.fillOval(90, 15, 20, 20);
                }
            }
        }
        String tempo = "Timer:  ";
        String vida = "Vida ";
        String pontos = "Pontuacao ";
        String fase = "Fase ";
        tempo = tempo + Integer.toString((int) (tempoDecorrente-tempoInicioEspecial));
        pontos = pontos + Integer.toString(pac.pontuacao);
        fase = fase + Integer.toString(faseAtual);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font(("Comics Sans"),Font.BOLD,20));
        g2.drawString(fase, 515, 35);
        g2.drawString(pontos,245, 35);
        g2.drawString(vida,40, 35);
        g2.drawString(tempo,260,595);
        
        
        if(pac.pontuacao >= 10000)
        {
            System.out.println("entro aki");            
            pac.vidas = pac.vidas + 1;
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
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            pac.changeImage("lolo_cima.png");
            pac.setMovDirection(Pacman.MOVE_UP);
            Random random = new Random();
            if(blinky.isMortal()==false){
                blinky.setMovDirection(Pacman.MOVE_UP);
            }
            else{
                blinky.setMovDirection(Pacman.MOVE_DOWN);
            } 
            if(clyde.isMortal() == false){
                if(clyde.distElem(pac) >= 64){
                    clyde.setMovDirection(Pacman.MOVE_UP);
                }
                else{
                    int z = random.nextInt(4);
                    clyde.setMovDirection(z+1);
                }
            }
            else{
                if(clyde.distElem(pac) >= 64){
                    clyde.setMovDirection(Pacman.MOVE_DOWN);
                }
                else{
                    int z = random.nextInt(4);
                    clyde.setMovDirection(z+1);
                }   
            }
            if(inky.isMortal()==false){
                if(inky.distElem(blinky) >= 64){
                    int x = random.nextInt(4);
                    inky.setMovDirection(x+1);
                }
                else{
                    inky.setMovDirection(Pacman.MOVE_UP);
                }
            }
            else{
                if(inky.distElem(blinky) >= 64){
                    int x = random.nextInt(4);
                    inky.setMovDirection(x+1);
                }
                else{
                    inky.setMovDirection(Pacman.MOVE_DOWN);
                }      
            }
            int y = random.nextInt(2);
            pinky.setMovDirection(y+1);

        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            pac.changeImage("lolo_baixo.png");
            pac.setMovDirection(Pacman.MOVE_DOWN);
                Random random = new Random();
            if(blinky.isMortal()==false){
                blinky.setMovDirection(Pacman.MOVE_DOWN);
            }
            else{
                blinky.setMovDirection(Pacman.MOVE_UP);
            } 
            if(clyde.isMortal() == false){
                if(clyde.distElem(pac) >= 64){
                    clyde.setMovDirection(Pacman.MOVE_DOWN);
                }
                else{
                    int z = random.nextInt(4);
                    clyde.setMovDirection(z+1);
                }
            }
            else{
                if(clyde.distElem(pac) >= 64){
                    clyde.setMovDirection(Pacman.MOVE_UP);
                }
                else{
                    int z = random.nextInt(4);
                    clyde.setMovDirection(z+1);
                }   
            }
            if(inky.isMortal()==false){
                if(inky.distElem(blinky) >= 64){
                    int x = random.nextInt(4);
                    inky.setMovDirection(x+1);
                }
                else{
                    inky.setMovDirection(Pacman.MOVE_DOWN);
                }
            }
            else{
                if(inky.distElem(blinky) >= 64){
                    int x = random.nextInt(4);
                    inky.setMovDirection(x+1);
                }
                else{
                    inky.setMovDirection(Pacman.MOVE_UP);
                }      
            }
            int y = random.nextInt(2);
            pinky.setMovDirection(y+1);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            pac.changeImage("lolo_esq.png");
            pac.setMovDirection(Pacman.MOVE_LEFT);
            Random random = new Random();
            if(blinky.isMortal()==false){
                blinky.setMovDirection(Pacman.MOVE_LEFT);
            }
            else{
                blinky.setMovDirection(Pacman.MOVE_RIGHT);
            } 
            if(clyde.isMortal() == false){
                if(clyde.distElem(pac) >= 64){
                    clyde.setMovDirection(Pacman.MOVE_LEFT);
                }
                else{
                    int z = random.nextInt(4);
                    clyde.setMovDirection(z+1);
                }
            }
            else{
                if(clyde.distElem(pac) >= 64){
                    clyde.setMovDirection(Pacman.MOVE_RIGHT);
                }
                else{
                    int z = random.nextInt(4);
                    clyde.setMovDirection(z+1);
                }   
            }
            if(inky.isMortal()==false){
                if(inky.distElem(blinky) >= 64){
                    int x = random.nextInt(4);
                    inky.setMovDirection(x+1);
                }
                else{
                    inky.setMovDirection(Pacman.MOVE_LEFT);
                }
            }
            else{
                if(inky.distElem(blinky) >= 64){
                    int x = random.nextInt(4);
                    inky.setMovDirection(x+1);
                }
                else{
                    inky.setMovDirection(Pacman.MOVE_RIGHT);
                }      
            }
            int y = random.nextInt(2);
            pinky.setMovDirection(y+3);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            pac.changeImage("lolo.png");
            pac.setMovDirection(Pacman.MOVE_RIGHT);
            Random random = new Random();
            if(blinky.isMortal()==false){
                blinky.setMovDirection(Pacman.MOVE_RIGHT);
            }
            else{
                blinky.setMovDirection(Pacman.MOVE_LEFT);
            } 
            if(clyde.isMortal() == false){
                if(clyde.distElem(pac) >= 64){
                    clyde.setMovDirection(Pacman.MOVE_RIGHT);
                }
                else{
                    int z = random.nextInt(4);
                    clyde.setMovDirection(z+1);
                }
            }
            else{
                if(clyde.distElem(pac) >= 64){
                    clyde.setMovDirection(Pacman.MOVE_LEFT);
                }
                else{
                    int z = random.nextInt(4);
                    clyde.setMovDirection(z+1);
                }   
            }
            if(inky.isMortal()==false){
                if(inky.distElem(blinky) >= 64){
                    int x = random.nextInt(4);
                    inky.setMovDirection(x+1);
                }
                else{
                    inky.setMovDirection(Pacman.MOVE_RIGHT);
                }
            }
            else{
                if(inky.distElem(blinky) >= 64){
                    int x = random.nextInt(4);
                    inky.setMovDirection(x+1);
                }
                else{
                    inky.setMovDirection(Pacman.MOVE_LEFT);
                }      
            }
            int y = random.nextInt(2);
            pinky.setMovDirection(y+3);
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            pac.setMovDirection(Pacman.STOP);
        }
        else if (e.getKeyCode() == KeyEvent.VK_F10) 
        {
            salvar();
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
            FileReader fr = new FileReader(arq);
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
            for(i=0;i<tamlim;i++)
            {
                for(j=0;j<tamcol;j++)
                {
                    System.out.print(map[i][j]);
                }
                System.out.println();
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
    private void salvar()
    {
        //1 - Crie um objeto FileOutputStream
        FileOutputStream fileStream;
        ObjectOutputStream os;
        try {
            fileStream = new FileOutputStream("Pacman");
            try {
                //2 - Crie um ObjectOutputStream
                os = new ObjectOutputStream(fileStream);
                //3 - Grave os objetos
                for (int i = 0; i < elemArray.size(); i++)
                {
                    try 
                    {
                        os.writeObject(elemArray.get(i)); //Serializa o objeto referenciado por user e grava no arquivo
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                try {
                    //4 - Feche ObjectOutputStream
                    os.close();
                } catch (IOException ex) 
                {
                    System.out.println(ex.getMessage());
                }
            } catch (IOException ex) 
            {
                System.out.println(ex.getMessage());
            }

        } catch (FileNotFoundException ex) 
        {
            System.out.println(ex.getMessage());
        }
    }
    
}
