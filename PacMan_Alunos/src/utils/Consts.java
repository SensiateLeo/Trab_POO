package utils;

import control.Menu;
import java.io.File;

/**
 * Projeto de POO 2017
 * 
 * @authors Hiago de Franco, Leonardo Sensiate, Mateus Castilho Leite e Vinícius Nakasone.
 * Baseado em material do Prof. Jose Fernando Junior disponibilizado pelo professor Luiz Eduardo.
 */
public class Consts {
    public static final int CELL_SIZE = 28;
    public static final int NUM_CELLS = 22;
    public static final int WALK_STEP_DEC_PLACES = 2;
    public static final double WALK_STEP_NORMAL = 0.1;
    public static final double WALK_STEP_MORTAL = 0.06;
    
    public static final String PATH = File.separator+"imgs"+File.separator;
    
    public static final int DELAY = 20;
    
    public static int numDots = 0;
    
    public static int vidas = 3;
    
    public static int numFantasmas = 4;
    
    public static long tempoInicioFantasma = 0;
    
    public static final Menu menuprincipal = new Menu();
}
