package elements;

import utils.Consts;
import utils.Drawing;
import java.awt.Graphics;

/**
 * Projeto de POO 2017
 * 
 * @authors Hiago de Franco, Leonardo Sensiate, Mateus Castilho Leite e Vinícius Nakasone.
 * Baseado em material do Prof. Jose Fernando Junior disponibilizado pelo professor Luiz Eduardo.
 */
public class Muro extends Element{
    
    public Muro(String imageName) {
        super(imageName);
        this.isTransposable = false;
    }

    public void autoDraw(Graphics g) {
        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
    }    
}
