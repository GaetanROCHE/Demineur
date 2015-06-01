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
    
    public int reveleCase(){
        etat = 1;
        return contenu;
    }
    
    public void marqueCase(){
        etat = 2;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void setEtat(int etat){
        this.etat=etat;
    }
    
    public boolean bombe(){
        return contenu == 9;
    }
    
    @Override
    public String toString(){
        return "|"+this.contenu +"|";
    }
}
