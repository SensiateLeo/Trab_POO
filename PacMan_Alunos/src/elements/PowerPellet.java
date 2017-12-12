package elements;

import utils.Drawing;
import java.awt.Graphics;
import java.io.Serializable;
import utils.Consts;

/**
 * Projeto de POO 2017
 * 
 * @authors Hiago de Franco, Leonardo Sensiate, Mateus Castilho Leite e Vinícius Nakasone.
 * Baseado em material do Prof. Jose Fernando Junior disponibilizado pelo professor Luiz Eduardo.
 */
public class PowerPellet extends Element implements Serializable{

    public PowerPellet(String imageName) {
        super(imageName);
        Consts.numDots = Consts.numDots + 1;
        this.isMortal = true;
    }

    @Override
    public void autoDraw(Graphics g) {
        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
    }
    
}