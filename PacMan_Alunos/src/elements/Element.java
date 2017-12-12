package elements;

import utils.Consts;
import utils.Position;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 * Projeto de POO 2017
 * 
 * @authors Hiago de Franco, Leonardo Sensiate, Mateus Castilho Leite e Vinícius Nakasone.
 * Baseado em material do Prof. Jose Fernando Junior disponibilizado pelo professor Luiz Eduardo.
 */
public abstract class Element implements Serializable{

    protected ImageIcon imageIcon;
    protected Position pos;
    protected boolean isTransposable; // Pode passar por cima?
    protected boolean isMortal;       // Se encostar, morre?

    public Element(String imageName) {
        this.pos = new Position(1, 1);
        this.isTransposable = true;
        this.isMortal = false;
        
        try {
            imageIcon = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + imageName);
            Image img = imageIcon.getImage();
            BufferedImage bi = new BufferedImage(Consts.CELL_SIZE, Consts.CELL_SIZE, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, Consts.CELL_SIZE, Consts.CELL_SIZE, null);
            imageIcon = new ImageIcon(bi);
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public boolean overlap(Element elem) {
        double xDist = Math.abs(elem.pos.getX() - this.pos.getX());
        double yDist = Math.abs(elem.pos.getY() - this.pos.getY());
        
        if (xDist < 0.7 && yDist < 0.7)
            return true;
        else
            return false;
    }

    public String getStringPosition() {
        return ("(" + pos.getX() + ", " + pos.getY() + ")");
    }
    
    public boolean setPosition(double x, double y) {
        return pos.setPosition(x, y);
    }

    public boolean isTransposable() {
        return isTransposable;
    }

    public void setTransposable(boolean isTransposable) {
        this.isTransposable = isTransposable;
    }
    
    public boolean isMortal() {
        return isMortal;
    }
    
    public void setMortal(boolean isTransposable) {
        this.isMortal = isTransposable;
    }

    abstract public void autoDraw(Graphics g);

    public boolean moveUp() {
        if(isMortal() == false){
            return this.pos.moveUp();
        }
        else{
            return this.setPosition(this.pos.getX()-Consts.WALK_STEP_MORTAL, this.pos.getY());
        }
    }

    public boolean moveDown() {
        if(isMortal() == false){
            return this.pos.moveDown();
        }
        else{
            return this.setPosition(this.pos.getX()+Consts.WALK_STEP_MORTAL, this.pos.getY());
        }
    }

    public boolean moveRight() {
        if(isMortal() == false){
            return this.pos.moveRight();
        }
        else{
            if(this.pos.getY() == 21){
                return this.setPosition(this.pos.getX(), 0);
            }
            else{
                return this.setPosition(this.pos.getX(), this.pos.getY()+Consts.WALK_STEP_MORTAL);
            }
        }   
    }

    public boolean moveLeft() {
        if(isMortal() == false){
            return this.pos.moveLeft();
        }
        else{
            if(this.pos.getY() == 0){
                return this.setPosition(this.pos.getX(), 21);
            }
            else{
                return this.setPosition(this.pos.getX(), this.pos.getY()-Consts.WALK_STEP_MORTAL);  
            }
        }
    }
}
