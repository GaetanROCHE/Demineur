/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demineur.model;

/**
 *
 * @author Gaëtan
 */
public class Case {
    int x;
    int y;
    private int etat;
    private int contenu;

    public Case(int j, int k, int contenue){
        contenu=0;
        etat = 0;
        this.contenu=contenue;
        x=j;
        y=k;
    }
    
    public boolean reveleCase(){
        etat = 1;
        return !(contenu == 9);
    }
    
    public void marqueCase(){
        etat = 2;
    }

    
    public void setEtat(int etat){
        this.etat=etat;
    }
    
    public boolean bombe(){
        return contenu == 9;
    }
}
