package control;

import elements.Dot;
import elements.Cereja;
import elements.Element;
import elements.Fantasma;
import elements.Morango;
import elements.Pacman;
import elements.PowerPellet;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import utils.Consts;

/**
 * Projeto de POO 2017
 * 
 * @authors Hiago de Franco, Leonardo Sensiate, Mateus Castilho Leite e Vinícius Nakasone.
 * Baseado em material do Prof. Jose Fernando Junior disponibilizado pelo professor Luiz Eduardo.
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
    
    public void processAllElements(ArrayList<Element> e, Pacman pacman, Fantasma ghost1, Fantasma ghost2, Fantasma ghost3, Fantasma ghost4){
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
                    if(eTemp instanceof Dot) {
                        e.remove(eTemp);
                        Consts.numDots = Consts.numDots - 1;
                        pacman.pontuacao = pacman.pontuacao + 10;
                        pacman.controleVidas = pacman.controleVidas + 10;
                        
                    } 
                    if(eTemp instanceof Morango){
                        e.remove(eTemp);
                        pacman.pontuacao = pacman.pontuacao + 300;
                        pacman.controleVidas = pacman.controleVidas + 300;
                    }
                    if(eTemp instanceof Cereja){
                        e.remove(eTemp);
                        pacman.pontuacao = pacman.pontuacao + 100;
                        pacman.controleVidas = pacman.controleVidas + 100;
                    }
                    if(eTemp instanceof PowerPellet) {
                        e.remove(eTemp);
                        pacman.pontuacao = pacman.pontuacao + 50;
                        pacman.controleVidas = pacman.controleVidas + 50;
                        Consts.numDots = Consts.numDots - 1;
                        Consts.tempoInicioFantasma = System.currentTimeMillis()/1000;
                        pacman.comeFantasma = true;
                    } 
                    if(eTemp instanceof Fantasma){
                        if(eTemp.isMortal()== false){
                            Consts.vidas = Consts.vidas - 1;
                            pacman.setPosition(2, 2);
                        }
                        else{
                            e.remove(eTemp);
                            ((Fantasma) eTemp).setVivo(false);
                            Consts.numFantasmas = Consts.numFantasmas - 1;
                            if(Consts.numFantasmas == 4){
                               pacman.pontuacao = pacman.pontuacao + 200;
                               pacman.controleVidas = pacman.controleVidas + 200;
                            }
                            if(Consts.numFantasmas == 3){
                               pacman.pontuacao = pacman.pontuacao + 400;
                               pacman.controleVidas = pacman.controleVidas + 400;
                            } 
                            if(Consts.numFantasmas == 2){
                               pacman.pontuacao = pacman.pontuacao + 800;
                               pacman.controleVidas = pacman.controleVidas + 800;
                            } 
                            if(Consts.numFantasmas == 1){
                               pacman.pontuacao = pacman.pontuacao + 1600;
                               pacman.controleVidas = pacman.controleVidas + 1600;
                            }
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
