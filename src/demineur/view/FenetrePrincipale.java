/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demineur.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author Malomek
 */
public final class FenetrePrincipale extends JPanel implements MouseListener {
    
    private final JPanel fenetre = new JPanel(new BorderLayout(3, 3));
    private JButton[][] Cases = new JButton[10][10];
    private JPanel platteaudemineur;
    private final JLabel message = new JLabel(
            " Pret a jouer");
    //rivate static final String COLS = "ABCDEFGH";
    
    public FenetrePrincipale(){
        FenetreInitialisation();
    }
    
    public void FenetreInitialisation(){
        
        fenetre.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        fenetre.add(tools, BorderLayout.PAGE_START);
        tools.add(new JButton("Nouvelle Partie")); // TODO - add functionality!
      /*  tools.addSeparator();
        tools.add(new JButton("Resign")); // TODO - add functionality!
        tools.addSeparator();*/
        tools.add(message);
        
        fenetre.add(new JLabel("?"), BorderLayout.LINE_START);

        platteaudemineur = new JPanel(new GridLayout(0, 10));
        platteaudemineur.setBorder(new LineBorder(Color.BLACK));
        fenetre.add(platteaudemineur);
        
        // create the chess board squares
        Insets buttonMargin = new Insets(0,0,0,0);
        for (int ii = 0; ii < Cases.length; ii++) {
            for (int jj = 0; jj < Cases[ii].length; jj++) {
                JButton b = new JButton();
                
                b.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(SwingUtilities.isLeftMouseButton(e)){
                            b.setIcon(null);
                            b.setBackground(Color.BLACK);                     
                            }
                        if(SwingUtilities.isRightMouseButton(e)){
                            Image img;                                 
                            if(b.getBackground()!=Color.RED)
                            {
                                b.setBackground(Color.RED);  
                                 try {
                                    img = ImageIO.read(getClass().getResource("icone.jpg"));
                                    b.setIcon(new ImageIcon(img));
                                    } catch (IOException ex) {
                                Logger.getLogger(FenetrePrincipale.class.getName()).log(Level.SEVERE, null, ex);
                                } 
                            }
                            else{
                                b.setIcon(null);
                                b.setBackground(Color.GREEN);  
                                }    
                            }
                        
                    }
                });
                
                
                
                b.setMargin(buttonMargin);
                b.addMouseListener(this);
                addMouseListener(this);
                // our chess pieces are 64x64 px in size, so we'll
                // 'fill this in' using a transparent icon..
                ImageIcon icon = new ImageIcon(
                        new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);
                b.setBackground(Color.GREEN);
                Cases[jj][ii] = b;
            }
        }
        
        //fill the gameboard
        for (int ii = 0; ii < 10; ii++) {
            for (int jj = 0; jj < 10; jj++) {
                    platteaudemineur.add(Cases[jj][ii]); 
            }
        }
    }
    /*
    public void actionPerformed(ActionEvent arg0) {
    //Lorsque l'on clique sur le bouton, on met à jour le JLabel
     System.out.print("ra");
  }*/
    
    public JPanel getFenetre(){
        return this.fenetre;
    }
    public JButton[][] getCases(){
        return this.Cases;
    }
    public JPanel getPlatteaudemineur(){
        return this.platteaudemineur;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e)){
            setBackground(Color.BLACK);
        }
        if(SwingUtilities.isRightMouseButton(e)){
             
        }
        setBackground(Color.BLACK);
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    @Override
    public void mousePressed(MouseEvent e) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     
    @Override
    public void mouseReleased(MouseEvent e) {
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setBackground(Color.BLACK);
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setBackground(Color.BLACK);
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }   
    
   //PTDR
}
