/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demineur;


import demineur.control.Vue;
import demineur.model.GameBoard;
import demineur.view.ChessBoardWithColumnsAndRows;
import demineur.view.FenetrePrincipale;
import javax.swing.JFrame;
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
        Vue v = new Vue(new GameBoard(10, 10, 10));
        v.setVisible(true);
    }    
    
    
    
    
    /**
     * @param args the command line arguments
     */
   /* public static void main(String[] args) {


        // TODO code application logic here
        
        Runnable r = new Runnable() {

            @Override
            public void run() {
                FenetrePrincipale fenetre =
                        new FenetrePrincipale();

                JFrame f = new JFrame("Demineur");
                f.add(fenetre.getFenetre());
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationByPlatform(true);

                // ensures the frame is the minimum size it needs to be
                // in order display the components within it
                f.pack();
                // ensures the minimum size is enforced.
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
                GameBoard Plateau = new GameBoard(10,10,10);
                System.out.println(Plateau.toString());
            }
        };
        SwingUtilities.invokeLater(r);
        
    }*/
}
