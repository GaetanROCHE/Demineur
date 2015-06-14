/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demineur;


import demineur.control.Vue;
import demineur.model.GameBoard;
import demineur.view.FenetrePrincipale;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


/**
 *
 * @author Gaëtan
 */
public class Demineur {

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Vue.run();
       // Vue v = new Vue(new GameBoard(10, 10, 10));
        //v.setVisible(true);
    }    
}
