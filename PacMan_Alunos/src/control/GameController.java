package control;

import elements.Bola;
import elements.Cereja;
import elements.Element;
import elements.Fantasma;
import elements.Morango;
import elements.Pacman;
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
            elemArray.get(i).autoDraw(g);
        }
    }
    public void processAllElements(ArrayList<Element> e){
        if(e.isEmpty())
            return;
        
        Pacman lLolo = (Pacman)e.get(0);
        if (!isValidPosition(e, lLolo)) {
            lLolo.backToLastPosition();
            lLolo.setMovDirection(Pacman.STOP);
            return;
        }
        
        Element eTemp;
        for(int i = 1; i < e.size(); i++){
            eTemp = e.get(i);
            if(lLolo.overlap(eTemp)){
                if(eTemp.isTransposable()){
                    if(eTemp instanceof Bola) {
                        e.remove(eTemp);
                        Consts.numBolas = Consts.numBolas - 1;
                        lLolo.pontuacao = lLolo.pontuacao + 10;
                    } 
                    if(eTemp instanceof Morango){
                        e.remove(eTemp);
                        lLolo.pontuacao = lLolo.pontuacao + 300;
                    }
                    if(eTemp instanceof Cereja){
                        e.remove(eTemp);
                        lLolo.pontuacao = lLolo.pontuacao + 100;
                    }
                    if(eTemp instanceof Fantasma){
                        lLolo.vidas = lLolo.vidas - 1;
                        lLolo.setPosition(1, 1);
                    }
                }
            }
        }
        
        lLolo.move();
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
