/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demineur.control;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import demineur.model.GameBoard;
import demineur.model.Case;
import java.util.ArrayList;
import javax.swing.border.Border;

/**
 *
 * @author Malomek
 */
public class Vue extends JFrame implements Observer, ActionListener{
    
    GameBoard platteau;
    int nbMines;
    int tailleX;
    int tailleY;
    
    CaseGraphique[][] cases ;
    
    JMenuItem nouvellepartie;
    JMenuItem sauvegarder;
    
    public Vue(GameBoard board){
        this.platteau=board;
        
        this.nbMines=board.getMines();
        this.tailleX=board.getTailleX();
        this.tailleY=board.getTailleY();
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Demineur");
        setSize(tailleX*38, tailleY*38 + 30);
        
        JMenuBar barremenu = new JMenuBar();
        JMenu menu = new JMenu("Jeu");
        nouvellepartie = new JMenuItem("Nouvelle Partie");
        nouvellepartie.addActionListener(this);
        sauvegarder = new JMenuItem("Sauvegarder");
        sauvegarder.addActionListener(this);
        
        menu.add(nouvellepartie);
        menu.add(sauvegarder);
        barremenu.add(menu);
        setJMenuBar(barremenu);
        
        JComponent jc = new JPanel (new GridLayout(tailleY, tailleX));
        Border limite = BorderFactory.createLineBorder(Color.black,1);
        
        cases = new CaseGraphique[tailleX][tailleY];
        
        for(int i = 0; i < tailleX; i++){
            for(int j = 0; j< tailleY; j++){
                CaseGraphique cg = new CaseGraphique(i,j, this);
                platteau.getCase(i, j).addObserver(this);
               // cases[i][j].add(new JLabel(""));
                cases[i][j]=cg;
                cg.setBorder(limite);
                jc.add(cg);  
            }
        }
        
        jc.setBorder(limite);
        add(jc);
        setContentPane(jc);
        platteau.addObserver(this);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Vue v = new Vue(new GameBoard(10, 10, 10));
        v.setVisible(true);
    }

    @Override
    @SuppressWarnings("empty-statement")
    public void update(Observable o, Object arg) {
        boolean findujeu = false;
        boolean defaite = false;
        int etat;
        JLabel label;
        String texte="";
        
        for(int i=0;i<tailleX;i++)
        {
            for(int j=0;j<tailleY;j++)
            {
                if(platteau.getCase(i, j).getEtat()==0)
                {
                    cases[i][j].setBackground(Color.white);
                    cases[i][j].setImage(1);
                   // cases[i][j].setImage(0);
                }
                else if(platteau.getCase(i, j).getEtat()==1)
                {
                    if(platteau.getCase(i, j).getContenu()==9){
                        cases[i][j].setBackground(Color.BLACK);
                        findujeu=true;
                        defaite=true;
                    }
                    
                    
                    else{
                        if(platteau.getCase(i, j).getContenu()!=0){
                            texte= Integer.toString(platteau.getCase(i, j).getContenu());
                            label= new JLabel(texte, JLabel.CENTER);
                            //label.add(cases[i][j]).setVisible(true);
                            cases[i][j].add(label);
                            label.setVisible(true);
                        }
                        //cases[i][j].add(new JLabel(label.setText));
                        cases[i][j].setBackground(Color.GREEN);
                       // cases[i][j].setValeur(platteau.getCase(i, j).getContenu());
                    }    
                }
                else if(platteau.getCase(i, j).getEtat()==2)
                {
                    cases[i][j].setBackground(Color.red);
                }
                else if(platteau.getCase(i, j).getContenu()==0)
                {
                    cases[i][j].setBackground(Color.green);
                }
                else
                {
                    cases[i][j].setValeur(platteau.getCase(i, j).getContenu());
                }
                cases[i][j].validate();
                cases[i][j].repaint();
            }
        }
        System.out.println(platteau.getJeu());
        if(platteau.getJeu()!=0){
            findujeu=true;
        };
    
        if(findujeu){
            findujeu=false;
             Object[] choix = {"Rejouer", "Arreter"};
             String textefin;
             if(defaite){
                 textefin="Dommage, c'est perdu";
             }
             else{
                 textefin ="Felicitations, c'est gagné";
             }
             
                 defaite=false;
             int boutton = JOptionPane.showOptionDialog(this, textefin, "C'est fini", 
                     JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choix, choix[1]);
                     
             if(boutton == 0){
                    setVisible(false);
                    Vue nouvellevue = new Vue(new GameBoard(this.nbMines, this.tailleX, this.tailleY));
                    nouvellevue.setVisible(true);
             }
             if(boutton == 1){
                 System.exit(0);
             }           
        }   
  }    

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == nouvellepartie){
            setVisible(false);
            Vue v = new Vue(new GameBoard(nbMines, tailleX, tailleY));
            v.setVisible(true);
        }
        if(e.getSource() == sauvegarder){
            platteau.sauvegarder();
            JOptionPane.showMessageDialog(rootPane, "La partie a été sauvegardée");
        }

    }
    
    
    
}
