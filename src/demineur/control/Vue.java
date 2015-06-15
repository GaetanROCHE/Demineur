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
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    int choixcouleur;
    
    JMenuItem nouvellepartie;
    JMenuItem sauvegarder;
    JMenuItem charger;
    JMenuItem changerdifficulte;
    JMenuItem grisrouge;
    JMenuItem bleucyan;
    JMenuItem rosemagenta;
    JMenuItem minesrestantes;
    JMenuItem minestrouvable;
    
    
    public Vue(GameBoard board){
       
        this.platteau=board;
        
        this.nbMines=board.getMines();
        this.tailleX=board.getTailleX();
        this.tailleY=board.getTailleY();
        
        this.choixcouleur=1;
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Demineur");
        setSize(tailleX*38, tailleY*38 + 30);
        
        JMenuBar barremenu = new JMenuBar();
        JMenu menu = new JMenu("Jeu");
        JMenu options = new JMenu("Couleurs");
        JMenu avancement = new JMenu("Avancement");
        
        nouvellepartie = new JMenuItem("Nouvelle Partie");
        nouvellepartie.addActionListener(this);
        sauvegarder = new JMenuItem("Sauvegarder");
        sauvegarder.addActionListener(this);
        charger = new JMenuItem("Charger");
        charger.addActionListener(this);
        changerdifficulte = new JMenuItem("Changer la difficulte");
        changerdifficulte.addActionListener(this);
        grisrouge = new JMenuItem("Gris et Blanc");
        grisrouge.addActionListener(this);
        bleucyan = new JMenuItem("Bleu et Cyan");
        bleucyan.addActionListener(this);
        rosemagenta = new JMenuItem("Surprise du chef");
        rosemagenta.addActionListener(this);
        minesrestantes = new JMenuItem("Mines restantes : " + Integer.toString(platteau.getMines()));
        minesrestantes.addActionListener(this);
        
        menu.add(nouvellepartie);
        menu.add(sauvegarder);
        menu.add(charger);
        menu.add(changerdifficulte);
        options.add(grisrouge);
        options.add(bleucyan);
        options.add(rosemagenta);
        avancement.add(minesrestantes);
        barremenu.add(menu);
        barremenu.add(options);
        barremenu.add(minesrestantes);
        setJMenuBar(barremenu);
        
        JComponent jc = new JPanel (new GridLayout(tailleY, tailleX));
        Border limite = BorderFactory.createLineBorder(Color.black,1);
        
        cases = new CaseGraphique[tailleX][tailleY];
        
        for(int i = 0; i < tailleX; i++){
            for(int j = 0; j< tailleY; j++){
                CaseGraphique cg = new CaseGraphique(i,j, this);
                platteau.getCase(i, j).addObserver(this);
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
    
    public static void run(){
        String choixdujoueur;
        int difficulte;
        String Texte = "Choisissez le mode de difficulté : \n";
        Texte+= "1 : Mode facile \n";
        Texte+= "2 : Mode intermediaire \n";
        Texte+= "3 : Mode difficile";
        do
        {
            choixdujoueur = JOptionPane.showInputDialog(Texte);
            difficulte = Integer.parseInt(choixdujoueur);
        }while(difficulte!=1 && difficulte!=2 && difficulte!=3);
        
        if(difficulte == 1){
            Vue v = new Vue(new GameBoard(10, 10, 10));
            v.setVisible(true);
            v.update(v.platteau, v);
        }
        if(difficulte == 2){
            Vue v = new Vue(new GameBoard(16, 16, 40));
            v.setVisible(true);
            v.update(v.platteau, v);
        }
        if(difficulte == 3){
            Vue v = new Vue(new GameBoard(30, 30, 99));
            v.setVisible(true);
            v.update(v.platteau, v);
        }
        
    }
    


    @Override
    @SuppressWarnings("empty-statement")
    public void update(Observable o, Object arg) {
        boolean findujeu = false;
        boolean defaite = false;
        int etat;
        JLabel label;
        String texte;
        String mines = ("Mines restantes : " + Integer.toString(platteau.getMines()));
        

        minesrestantes.setText(mines);


        if(platteau.getCasedecouverte() == 1 && platteau.getJeu() == 1){
            int[] choix = platteau.chercheMine();
            this.setVisible(false);
            Vue v = new Vue(new GameBoard(nbMines, tailleX, tailleY));
            v.setVisible(true);
            v.platteau.reveleCase(choix[0], choix[1]);
            return;
        }   

        for(int i=0;i<tailleX;i++)
        {
            for(int j=0;j<tailleY;j++)
            {
                if(platteau.getCase(i, j).getEtat()==0)
                {
                    if(choixcouleur == 1){
                       cases[i][j].setBackground(Color.GRAY);
                    }
                    if(choixcouleur == 2){
                       cases[i][j].setBackground(Color.BLUE); 
                    }
                    if(choixcouleur == 3){
                        cases[i][j].setBackground(Color.MAGENTA); 
                    } 
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
                            cases[i][j].add(label);
                            label.setVisible(true);
                        }
                        if(choixcouleur == 1){
                            cases[i][j].setBackground(Color.WHITE);
                        }
                        if(choixcouleur == 2){
                           cases[i][j].setBackground(Color.CYAN); 
                        }
                        if(choixcouleur == 3){
                           cases[i][j].setBackground(Color.PINK); 
                        }
                    }    
                }
                else if(platteau.getCase(i, j).getEtat()==2)
                {
                    cases[i][j].setBackground(Color.RED);
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
        if(platteau.testVictoire()){
            findujeu=true;
        }
        else{
            if(!(defaite)){
                findujeu=false;
            }
        }
    
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
            int boutton = JOptionPane.showOptionDialog(this, textefin, "Jeu fini", 
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choix, choix[1]);
                             
            if(boutton == 0){   

                setVisible(false);
                Vue nouvellevue = new Vue(new GameBoard(this.tailleX, this.tailleY, this.nbMines));
                this.platteau=nouvellevue.platteau;
                this.cases=nouvellevue.cases;
                update(o,arg);

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
            this.setVisible(false);
            Vue v = new Vue(new GameBoard(tailleX, tailleY, nbMines));
            v.setVisible(true);
        }
        if(e.getSource() == sauvegarder){
            platteau.sauvegarder();
            JOptionPane.showMessageDialog(rootPane, "La partie a été sauvegardée");
        }
        if(e.getSource() == charger){
            this.setVisible(false);
            try {
                Vue v;
                v = new Vue(new GameBoard("sauvegarde.txt"));
                v.setVisible(true);
                v.update(platteau, v);
            } catch (IOException ex) {
                Logger.getLogger(Vue.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(e.getSource() == changerdifficulte){
            this.setVisible(false);
            run();
        }
        if(e.getSource() == grisrouge){
            choixcouleur=1;
            this.update(platteau, this);
        }
        if(e.getSource() == bleucyan){
            choixcouleur=2;
            this.update(platteau, this);
        }
        if(e.getSource() == rosemagenta){
            choixcouleur=3;
            this.update(platteau, this);
        }
    }  
}
