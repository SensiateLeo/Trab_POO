package utils;

import control.Stage;
import java.awt.Graphics;
import javax.swing.ImageIcon;

/**
 * Projeto de POO 2017
 * 
 * @authors Hiago de Franco, Leonardo Sensiate, Mateus Castilho Leite e Vinícius Nakasone.
 * Baseado em material do Prof. Jose Fernando Junior disponibilizado pelo professor Luiz Eduardo.
 */
public class Drawing {
    static Stage screen;

    public static Stage getGameScreen() {
        return screen;
    }

    public static void setGameScreen(Stage newScreen) {
        screen = newScreen;
    }    
    public static void draw(Graphics g, ImageIcon imageIcon, double y, double x) {
        //System.out.println("y="+(y * Consts.CELL_SIZE)+", x="+(x * Consts.CELL_SIZE));
        imageIcon.paintIcon(screen, g, (int)Math.round(y * Consts.CELL_SIZE),(int)Math.round(x * Consts.CELL_SIZE));
    }
}
