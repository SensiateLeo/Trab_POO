package control;

import utils.Consts;

/**
 * Projeto de POO 2017
 * 
 * @authors Hiago de Franco, Leonardo Sensiate, Mateus Castilho Leite e Vinícius Nakasone.
 * Baseado em material do Prof. Jose Fernando Junior disponibilizado pelo professor Luiz Eduardo.
 */
public class Main {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Consts.menuprincipal.setVisible(true);
            }
        });
    }
}

