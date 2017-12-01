package control;

import elements.Bola;
import elements.Cereja;
import elements.Element;
import elements.Blinky;
import elements.Clyde;
import elements.Inky;
import elements.Morango;
import elements.Pacman;
import elements.Pinky;
import elements.PowerPellet;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import utils.Consts;

/**
 * Projeto de POO 2017
 * 
 * @author Luiz Eduardo
 * Baseado em material do Prof. Jose Fernando Junior
 */
public class GameController {
    
    public void drawAllElements(ArrayList<Element> elemArray, Graphics g){
        for(int i=0; i<elemArray.size(); i++){
            if (elemArray.get(i) instanceof Pacman || elemArray.get(i) instanceof Blinky || elemArray.get(i) instanceof Inky || elemArray.get(i) instanceof Pinky || elemArray.get(i) instanceof Clyde){
                continue;
            }
            else{
                elemArray.get(i).autoDraw(g);
            }
        }
    }
    public void drawDynamicElements(ArrayList<Element> elemArray, Graphics g){
        
        for(int i=0; i<elemArray.size(); i++){
            if (elemArray.get(i) instanceof Pacman || elemArray.get(i) instanceof Blinky || elemArray.get(i) instanceof Inky || elemArray.get(i) instanceof Pinky || elemArray.get(i) instanceof Clyde){
                elemArray.get(i).autoDraw(g);
            }
        }
    }
    
    public void processAllElements(ArrayList<Element> e, Pacman pacman, Blinky ghost1, Inky ghost2, Pinky ghost3, Clyde ghost4){
        if(e.isEmpty())
            return;
        
        Element eTemp;
        
        for(int i = 1; i < e.size(); i++){
            eTemp = e.get(i);
            if(eTemp instanceof Blinky){
                break;
            }
            if(eTemp instanceof Inky){
                break;
            }
            if(eTemp instanceof Pinky){
                break;
            }
            if(eTemp instanceof Clyde){
                break;
            }
        }
        
        if (!isValidPosition(e, pacman)) {
            pacman.backToLastPosition();
            pacman.setMovDirection(Pacman.STOP);
            return;
        }
        
        if (!isValidPosition(e, ghost1)) {
            Random random = new Random();
            int x = random.nextInt(4);
            ghost1.backToLastPosition();
            ghost1.setMovDirection(x + 1);
            return;
        }
        
        if (!isValidPosition(e, ghost2)) {
            Random random = new Random();
            int x = random.nextInt(4);
            ghost2.backToLastPosition();
            ghost2.setMovDirection(x + 1);
            return;
        }
        
        if (!isValidPosition(e, ghost3)) {
            Random random = new Random();
            int x = random.nextInt(4);
            ghost3.backToLastPosition();
            ghost3.setMovDirection(x + 1);
            return;
        }
        
        if (!isValidPosition(e, ghost4)) {
            Random random = new Random();
            int x = random.nextInt(4);
            ghost4.backToLastPosition();
            ghost4.setMovDirection(x + 1);
            return;
        }
        
        
        for(int i = 1; i < e.size(); i++){
            eTemp = e.get(i);
            if(pacman.overlap(eTemp)){
                if(eTemp.isTransposable()){
                    if(eTemp instanceof Bola) {
                        e.remove(eTemp);
                        Consts.numBolas = Consts.numBolas - 1;
                        pacman.pontuacao = pacman.pontuacao + 10;
                    } 
                    if(eTemp instanceof Morango){
                        e.remove(eTemp);
                        pacman.pontuacao = pacman.pontuacao + 300;
                    }
                    if(eTemp instanceof Cereja){
                        e.remove(eTemp);
                        pacman.pontuacao = pacman.pontuacao + 100;
                    }
                    if(eTemp instanceof PowerPellet) {
                        e.remove(eTemp);
                        pacman.pontuacao = pacman.pontuacao + 50;
                        pacman.comeFantasma = true;
                    } 
                    if(eTemp instanceof Blinky || eTemp instanceof Inky || eTemp instanceof Pinky || eTemp instanceof Clyde){
                        if(eTemp.isMortal()== false){
                            pacman.vidas = pacman.vidas - 1;
                            pacman.setPosition(2, 2);
                        }
                        else{
                            e.remove(eTemp);
                            pacman.pontuacao = pacman.pontuacao + 200;
                        }
                    }
                }
            }
        }
        
        pacman.move();
        ghost1.move();
        ghost2.move();
        ghost3.move();
        ghost4.move();
    }
    public boolean isValidPosition(ArrayList<Element> elemArray, Element elem){
        Element elemAux;
        for(int i = 1; i < elemArray.size(); i++){
            elemAux = elemArray.get(i);            
            if(!elemAux.isTransposable())
                if(elemAux.overlap(elem))
                    return false;
        }        
        return true;
    }
}
