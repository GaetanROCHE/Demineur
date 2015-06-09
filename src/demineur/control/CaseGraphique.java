/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demineur.control;
import demineur.model.GameBoard;
import demineur.model.Case;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 *
 * @author Malomek
 */
public class CaseGraphique extends JPanel {
    
    private int[] id;
    private Vue view;
    
    ImageIcon images[];
    JLabel icone;
    
    public CaseGraphique(int x, int y, Vue vue){
        super();
        this.id= new int[2];
        id[0]=x;
        id[1]=y;
        this.view=vue;
        setBackground(Color.white);
        
        initImages();
        icone = new JLabel( images[0] );
        setLayout(new BorderLayout());
        add(icone, BorderLayout.CENTER);
        
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent arg0) {
                super.mouseClicked(arg0);
                if (vue.platteau.getCase(id).getEtat()==0)
                    setBackground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                super.mouseExited(arg0);
                if (vue.platteau.getCase(id).getEtat()==1);
                    //setBackground(Color.BLACK);
            }

            @Override
            public void mousePressed(MouseEvent arg0) {
                if (arg0.getButton() == 3) {
                    vue.platteau.poseDrapeau(id[0], id[1]);
                } else {
                    if (!(vue.platteau.getCase(id).getEtat()==2))
                        vue.platteau.reveleCase(id[0], id[1]);
                }
            }

        });
    }
    
    public void setValeur(int valeur) {
        remove(icone);
        icone = new JLabel( images[10] );
        setLayout(new BorderLayout());
        add(icone, BorderLayout.CENTER);
        repaint();
        icone = new JLabel( images[valeur] );
        setLayout(new BorderLayout());
        add(icone, BorderLayout.CENTER);
    }
    
    public void setBombe () {
        remove(icone);
        icone = new JLabel( images[9] );
        setLayout(new BorderLayout());
        add(icone, BorderLayout.CENTER);
    }
    
    public void setImage(int etat) {
        
        if(etat==0){
            remove(icone);
            icone = new JLabel( images[0] );
            setLayout(new BorderLayout());
            add(icone, BorderLayout.CENTER);
        }
        if(etat==1){
            remove(icone);
            icone = new JLabel(images[10]);
            setLayout(new BorderLayout());
            add(icone, BorderLayout.CENTER);
        }
        if(etat==2){
            remove(icone);
            icone = new JLabel(images[11]);
            setLayout(new BorderLayout());
            add(icone, BorderLayout.CENTER);
        }
        
    }
    
    private void initImages(){
        images = new ImageIcon[12];
        
        images[0]=new ImageIcon( "./src/Ressources/batlike.png");
        for(int i=0;i<9;i++)
        {
            images[i]=new ImageIcon( "./src/Ressources/batlike.png");
        }  
        images[9]=new ImageIcon( "./src/Ressources/batlike.png");
        images[10] =  new ImageIcon( "./src/Ressources/icone.png");
        images[11] =  new ImageIcon( "./src/Ressources/batlike.png");
    }
    
    
    
    
    
}
