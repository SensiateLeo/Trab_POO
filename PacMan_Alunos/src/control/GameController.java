package control;

import elements.Bola;
import elements.Cereja;
import elements.Element;
import elements.Fantasma;
import elements.Morango;
import elements.Pacman;
import elements.PowerPellet;
import java.awt.Graphics;
import java.util.ArrayList;
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
            if (elemArray.get(i) instanceof Pacman || elemArray.get(i) instanceof Fantasma){
                continue;
            }
            else{
                elemArray.get(i).autoDraw(g);
            }
        }
    }
    public void drawDynamicElements(ArrayList<Element> elemArray, Graphics g){
        
        for(int i=0; i<elemArray.size(); i++){
            if (elemArray.get(i) instanceof Pacman || elemArray.get(i) instanceof Fantasma){
                elemArray.get(i).autoDraw(g);
            }
        }
    }
    
    public void processAllElements(ArrayList<Element> e, Pacman pacman, Fantasma ghost){
        if(e.isEmpty())
            return;
        
        Element eTemp;
        
        for(int i = 1; i < e.size(); i++){
            eTemp = e.get(i);
            if(eTemp instanceof Fantasma){
                break;
            }
        }
        
        if (!isValidPosition(e, pacman)) {
            pacman.backToLastPosition();
            pacman.setMovDirection(Pacman.STOP);
            return;
        }
        
        if (!isValidPosition(e, ghost)) {
            ghost.backToLastPosition();
            ghost.setMovDirection(Pacman.STOP);
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
                        pacman.pontuacao = pacman.pontuacao + 10;
                        pacman.comeFantasma = true;
                    } 
                    if(eTemp instanceof Fantasma){
                        if(eTemp.isMortal()== false){
                            pacman.vidas = pacman.vidas - 1;
                            pacman.setPosition(1, 1);
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
        ghost.move();
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
