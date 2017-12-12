/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import java.awt.Graphics;
import java.util.ArrayList;
import utils.Drawing;

/**
 *
 * @authors Hiago de Franco, Leonardo Sensiate, Mateus Castilho Leite e Vinícius Nakasone.
 * Baseado em material do Prof. Jose Fernando Junior disponibilizado pelo professor Luiz Eduardo.
 */
public class Morango extends Element{

    public Morango(String imageName) {
        super(imageName);
        this.isTransposable = true;
    }

    public void autoDraw(Graphics g) {
        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
    }
    public void removeMorango(ArrayList<Element> e)
    {
        if(e.isEmpty())
        {
            return;
        }
        Element eTemp;
        for(int i = 1; i < e.size(); i++)
        {
            eTemp = e.get(i);
            if(eTemp instanceof Morango)
            {
                e.remove(eTemp);
            }
        }
    }
}
